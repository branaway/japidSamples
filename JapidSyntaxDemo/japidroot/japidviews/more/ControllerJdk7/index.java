//version: 0.9.6.2
package japidviews.more.ControllerJdk7;
import java.util.*;
import java.io.*;
import cn.bran.japid.tags.Each;
import static cn.bran.play.JapidPlayAdapter.*;
import static play.data.validation.Validation.*;
import static play.templates.JavaExtensions.*;
import play.data.validation.Error;
import play.i18n.Messages;
import play.mvc.Scope.*;
import japidviews._tags.*;
import play.data.validation.Validation;
import play.i18n.Lang;
import controllers.*;
import japidviews._layouts.*;
import models.*;
import play.mvc.Http.*;
//
// NOTE: This file was generated from: japidviews/more/ControllerJdk7/index.html
// Change to this file will be lost next time the template file is compiled.
//
@cn.bran.play.NoEnhance
public class index extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/more/ControllerJdk7/index.html";
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
	public static final String[] argNames = new String[] {/* args of the template*/"m",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"String",  };
	public static final Object[] argDefaults= new Object[] {null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.more.ControllerJdk7.index.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private String m; // line 1, japidviews/more/ControllerJdk7/index.html
	public cn.bran.japid.template.RenderResult render(String m) {
		this.m = m;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/more/ControllerJdk7/index.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(String m) {
		return new index().render(m);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, index.html
		p("m: ");// line 1, index.html
		try { Object o = m ; if (o.toString().length() ==0) { p("nothing"); } else { p(o); } } catch (NullPointerException npe) { p("nothing"); }// line 3, index.html
		p("\n" + 
"\n");// line 3, index.html
		p("\n");// line 15, index.html
		//reverse: @@{Pages.edit()}// line 17, index.html
		p("ok\n");// line 17, index.html
		
		endDoLayout(sourceTemplate);
	}

}