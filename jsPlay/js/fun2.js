/**
 * 
 */


// the name must match that of this source file
var fun2 = function() {

	function GET(p1, p2, p3) {
		print('p1 -> ' + p1);
		print('p2 -> ' + p2);
		print('p3 -> ' + p3);
	 return renderText('hi...' + request.url + ". arguments: " + arguments[1]);
	// return ok();
//	 return notFound(request.url);
//	 return renderJackson({a:1, b:"b-value", c: Date()});
//		return renderJapid(p1, {a:1, b:p2, c: Date()});
	}
	
	/**
	 * curl -v -# -o output -w %{speed_upload}:%{time_total} --form
	 * "attachment=@2.jpg" --form comment="check this out"
	 * http://localhost:9000/js/fun && cat output
	 */
	function POST() {
	// for each (var k in params.keySet()) {
	// print(k + ':' + params.get(k)[0]);
	// }
		// I know the attachment is a file, so I locate it with a utility tool
		let file = JavaUtils.bindFile("attachment");
		if (file != null){
			print(file.length() + ":" + file.absolutePath);
			file.delete();
		}
		
	// print(arguments[1]);
	// return renderText('hi...' + request.url + ". arguments: " + arguments[1]);
	// return ok();
	// return notFound(request.url);
	// return renderJackson({a:1, b:"b-value", c: Date()});
		return renderJapid(params.get("attachment")[0], {b:params.get("comment")[0], c: Date()});
	}
	
	return {
		GET: GET,
		POST: POST,
		coerceArgs: true
	}
}();
