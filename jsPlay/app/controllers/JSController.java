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
import java.util.stream.Collectors;

import javax.script.Compilable;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.math.NumberUtils;

import etc.NashornExecutionException;
import etc.NashornTool;
import etc.NashornTool.FunctionInfo;
import etc.RenderJackson;
import etc.RenderJapid;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.Undefined;
import play.Play;
import play.Play.Mode;
import play.db.jpa.Model;
import play.exceptions.CompilationException;
import play.mvc.Http.Request;
import play.mvc.Scope.Params;
import play.mvc.results.RenderJson;
import play.mvc.results.Result;
import play.utils.Utils;
import play.utils.Utils.AlternativeDateFormat;
import play.vfs.VirtualFile;

public class JSController extends cn.bran.play.JapidController {
	private static final String _PARAMS = "_params";
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
	 * 
	 * @param _module
	 *            the js file that defines a module.
	 * @param _method
	 *            the method of the module to invoke
	 * @throws NoSuchMethodException
	 */
	public static void process(String _module, String _method) throws NoSuchMethodException {
		
		ScriptEngine engine = engineHolder.get();
		if (_module.endsWith(".js"))
			_module += _module.substring(0, _module.lastIndexOf(".js"));
		// get
		// file
		// name
		// without
		// extension
		String fileName = jsRoot + "/" + _module + ".js";
		File rawFile = new File(fileName);
		if (!rawFile.exists()) {
			notFound(fileName);
		}

		try {

			JSObject module = getModule(_module, engine, rawFile);

			// not implemented yet
			// Object optionCoerceArgsMember =
			// var.getMember("optionCoerceArgs");

			// method one:
			Object member = module.getMember(_method);
			if (member instanceof Undefined) {
				notFound("methd not found in the module: " + _module + "." + _method);
			}
			
			Object before = module.getMember("_before");
			if (before instanceof JSObject) {
				// call the interceptor with the method name
				Object itorResult = ((JSObject)before).call(null, _method);
				if (itorResult instanceof RenderJapid) {
					RenderJapid rj = (RenderJapid) itorResult;
					renderJapidWith(jsRoot + "/" + _module + "/" + "_before", rj.args);
				} else if (itorResult instanceof Result) {
					throw (Result) itorResult;
				}
			}
			
//			Object[] args = processParams((FunctionInfo) engine.get(_module + "." + _method));
			Object[] args = processParams((FunctionInfo) module.getMember(_method + _PARAMS));

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
			convertToPlayCompilationError(fileName, e);
		} catch(ECMAException e) {
//			Object t = e.getThrown();
			convertToPlayCompilationError(fileName, e);
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
			// need to parser the line like:
			// 	at jdk.nashorn.internal.scripts.Script$Recompilation$24$535A$\^eval\_.books$getBookById-1(<eval>:32)
			List<StackTraceElement> goodLines = Arrays.stream(e.getStackTrace())
			.filter( st -> st.toString().contains("scripts.Script$"))
			.collect(Collectors.toList());
			if (goodLines.size() > 0) {
				StackTraceElement ste = goodLines.get(0);
				Integer lineNum = ste.getLineNumber();
				String fname = ste.getFileName();
				if ("<eval>".equals(fname)){
					fname = fileName;
				}
				String tempName = fname; 
				VirtualFile vf = VirtualFile.fromRelativePath(tempName);
				NashornExecutionException ce = new NashornExecutionException(vf, "\"" + e.getMessage() + "\"", lineNum, 0, 0);
				throw ce;
			}
			else {
				throw e;
			}
		}
	}

	private static void convertToPlayCompilationError(String fileName, ECMAException e) {
		String fname = e.getFileName();
		if ("<eval>".equals(fname)){
			fname = fileName;
		}
		int line = e.getLineNumber();
		int col = e.getColumnNumber();
//			Object ecmaError = e.getEcmaError();
		String tempName = fname; 
		VirtualFile vf = VirtualFile.fromRelativePath(tempName);
		NashornExecutionException ce = new NashornExecutionException(vf, "\"" + e.getMessage() + "\"", line, col, col + 1);
		throw ce;
	}

	private static void convertToPlayCompilationError(String fileName, ScriptException e) {
		String fname = e.getFileName();
		if ("<eval>".equals(fname)){
			fname = fileName;
		}
		int line = e.getLineNumber();
		int col = e.getColumnNumber();
//			Object ecmaError = e.getEcmaError();
		String tempName = fname; 
		VirtualFile vf = VirtualFile.fromRelativePath(tempName);
		CompilationException ce = new CompilationException(vf, "\"" + e.getMessage() + "\"", line, col, col + 1);
		throw ce;
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
			if (modelsFile.exists()) {
//				engine.eval(new FileReader(modelsFile));
				// let's see if we can keep the file name in the compiled classes 
				engine.eval("load('" + modelsFile + "');");
			}
			// parse the header
//			engine.eval(new FileReader(PLAY_HEADERS_JS));
			engine.eval("load('" + PLAY_HEADERS_JS  + "');");
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
				if (funcs.size() > 0) {
					FunctionInfo fi = funcs.get(0);
					// store the method signature in the engine scope
					// key by <modulename>.<method name>
					module.setMember(k + _PARAMS, fi);
//					engine.put(moduleName + "." + k, fi);
				}
				else{
					// might be an inline function
					FunctionInfo extractAnonymous = NashornTool.extractAnonymous(funcSource);
					if (extractAnonymous != null){
						module.setMember(k + _PARAMS, extractAnonymous);
					}
					else {
						throw new RuntimeException("could not identify the parameter pattern: " + funcSource);
					}
				}
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
//				return engine.eval(new FileReader((File) url));
				return engine.eval("load('" + ((File) url).getPath() + "');");
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