function foo(id) {
	var book = Book.findById(id);
	if (book)
		return renderJson(book);
	else
		return notFound(id);
}