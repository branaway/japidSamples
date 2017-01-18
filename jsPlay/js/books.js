'use strict';
load('js/fun.js');

// the name must match that of this source file
var books = function() {
	return {
		
		/**
		 * the default function
		 */
		index: function() {
			return new File('readme.txt')
		},
		
		/**
		 * path: http://localhost:9000/js/books/all
		 */
		all : function() {
			var books = Book.find("order by year", []).fetch();
			return books;
		},

		/**
		 * path: http://localhost:9000/js/books/some?howMany=3
		 */
		some : function(howMany) {
			var jpaQuery = JPA.find(Book.class);
			if (howMany) {
				var books = jpaQuery.fetch(howMany)
				return {
					errorcode : 0,
					data : books
				}
			} else {
				return {
					errorcode : 1,
					msg : "howMany is not defined"
				}
			}
		},

		/**
		 * path: http://localhost:9000/js/books/getBookById?id=3
		 */
		getBookById : function(id) {
			// return foo(id); // 可以调用 ‘js/fun.js' 定义的函数
			var book = Book.findById(id);
			if (book)
				// return renderJapid(0, book); 
				//the template
				// is"japidroot/japidviews/js/books/getBookById.html"
				return book;
//				return id;
			else
				return "ooops!";
		},
		
		/**
		 * path: http://localhost:9000/js/books/newBook?title=My Book&year=1966
		 * or use curl: curl --form title="My Book" --form year="1966" http://localhost:9000/js/books/newBook
		 */
		newBook : function (title, year) {
			var book = new Book();
			if (title)
				book['title'] = title; // set on an attribute
			if (year)
				book.year = year; // as direct javabean

			book.save();

			return book;
		},
		
		/**
		 * sample path: 
		 * http://localhost:9000/js/books/newBook2?jsonData={"title":"my book", "year":1999}
		 * or use curl: curl --form title="My Book" --form year="1966" http://localhost:9000/js/books/newBook
		 */
		newBook2 : function (jsonData) {
			var book = new Book();
			var json = JSON.parse(jsonData)
			for (var p in json) {
				book[p] = json[p]
			}

			book.save();
			
			return book;
		},
		
		/**
		 * path: http://localhost:9000/js/books/getFile?name=/js/books.js
		 */
		getFile : function(name) {
			return new File(name)
		},
		
		/**
		 * path: http://localhost:9000/js/books/jpa?year=1956
		 */
		jpa: function(year) {
			// the findBy return all data immediately
			var books = JPA.findBy(Book.class, "select title, year, rank from Book where year > ?1 order by year", year);
			return books;
		},

		/**
		 * path: http://localhost:9000/js/books/jpa2?fromRow=1&maxResults=2
		 */
		jpa2: function(fromRow, maxResults) {
			// the find return a query which can be set limit the returned data
			var query = JPA.find(Book.class, "select title, author.name from Book");
			return query.from(fromRow).fetch(maxResults);
		},
		
		/**
		 * path: http://localhost:9000/js/books/jpa3
		 */
		jpa3: function() {
			// would generate the all the combination of the two field values
			return JPA.findBy(Book.class, "select b.title, c.name from Book b, Contact c");
		},
		
		/**
		 * 特殊interceptor function， 在真正函数调用之前被调用。 常用于安全检查。 如果欲立刻返回给用户错误信息
		 * 可以简单粗暴返回 return Forbidden()。 也可以直接调用另外的函数， 例如 login（）。
		 * 如果不想中断目标函数的访问，不要调用任何 return 语句。
		 */
		_before : function(functionName) {
			// do nothing to pass through
			// or return something to reflow the action chain
			// return forbidden(functionName);
			// return renderText("you don't have the right: " + functionName);
		}
	}
}();
