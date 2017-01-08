/**
 * 
 */
load('app/js/etc/playHeaders.js');

function GET(request) {
//	print(arguments[1]);
//	return renderText('hi...' + request.url + ". arguments: " + arguments[1]);
//	return ok();
//	return notFound(request.url);
//	return renderJackson({a:1, b:"b-value", c: Date()});
	return renderJapid('hi', {a:1, b:"b-value", c: Date()});
}

