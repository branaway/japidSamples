//version: 0.9.6.2
package japidviews.Application;
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
// NOTE: This file was generated from: japidviews/Application/index.html
// Change to this file will be lost next time the template file is compiled.
//
public class index extends main
{
	public static final String sourceTemplate = "japidviews/Application/index.html";
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
	public static final String[] argNames = new String[] {/* args of the template*/"now",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"Date",  };
	public static final Object[] argDefaults= new Object[] {null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.Application.index.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	private Date now; // line 1, japidviews/Application/index.html
	public cn.bran.japid.template.RenderResult render(Date now) {
		this.now = now;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/Application/index.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(Date now) {
		return new index().render(now);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, index.html
		;// line 1, index.html
p("\n" + 
"\n" + 
"<p id=\"time\">\n" + 
"    <span>");// line 3, index.html
		p(format(now, "EEEE',' MMMM dd',' yyyy"));// line 7, index.html
		p("</span>\n" + 
"    ");// line 7, index.html
		p(format(now, "hh'h' MM'mn' ss's'"));// line 8, index.html
		p("\n" + 
"</p>\n" + 
"\n" + 
"<script type=\"text/javascript\" charset=\"utf-8\">\n" + 
"    setInterval(function() {\n" + 
"        $('section').load('");// line 8, index.html
		p(lookup("index", new Object[]{}));// line 13, index.html
		p(" #time')\n" + 
"    }, 1000)\n" + 
"</script>");// line 13, index.html
		
		endDoLayout(sourceTemplate);
	}

	@Override protected void title() {
		p("Home Page");;
	}
}