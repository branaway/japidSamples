function foo(id) {
	var book = Book.findById(id);
	if (book)
		return renderJapid(0, book); //the template is"japidroot/japidviews/js/books/getBookById.html"
	else
		return renderText("ooops!");
}