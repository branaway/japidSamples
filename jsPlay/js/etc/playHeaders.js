/**
 * 
 */

var Ok = Java.type("play.mvc.results.Ok");  // k is in lower case;
var RenderText = Java.type("play.mvc.results.RenderText");
var RenderJson = Java.type("play.mvc.results.RenderJson"); 
var RenderHtml = Java.type("play.mvc.results.RenderHtml"); 
var RenderXml = Java.type("play.mvc.results.RenderXml"); 
var RenderFile = Java.type("play.mvc.results.RenderBinary"); 
var Redirect = Java.type("play.mvc.results.Redirect"); 
var RenderError = Java.type("play.mvc.results.Error"); 
var NotFound = Java.type("play.mvc.results.NotFound"); 
var RenderJapid = Java.type("etc.RenderJapid"); 
var RenderJackson = Java.type("etc.RenderJackson"); 


function ok() {new Ok()}
function renderJson(o) {return new RenderJson(o);}
function renderText(o) {return new RenderText(o);}
function renderJackson(o) {return new RenderJackson(o);}
function notFound(o) {return new NotFound(o);}
function renderHtml(o) {return new RenderHtml(o);}
function renderXml(o) {return new RenderXml(o);}
function renderFile(o) {return new RenderFile(o);}
function renderError(o) {return new RenderError(o);}
function redirect(o) {return new Redirect(o);}
function renderJapid() {return new RenderJapid(arguments);}

var Request = Java.type("play.mvc.Http.Request"); 

var JavaUtils = Java.type("etc.JavaUtils");

var request = Request.current();

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