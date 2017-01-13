/**
 * 
 */


// the name must match that of this source file
var books = function() {

	function all() {
		var books = Book.findAll();
		return renderJson(books);
	}
	

	function getBookById(id){
		var book = Book.findById(id);
		if (book)
			return renderJson(book);
		else
			return notFound(id);
	}
	
	function newBook(title, year){
		var book = new Book();
		book.title = title;
		book.year = year;
		book.save();
		return renderJson({errorcode: 0, data: book});
	}
	
	return {
		all: all,
		getBookById:getBookById,
		newBook: newBook,
		optionCoerceArgs: true // not used. use jscontroller.coerce.args=true in application.conf
	}
}();
