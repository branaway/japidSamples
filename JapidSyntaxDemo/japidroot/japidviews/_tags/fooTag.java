//version: 0.9.6.2
package japidviews._tags;
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
// NOTE: This file was generated from: japidviews/_tags/fooTag.html
// Change to this file will be lost next time the template file is compiled.
//
@cn.bran.play.NoEnhance
public class fooTag extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/_tags/fooTag.html";
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


	public fooTag() {
	super((StringBuilder)null);
	initHeaders();
	}
	public fooTag(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public fooTag(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/"well",  };
	public static final String[] argTypes = new String[] {/* arg types of the template*/"String",  };
	public static final Object[] argDefaults= new Object[] {null, };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews._tags.fooTag.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	{ setHasDoBody(); }
	private String well; // line 1, japidviews/_tags/fooTag.html
public cn.bran.japid.template.RenderResult render(DoBody body, cn.bran.japid.compiler.NamedArgRuntime... named) {
    Object[] args = buildArgs(named, body);
    try {return runRenderer(args);} catch(RuntimeException e) {handleException(e); throw e;} // line 1, japidviews/_tags/fooTag.html
}

	private DoBody body;
public static interface DoBody {
		void render();
		void setBuffer(StringBuilder sb);
		void resetBuffer();
}
 String renderBody() {
		StringBuilder sb = new StringBuilder();
		if (body != null){
			body.setBuffer(sb);
			body.render();
			body.resetBuffer();
		}
		return sb.toString();
	}
	public cn.bran.japid.template.RenderResult render(String well, DoBody body) {
		this.body = body;
		this.well = well;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/_tags/fooTag.html
		return getRenderResult();
	}
	public cn.bran.japid.template.RenderResult render(String well) {
		this.well = well;
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 1, japidviews/_tags/fooTag.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply(String well) {
		return new fooTag().render(well);
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
;// line 1, fooTag.html
		p("\n" + 
"<span>something</span>\n" + 
"\n");// line 1, fooTag.html
		if (body != null){ body.setBuffer(getOut()); body.render(); body.resetBuffer();}// line 5, fooTag.html
		p(" ");// line 5, fooTag.html
		
		endDoLayout(sourceTemplate);
	}

}