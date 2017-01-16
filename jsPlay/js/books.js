'use strict';
load('js/fun.js');

// the name must match that of this source file
var books = function() {

	function all() {
		var books = Book.findAll();
		return renderJson(books);
	}

	function newBook(title, year) {
		var book = new Book();
		if (title)
			book.title = title;
		if(year)
			book.year = year;
		
		book.save();

		return renderJson({
			errorcode : 0,
			data : book
		});
	}

	return {
		all : all,
		getBookById : function(id) {
//			return foo(id);
			var book = Book.findById(id);
			if (book)
				return renderJapid(1, book); //the template is"japidroot/japidviews/js/books/getBookById.html"
			else
				return renderText("ooops!");
		},
		newBook : newBook,
		optionCoerceArgs : true, // not used. use
									// jscontroller.coerce.args=true in
									// application.conf
		/**
		 * 特殊interceptor function， 在真正函数调用之前被调用。 常用于安全检查。 如果欲立刻返回给用户错误信息
		 * 可以简单粗暴返回 return Forbidden()。 也可以直接调用另外的函数， 例如 login（）。
		 * 如果不想中断目标函数的访问，不要调用任何 return 语句。
		 */
		_before : function(functionName) {
			// do nothing to pass through
			// or return something to reflow the action chain
//			 return forbidden(functionName);
//			return renderText("you don't have the right: " + functionName);
		}

	}
}();
