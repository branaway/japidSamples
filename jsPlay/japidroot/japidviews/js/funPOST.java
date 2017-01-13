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
// NOTE: This file was generated from: japidviews/js/funPOST.html
// Change to this file will be lost next time the template file is compiled.
//
public class funPOST extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/js/funPOST.html";
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


	public funPOST() {
	super((StringBuilder)null);
	initHeaders();
	}
	public funPOST(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public funPOST(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/"s", "jso",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"String", "Map<String, Object>",  };
	public static final Object[] argDefaults= new Object[] {null,null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.js.funPOST.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private String s; // line 1, japidviews/js/funPOST.html
	private Map<String, Object> jso; // line 1, japidviews/js/funPOST.html
	public cn.bran.japid.template.RenderResult render(String s,Map<String, Object> jso) {
		this.s = s;
		this.jso = jso;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/js/funPOST.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(String s,Map<String, Object> jso) {
		return new funPOST().render(s, jso);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, funPOST.html
		p("<!DOCTYPE html>\n" + 
"<html>\n" + 
"<head>\n" + 
"<meta charset=\"UTF-8\">\n" + 
"<title>");// line 1, funPOST.html
		p(s);// line 7, funPOST.html
		p("</title>\n" + 
"</head>\n" + 
"<body>\n");// line 7, funPOST.html
		for (String k : jso.keySet()) {// line 10, funPOST.html
		p("    <p><em>");// line 10, funPOST.html
		p(k);// line 11, funPOST.html
		p("</em> : ");// line 11, funPOST.html
		p(jso.get(k));// line 11, funPOST.html
		p("</p> \n");// line 11, funPOST.html
		}// line 12, funPOST.html
		p("</body>\n" + 
"</html>");// line 12, funPOST.html
		
		endDoLayout(sourceTemplate);
	}

}