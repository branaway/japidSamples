package controllers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.math.NumberUtils;

import etc.NashornTool;
import etc.NashornTool.FunctionInfo;
import etc.RenderJackson;
import etc.RenderJapid;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.Undefined;
import play.Play;
import play.Play.Mode;
import play.db.jpa.Model;
import play.mvc.Http.Request;
import play.mvc.Scope.Params;
import play.mvc.results.RenderJson;
import play.mvc.results.Result;
import play.utils.Utils;
import play.utils.Utils.AlternativeDateFormat;

public class JSController extends cn.bran.play.JapidController {
	public static String jsRoot = "js";
	private static boolean shouldCoerceArg = Boolean
			.parseBoolean(Play.configuration.getProperty("jscontroller.coerce.args", "false"));

	private static final String PLAY_HEADERS_JS = jsRoot + "/etc/playHeaders.js";
	private static final String MODEL_HEADERS_JS = jsRoot + "/etc/modelHeaders.js";
	// private static final String JAVA_IMPORTS = "var RenderText =
	// Java.type(\"play.mvc.results.RenderText\");" +
	// // "var OK = Java.type(\"play.mvc.results.OK\");" + // class not found
	// for
	// // this, why?
	// "var RenderJson = Java.type(\"play.mvc.results.RenderJson\");"
	// + "var NotFound = Java.type(\"play.mvc.results.NotFound\");"
	// + "var Request = Java.type(\"play.mvc.Http.Request\");" + "";
	private static ThreadLocal<ScriptEngine> engineHolder = ThreadLocal.withInitial(() -> {
		// ScriptEngine engine = new
		// ScriptEngineManager().getEngineByName("nashorn");
		System.setProperty("nashorn.typeInfo.maxFiles", "20000");
		String[] options = new String[] { "-ot=true", "--language=es6" };
		ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine(options);
		if (Play.mode.isProd())
			try {
				engine.eval(new FileReader(PLAY_HEADERS_JS));
				updateModelsHeader();
				engine.eval(new FileReader(MODEL_HEADERS_JS));

			} catch (ScriptException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return engine;
	});

	/**
	 * the gateway to the JavaScript engine. The action is determined by the request method. 
	 * 
	 * 
	 * @param _jsfile
	 *            the JS file to invoke to process the request
	 * 
	 * @throws NoSuchMethodException
	 * @Deprecated use the process2 entry point. 
	 */
	@Deprecated
	public static void process(String _jsfile) throws NoSuchMethodException {
		ScriptEngine engine = engineHolder.get();
		if (_jsfile.endsWith(".js"))
			_jsfile += _jsfile.substring(0, _jsfile.lastIndexOf(".js"));
		// get
		// file
		// name
		// without
		// extension
		try {
			String fileName = jsRoot + "/" + _jsfile + ".js";
			File rawFile = new File(fileName);
			if (!rawFile.exists()) {
				notFound(fileName);
			}

			JSObject module = getModule(_jsfile, engine, rawFile);

			Object[] args = processParams((FunctionInfo) engine.get(_jsfile + "." + request.method));

			// method one:
			Object r = ((JSObject) module.getMember(request.method)).call(null, args);
			//
			// method two: this method use the engine and call top level
			// function.
			// this is to invoke method on object
			// Object r = ((Invocable) engine).invokeMethod(module,
			// request.method, args);

			if (r instanceof RenderJapid) {
				RenderJapid rj = (RenderJapid) r;
				renderJapidWith(jsRoot + "/" + _jsfile + request.method, rj.args);
			} else if (r instanceof Result) {
				throw (Result) r;
			} else {
				renderText(r);
			}
		} catch (FileNotFoundException e) {
			notFound(_jsfile);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error(e);
		}
	}

	/**
	 * 
	 * @param _module
	 *            the js file that defines a module.
	 * @param _method
	 *            the method of the module to invoke
	 * @throws NoSuchMethodException
	 */
	public static void process2(String _module, String _method) throws NoSuchMethodException {
		ScriptEngine engine = engineHolder.get();
		if (_module.endsWith(".js"))
			_module += _module.substring(0, _module.lastIndexOf(".js"));
		// get
		// file
		// name
		// without
		// extension
		try {
			String fileName = jsRoot + "/" + _module + ".js";
			File rawFile = new File(fileName);
			if (!rawFile.exists()) {
				notFound(fileName);
			}

			JSObject var = getModule(_module, engine, rawFile);

			// not implemented yet
			// Object optionCoerceArgsMember =
			// var.getMember("optionCoerceArgs");

			// method one:
			Object member = var.getMember(_method);
			if (member instanceof Undefined) {
				notFound("methd not found in the module: " + _module + "." + _method);
			}
			Object[] args = processParams((FunctionInfo) engine.get(_module + "." + _method));

			Object r = ((JSObject) member).call(null, args);
			//
			// method two: this method use the engine and call top level
			// function.
			// this is to invoke method on object
			// Object r = ((Invocable) engine).invokeMethod(module,
			// request.method, args);

			if (r instanceof RenderJapid) {
				RenderJapid rj = (RenderJapid) r;
				renderJapidWith(jsRoot + "/" + _module + "/" + _method, rj.args);
			} else if (r instanceof Result) {
				throw (Result) r;
			} else {
				renderText(r);
			}
		} catch (FileNotFoundException e) {
			notFound(_module);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error(e);
		}
	}

	/**
	 * assuming module definition in the js file: var _jsfile = function()
	 * {function GET(){} function POST{} return {GET:GET, POST:POST}}()
	 *
	 * This is using the script engine to cache the state
	 * 
	 * @param moduleName
	 * @param engine
	 * @param rawFile
	 * @return
	 * @throws ScriptException
	 * @throws FileNotFoundException
	 */
	private static JSObject getModule(String moduleName, ScriptEngine engine, File rawFile)
			throws ScriptException, FileNotFoundException {
		if (Play.mode.isDev()) {
			_updateModelsHeader();
			File modelsFile = new File(MODEL_HEADERS_JS);
			if (modelsFile.exists())
				engine.eval(new FileReader(modelsFile));
			// parse the header
			engine.eval(new FileReader(PLAY_HEADERS_JS));
			// remove old definition
			engine.getBindings(ScriptContext.ENGINE_SCOPE).remove(moduleName);
			evaluate(engine, rawFile);
			JSObject module = parserModule(moduleName, engine);
			return module;
		} else {
			JSObject module = (JSObject) engine.get(moduleName);
			if (module == null) {
				module = parserModule(moduleName, engine);
			}
			return module;
		}
	}

	private static JSObject parserModule(String moduleName, ScriptEngine engine) {
		JSObject module = (JSObject) engine.get(moduleName);
		if (module == null) {
			notFound("the module is not defined in " + moduleName);
		} else {
			// parse the function parameters
			extractMethodInfo(moduleName, engine, module);
		}
		return module;
	}

	private static void extractMethodInfo(String moduleName, ScriptEngine engine, JSObject module) {
		Set<String> keys = module.keySet();
		keys.stream().forEach(k -> {
			Object member = module.getMember(k);
			if (member instanceof ScriptObjectMirror && ((ScriptObjectMirror) member).isFunction()) {
				String funcSource = member.toString();
				List<FunctionInfo> funcs = NashornTool.extractFuncs(k, funcSource);
				FunctionInfo fi = funcs.get(0);
				// store the method signature in the engine scope
				// key by <modulename>.<method name>
				engine.put(moduleName + "." + k, fi);
			}
		});
	}

	private static Object[] processParams(FunctionInfo fi) {
		params.checkAndParse();
		Map<String, String[]> data = params.data;

		return fi.parameterNames.stream().map(k -> {
			String[] v = data.get(k);
			if (v.length == 1) {
				return coerceArg(v[0]); // unwrap single element array
			} else {
				// convert string to typed object
				return Arrays.stream(v).map(ve -> coerceArg(ve)).toArray();
			}
		}).toArray();
	}

	/**
	 * invoke a engine level function
	 * 
	 * @param inv
	 * @param func
	 * @param args
	 * @return
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 */
	private static Object invoke(Invocable inv, String func, Object... args)
			throws ScriptException, NoSuchMethodException {
		final long start = System.currentTimeMillis();
		try {
			Object r = inv.invokeFunction(func, args);
			return r;
		} finally {
			System.out.println("executed in " + (System.currentTimeMillis() - start) + " milliseconds");
		}
	}

	private static Object evaluate(ScriptEngine engine, Object url) throws ScriptException, FileNotFoundException {
		final long start = System.currentTimeMillis();
		try {
			if (url instanceof File) {
				return engine.eval(new FileReader((File) url));
			} else if (url instanceof String) {
				return engine.eval((String) url);
			} else
				return null;
		} finally {
			System.out.println("Evaluated in " + (System.currentTimeMillis() - start) + " milliseconds");
		}
	}

	/**
	 * if the string is in "", it'll be unwrapped and be parsed to proper type.
	 * If quoted in single quote, it'll be unwrapped and returned as a String.
	 * 
	 * 
	 * 
	 * @param e
	 * @return
	 */
	public static Object coerceArg(String e) {
		if (!shouldCoerceArg)
			return e;

		if (e.startsWith("\"") && e.endsWith("\"")) {
			// unwrap it
			e = e.substring(1, e.length() - 1);
		}

		if (e.startsWith("\'") && e.endsWith("\'")) {
			return e.substring(1, e.length() - 1);
		}

		if (NumberUtils.isNumber(e)) {
			return NumberUtils.createNumber(e);
		} else {
			try {
				java.util.Date d = AlternativeDateFormat.getDefaultFormatter().parse(e);
				return d;
			} catch (ParseException e1) {
				// ok not a date
				if ("true".equalsIgnoreCase(e)) {
					return Boolean.TRUE;
				} else if ("false".equalsIgnoreCase(e)) {
					return Boolean.FALSE;
				} else
					return e;
			}
		}
	}

	/**
	 * regenerate the model import file
	 * 
	 * @throws IOException
	 */
	private static void _updateModelsHeader() {
		// try in exposed controller method is not good.
		try (FileWriter fw = new FileWriter(new File(MODEL_HEADERS_JS))) {
			List<Class> models = Play.classloader.getAssignableClasses(Model.class);
			models.forEach(model -> {
				String shortName = model.getSimpleName();
				String nameWithPath = model.getCanonicalName();
				try {
					fw.write("var " + shortName + " = Java.type(\"" + nameWithPath + "\");\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void updateModelsHeader() {
		_updateModelsHeader();
		renderText(MODEL_HEADERS_JS + " has been updated");
	}

}