//version: 0.9.6.2
package japidviews._layouts;
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
// NOTE: This file was generated from: japidviews/_layouts/main.html
// Change to this file will be lost next time the template file is compiled.
//
public abstract class main extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/_layouts/main.html";
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


	public main() {
	super((StringBuilder)null);
	initHeaders();
	}
	public main(StringBuilder out) {
		super(out);
		initHeaders();
	}
	public main(cn.bran.japid.template.JapidTemplateBaseWithoutPlay caller) {
		super(caller);
	}

	@Override public void layout() {
		beginDoLayout(sourceTemplate);
p("<!DOCTYPE html>\n" + 
"<html>\n" + 
"    <head>\n" + 
"    	<title>");// line 1, main.html
		title();p(", by Bing Ran</title>\n" + 
"    	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" + 
"    </head>\n" + 
"	<body>\n" + 
"	<h3>This is a <a href=\"https://github.com/branaway/japid\">Japid</a>-based rendering system.</h3>\n" + 
"	");// line 4, main.html
		doLayout();// line 9, main.html
		p("	<h3>footer</h3>\n" + 
"	</body>\n" + 
"</html>\n");// line 9, main.html
		
		endDoLayout(sourceTemplate);
	}

	 protected void title() {};

	protected abstract void doLayout();
}