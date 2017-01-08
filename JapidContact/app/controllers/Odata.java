package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Book;
import models.Contact;
import play.Logger;
import play.Play;
import play.Play.Mode;
import play.data.binding.Binder;
import play.data.binding.ParamNode;
import play.data.binding.RootParamNode;
import play.data.parsing.DataParser;
import play.data.parsing.DataParsers;
import play.data.parsing.UrlEncodedParser;
import play.data.validation.Valid;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.db.jpa.JPAModelLoader;
import play.db.jpa.Model;
import play.mvc.results.Error;
import play.mvc.results.NotFound;
import play.utils.Utils;
import cn.bran.play.JapidController;

public class Odata extends JapidController {

	private static String regex = "\\bselect\\b|\\bdelete\\b|\\bupdate\\b";
	private static Pattern BadWords = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	public static String paramPatternString = "(\\w+)\\s*\\[(.+)\\]";
	public static Pattern paramsPattern = Pattern.compile(paramPatternString);
	public static String idPatternString = "(\\w+)\\s*\\((.+)\\)";
	public static Pattern idPattern = Pattern.compile(idPatternString);


	/**
	 * return a single object regardless of the result set size
	 * 
	 * @param modelName
	 * @param query
	 */
	public static void getOne(String modelName) {
		List<Object> results = doSerch(modelName, request.querystring);
		if (results.size() > 0) {
			renderJSON(results.get(0));
		} else {
			renderJSON("null"); // "null" will be parsed to an null object
		}
	}

	/**
	 * the result is an array, even if there are no records returned
	 * 
	 * @param modelName
	 * @param query
	 */
	public static void getAll(String modelName) {
		List<Object> results = doSerch(modelName, request.querystring);
		renderJSON(results);
	}

	// the model name field has various formats:
	// model[f1, f2]: select f1, f2 from model
	// model(123), select * from model where id = 123. query string is ignored
	private static List<Object> doSerch(String modelName, String query) {
		try {
			query = URLDecoder.decode(query, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			Logger.error(e1, query);
		}

		if (containsSensitiveWord(modelName) || containsSensitiveWord(query))
			error("query contains sensitive words");

		modelName = modelName.trim();

		List result = caseFindById(modelName);
		if (result != null)
			return result;
		
		
		query = query.trim().toLowerCase();

		Matcher matcher = paramsPattern.matcher(modelName);
		String columns = "";
		if (matcher.matches()) {
			modelName = matcher.group(1);
			columns = matcher.group(2).trim();
		}

		if (!modelName.startsWith("models.")) {
			modelName = "models." + modelName;
		}

		try {
			Class<Model> clazz = (Class<Model>) Play.classloader.loadClass(modelName);
			String qs = (columns.length() == 0 ? "" : "select " + columns) + " from " + clazz.getName() + "";
			if (query.startsWith("order by ") || query.startsWith("where ") || query.startsWith("group by ")
					|| query.startsWith("having ")) {
				qs += " " + query;
			} else if (query.length() > 0) {
				qs += " where " + query;
			}

			Query q = JPABase.getJPAConfig(clazz).getJPAContext().em().createQuery(qs);
			List results = q.getResultList();
			//
			// List<? extends GenericModel> results =
			// JPABase.getJPAConfig(clazz).jpql.findBy(clazz.getName(), query,
			// new Object[] {});
			if (columns.length() > 0) {
				// parse the columns
				String[] split = columns.split(",");
				Object collect = results.stream().map(line -> {
					Object[] l = (Object[]) line;
					if (l.length != split.length) {
						String reason = "实际得到的数据宽度不等于选择的数据列数";
						Logger.error(reason);
						throw new play.mvc.results.Error(reason);
					} else {

						// use LinkeHashMap to keep the column order
						Map<String, Object> row = new LinkedHashMap<>(); 
						for (int i = 0; i < l.length; i++) {
							row.put(split[i].trim(), l[i]);
						}
						return row;
					}
				}).collect(Collectors.toList());
				return (List) collect;
			} else {
				return results;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();

			ByteArrayOutputStream bos = new ByteArrayOutputStream(200);
			PrintStream ps = new PrintStream(bos);
			cause.printStackTrace(ps);
			String msg = bos.toString();
			Logger.error(cause.getMessage());
			if (Play.mode == Mode.DEV)
				error(cause.getMessage());
			else
				error(e.getMessage());
			// String msg = e.getCause() != null ? e.getCause().getMessage() :
			// e.getMessage();
		}
		return null;
	}

	private static List caseFindById(String modelName) {
		// the simple find by id case: model(id)
		Matcher matchId = idPattern.matcher(modelName);
		if (matchId.matches()) {
			modelName = matchId.group(1);
			String ids = matchId.group(2).trim();
			try {
				Long id = Long.parseLong(ids);
				if (!modelName.startsWith("models.")) {
					modelName = "models." + modelName;
				}
				Class<Model> clazz = (Class<Model>) Play.classloader.loadClass(modelName);
				Model target = JPABase.getJPAConfig(clazz).jpql.findById(clazz, id);
				return target!= null ? Arrays.asList(target) : Collections.emptyList();
			}catch(NumberFormatException nfe){
				error("the id is not a number: " + ids);
			} catch (ClassNotFoundException e) {
				error("the model name is not found: " + modelName);
			}
		}
		return null;
	}

	private static boolean containsSensitiveWord(String query) {
		if (BadWords.matcher(query).find())
			return true;
		return false;
	}

	/**
	 * to process post data by webix DataProcessor,
	 * 
	 * @see http://docs.webix.com/desktop__custom_serverside.html
	 */
	public static void saveRecord(String modelName) {
		if (!modelName.startsWith("models.")) {
			modelName = "models." + modelName;
		}

		try {
			Class<Model> clazz = (Class<Model>) Play.classloader.loadClass(modelName);

			WebixOp webix = WebixOp.parseRequest(clazz, request);

			Model m = webix.model;

			switch (webix.op) {
			case insert:
				m.create();
				renderJSON(m);//
				break;
			case update:
				m.save();
				ok();
				break;
			case delete:
				m.delete();
				ok();
				break;
			}
		} catch (Exception e) {
			Logger.error(e.getMessage());
			notFound();
		}

	}

	public static void saveBook(Book book) {
		book.save();
		ok();
	}

	public static void dtGetREst() {

		List<Book> books = Book.find("order by rank").fetch();
		renderJSON(books);
	}

	public static void dtSaveRest() {
		Map<String, String[]> params = DataParsers.forContentType(request.contentType).parse(request.body);
		params.entrySet().forEach(en -> System.out.println(en.getKey() + ":" + Utils.join(en.getValue(), ",")));
		ok();

	}
}