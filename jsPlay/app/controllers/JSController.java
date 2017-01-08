package controllers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import etc.RenderJackson;
import etc.RenderJapid;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import play.mvc.Http.Request;
import play.mvc.results.RenderJson;
import play.mvc.results.Result;

public class JSController extends cn.bran.play.JapidController {
	private static final String JAVA_IMPORTS = 
			"var RenderText = Java.type(\"play.mvc.results.RenderText\");" + 
//					"var OK = Java.type(\"play.mvc.results.OK\");" +  // class not found for this, why?
					"var RenderJson = Java.type(\"play.mvc.results.RenderJson\");" + 
					"var NotFound = Java.type(\"play.mvc.results.NotFound\");" + 
					"var Request = Java.type(\"play.mvc.Http.Request\");" + 
					"";
	private static ThreadLocal<ScriptEngine> engineHolder = ThreadLocal
			.withInitial(() -> {
//				ScriptEngine engine  = new ScriptEngineManager().getEngineByName("nashorn");
				System.setProperty("nashorn.typeInfo.maxFiles", "20000");
				String[] options = new String[] { "-ot=true", "--language=es6" };
				ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine(options);
//				try {
//					engine.eval(JAVA_IMPORTS);
//				} catch (ScriptException e) {
//					e.printStackTrace();
//				}
				return engine;
			});

	public static void process(String jsfile) throws NoSuchMethodException {
		ScriptEngine engine = engineHolder.get();
		if (jsfile.endsWith(".js"))
			jsfile += jsfile.substring(0, jsfile.lastIndexOf(".js")); // get file name without extension
		try {
			// should cache this
			evaluate(engine, new File("app/js/" + jsfile + ".js"));
			Invocable inv = (Invocable) engine; // should cache this
			Object r = invoke(inv, request.method, request);
			if (r instanceof RenderJapid){
				RenderJapid rj = (RenderJapid)r;
				renderJapidWith("js/" + jsfile + request.method, rj.args);
			}
			else if (r instanceof Result){
				throw (Result)r;
			}
			else{
				renderText(r);
			}
		} catch (FileNotFoundException e) {
			notFound(jsfile);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error(e);
		} 
	}

	private static Object invoke(Invocable inv, String method, Object... args) throws ScriptException, NoSuchMethodException {
		final long start = System.currentTimeMillis();
		try {
		Object r = inv.invokeFunction(method, args);
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

}