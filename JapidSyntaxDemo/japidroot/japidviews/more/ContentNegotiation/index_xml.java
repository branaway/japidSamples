//version: 0.9.6.2
package japidviews.more.ContentNegotiation;
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
// NOTE: This file was generated from: japidviews/more/ContentNegotiation/index.xml
// Change to this file will be lost next time the template file is compiled.
//
@cn.bran.play.NoEnhance
public class index_xml extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/more/ContentNegotiation/index.xml";
	 private void initHeaders() {
		putHeader("Content-Type", "text/xml; charset=utf-8");
		setContentType("text/xml; charset=utf-8");
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


	public index_xml() {
	super((StringBuilder)null);
	initHeaders();
	}
	public index_xml(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public index_xml(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/ };
	public static final String[] argTypes = new String[] {/* arg types of the template*/ };
	public static final Object[] argDefaults= new Object[] { };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.more.ContentNegotiation.index_xml.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	public cn.bran.japid.template.RenderResult render() {
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 0, japidviews/more/ContentNegotiation/index.xml
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply() {
		return new index_xml().render();
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
p("<format>\n");// line 1, index.xml
		p(request.format);// line 2, index.xml
		p("\n" + 
"</format>");// line 2, index.xml
		
		endDoLayout(sourceTemplate);
	}

}