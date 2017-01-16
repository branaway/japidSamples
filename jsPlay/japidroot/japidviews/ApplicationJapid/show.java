//version: 0.9.6.2
package japidviews.ApplicationJapid;
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
// NOTE: This file was generated from: japidviews/ApplicationJapid/show.html
// Change to this file will be lost next time the template file is compiled.
//
public class show extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/ApplicationJapid/show.html";
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


	public show() {
	super((StringBuilder)null);
	initHeaders();
	}
	public show(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public show(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/"name", "age",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"String", "int",  };
	public static final Object[] argDefaults= new Object[] {null,null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.ApplicationJapid.show.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private String name; // line 1, japidviews/ApplicationJapid/show.html
	private int age; // line 1, japidviews/ApplicationJapid/show.html
	public cn.bran.japid.template.RenderResult render(String name,int age) {
		this.name = name;
		this.age = age;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/ApplicationJapid/show.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(String name,int age) {
		return new show().render(name, age);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, show.html
		p("I'm ");// line 1, show.html
		p(name);// line 2, show.html
		p(". I'm ");// line 2, show.html
		try { Object o = escape(age); if (o.toString().length() ==0) { p(escape(null)); } else { p(o); } } catch (NullPointerException npe) { p(escape(null)); }// line 2, show.html
		p("-year old.");// line 2, show.html
		
		endDoLayout(sourceTemplate);
	}

}