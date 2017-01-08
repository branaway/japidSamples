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
// NOTE: This file was generated from: japidviews/Application/getRecords.html
// Change to this file will be lost next time the template file is compiled.
//
public class getRecords extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/Application/getRecords.html";
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


	public getRecords() {
	super((StringBuilder)null);
	initHeaders();
	}
	public getRecords(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public getRecords(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/ };
	public static final String[] argTypes = new String[] {/* arg types of the template*/ };
	public static final Object[] argDefaults= new Object[] { };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.Application.getRecords.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	public cn.bran.japid.template.RenderResult render() {
		try {super.layout();} catch (RuntimeException __e) { super.handleException(__e);} // line 0, japidviews/Application/getRecords.html
		return getRenderResult();
	}

	public static cn.bran.japid.template.RenderResult apply() {
		return new getRecords().render();
	}

	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
p("[\n" + 
"	{ \"id\":1, \"title\":\"The Shawshank Redemption救赎\", \"year\":1994, \"votes\":678790, \"rating\":9.2, \"rank\":1},\n" + 
"	{ \"id\":2, \"title\":\"The Godfather\", \"year\":1972, \"votes\":511495, \"rating\":9.2, \"rank\":2},\n" + 
"	{ \"id\":3, \"title\":\"The Godfather: Part II\", \"year\":1974, \"votes\":319352, \"rating\":9.0, \"rank\":3},\n" + 
"	{ \"id\":4, \"title\":\"The Good, the Bad and the Ugly\", \"year\":1966, \"votes\":213030, \"rating\":8.9, \"rank\":4},\n" + 
"	{ \"id\":5, \"title\":\"My Fair Lady\", \"year\":1964, \"votes\":533848, \"rating\":8.9, \"rank\":5},\n" + 
"	{ \"id\":6, \"title\":\"12 Angry Men\", \"year\":1957, \"votes\":164558, \"rating\":8.9, \"rank\":6}\n" + 
"]\n");// line 1, getRecords.html
		
		endDoLayout(sourceTemplate);
	}

}