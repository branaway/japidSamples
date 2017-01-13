//version: 0.9.6.2
package japidviews.js;
import java.util.*;
import java.io.*;
import cn.bran.japid.tags.Each;
import static cn.bran.play.JapidPlayAdapter.*;
import static play.data.validation.Validation.*;
import static play.templates.JavaExtensions.*;
import play.data.validation.Error;
import play.i18n.Messages;
import play.mvc.Scope.*;
import play.data.validation.Validation;
import play.i18n.Lang;
import controllers.*;
import japidviews._layouts.*;
import models.*;
import play.mvc.Http.*;
//
// NOTE: This file was generated from: japidviews/js/funGET.html
// Change to this file will be lost next time the template file is compiled.
//
public class funGET extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/js/funGET.html";
	 private void initHeaders() {
		putHeader("Content-Type", "text/html; charset=utf-8");
		setContentType("text/html; charset=utf-8");
	}
	{
	}

// - add implicit fields with Play

	final play.mvc.Http.Request request = play.mvc.Http.Request.current(); 
	final play.mvc.Http.Response response = play.mvc.Http.Response.current(); 
	final play.mvc.Scope.Session session = play.mvc.Scope.Session.current();
	final play.mvc.Scope.RenderArgs renderArgs = play.mvc.Scope.RenderArgs.current();
	final play.mvc.Scope.Params params = play.mvc.Scope.Params.current();
	final play.data.validation.Validation validation = play.data.validation.Validation.current();
	final cn.bran.play.FieldErrors errors = new cn.bran.play.FieldErrors(validation);
	final play.Play _play = new play.Play(); 

// - end of implicit fields with Play 


	public funGET() {
	super((StringBuilder)null);
	initHeaders();
	}
	public funGET(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public funGET(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/"s", "jso",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"Integer", "Map<String, Object>",  };
	public static final Object[] argDefaults= new Object[] {null,null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.js.funGET.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private Integer s; // line 1, japidviews/js/funGET.html
	private Map<String, Object> jso; // line 1, japidviews/js/funGET.html
	public cn.bran.japid.template.RenderResult render(Integer s,Map<String, Object> jso) {
		this.s = s;
		this.jso = jso;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/js/funGET.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(Integer s,Map<String, Object> jso) {
		return new funGET().render(s, jso);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, funGET.html
		p("<!DOCTYPE html>\n" + 
"<html>\n" + 
"<head>\n" + 
"<meta charset=\"UTF-8\">\n" + 
"<title>");// line 1, funGET.html
		p(s);// line 7, funGET.html
		p("</title>\n" + 
"</head>\n" + 
"<body>\n");// line 7, funGET.html
		for (String k : jso.keySet()) {// line 10, funGET.html
		p("    <p><em>");// line 10, funGET.html
		p(k);// line 11, funGET.html
		p("</em> : ");// line 11, funGET.html
		p(jso.get(k));// line 11, funGET.html
		p("</p> \n");// line 11, funGET.html
		}// line 12, funGET.html
		p("</body>\n" + 
"</html>");// line 12, funGET.html
		
		endDoLayout(sourceTemplate);
	}

}