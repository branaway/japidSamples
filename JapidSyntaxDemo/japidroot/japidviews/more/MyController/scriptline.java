//version: 0.9.6.2
package japidviews.more.MyController;
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
// NOTE: This file was generated from: japidviews/more/MyController/scriptline.html
// Change to this file will be lost next time the template file is compiled.
//
@cn.bran.play.NoEnhance
public class scriptline extends scriptlineLayout
{
	public static final String sourceTemplate = "japidviews/more/MyController/scriptline.html";
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


	public scriptline() {
	super((StringBuilder)null);
	initHeaders();
	}
	public scriptline(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public scriptline(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/ };
	public static final String[] argTypes = new String[] {/* arg types of the template*/ };
	public static final Object[] argDefaults= new Object[] { };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.more.MyController.scriptline.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	public cn.bran.japid.template.RenderResult render() {
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 0, japidviews/more/MyController/scriptline.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply() {
		return new scriptline().render();
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, scriptline.html
p("\n" + 
"hello ");// line 2, scriptline.html
		new Tag2(scriptline.this).render(named("msg", "123")); // line 4, scriptline.html// line 4, scriptline.html
		p(" a  ");// line 4, scriptline.html
		new Tag2(scriptline.this).render(named("msg", "456")); // line 4, scriptline.html// line 4, scriptline.html
		p("!\n" + 
"this is how to print a single back quote: ");// line 4, scriptline.html
		p('`');// line 5, scriptline.html
		p(" or `");// line 5, scriptline.html
		
		endDoLayout(sourceTemplate);
	}

	@Override protected void meta() {
		p("holy meta");;
	}
}