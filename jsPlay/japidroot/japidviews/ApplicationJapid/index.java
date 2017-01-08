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
// NOTE: This file was generated from: japidviews/ApplicationJapid/index.html
// Change to this file will be lost next time the template file is compiled.
//
public class index extends main
{
	public static final String sourceTemplate = "japidviews/ApplicationJapid/index.html";
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


	public index() {
	super((StringBuilder)null);
	initHeaders();
	}
	public index(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public index(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/"name", "age",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"String", "int",  };
	public static final Object[] argDefaults= new Object[] {null,null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.ApplicationJapid.index.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private String name; // line 1, japidviews/ApplicationJapid/index.html
	private int age; // line 1, japidviews/ApplicationJapid/index.html
	public cn.bran.japid.template.RenderResult render(String name,int age) {
		this.name = name;
		this.age = age;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/ApplicationJapid/index.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(String name,int age) {
		return new index().render(name, age);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, index.html
		;// line 1, index.html
p("\n" + 
"<p>Hi, ");// line 3, index.html
		new show(index.this).render(name,age); // line 5, index.html// line 5, index.html
		p("</p>\n" + 
"<p>The classic Groovy-based system is <a href=\"");// line 5, index.html
		p(lookup("Application.index", new Object[]{}));// line 6, index.html
		p("\">here</a>.");// line 6, index.html
		
		endDoLayout(sourceTemplate);
	}

	@Override protected void title() {
		p("Japid Sample");;
	}
}