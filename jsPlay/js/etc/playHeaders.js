

/**
 * 
 */

var Ok = Java.type("play.mvc.results.Ok");  // k is in lower case;
function ok() {new Ok()}

var RenderText = Java.type("play.mvc.results.RenderText");
function renderText(o) {return new RenderText(o);}

var RenderJson = Java.type("play.mvc.results.RenderJson"); 
function renderJson(o) {return new RenderJson(o);}

var RenderHtml = Java.type("play.mvc.results.RenderHtml"); 
function renderHtml(o) {return new RenderHtml(o);}

var RenderXml = Java.type("play.mvc.results.RenderXml"); 
function renderXml(o) {return new RenderXml(o);}

var RenderFile = Java.type("play.mvc.results.RenderBinary"); 
function renderFile(o) {return new RenderFile(o);}

var Redirect = Java.type("play.mvc.results.Redirect"); 
var RenderError = Java.type("play.mvc.results.Error"); 

var NotFound = Java.type("play.mvc.results.NotFound"); 
function notFound(o) {return new NotFound(o);}

var Forbidden = Java.type("play.mvc.results.Forbidden"); 
function forbidden(o) {return new Forbidden(o);}

var RenderJapid = Java.type("etc.RenderJapid"); 
function renderJapid() {return new RenderJapid(arguments);}

var RenderJackson = Java.type("etc.RenderJackson"); 
function renderJackson(o) {return new RenderJackson(o);}

function renderError(o) {return new RenderError(o);}
function redirect(o) {return new Redirect(o);}

var Request = Java.type("play.mvc.Http.Request"); 
var request = Request.current();

var Flash = Java.type("play.mvc.Scope.Flash"); 
var flash = Flash.current();

var Session = Java.type("play.mvc.Scope.Session"); 
var session = Session.current();



var JavaUtils = Java.type("etc.JavaUtils");


var params = request.params.data; // a Map object

/**
 * parse out function parameter names
 */
var STRIP_COMMENTS = /((\/\/.*$)|(\/\*[\s\S]*?\*\/))/mg;
var ARGUMENT_NAMES = /([^\s,]+)/g;
function getParamNames(func) {
  var fnStr = func.toString().replace(STRIP_COMMENTS, '');
  var result = fnStr.slice(fnStr.indexOf('(')+1, fnStr.indexOf(')')).match(ARGUMENT_NAMES);
  if(result === null)
     result = [];
  return result;
}