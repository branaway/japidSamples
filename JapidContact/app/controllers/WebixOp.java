package controllers;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import play.data.binding.Binder;
import play.data.parsing.DataParsers;
import play.db.jpa.Model;
import play.mvc.Http.Request;

public  class WebixOp <E extends Model>  {
	private static final String PREFIX = "anything";
	static final String WEBIX_OPERATION = "webix_operation";
	public WebixOp(E entity, String op2) {
		model = entity;
		op = Enum.valueOf(Op.class, op2);
	}
	public E model;
	public Op op;
	static <M extends Model> WebixOp parseRequest (Class<M> clazz, Request request) {
		Map<String, String[]> params = DataParsers.forContentType(request.contentType).parse(request.body);
		
		Map<String, String[]> paramsnorm = new HashMap<>();
		params.entrySet().forEach(en -> {
			if (!en.getKey().equals(WEBIX_OPERATION) && !en.getKey().startsWith(PREFIX + ".")){
				paramsnorm.put(PREFIX + "." + en.getKey(), en.getValue());
			}
			else {
				paramsnorm.put(en.getKey(), en.getValue());
			}
		});
		String op = params.get(WEBIX_OPERATION)[0];
	
		@SuppressWarnings("deprecation")
		Object bind = Binder.bind(PREFIX, clazz, clazz, new Annotation[]{}, paramsnorm);
		WebixOp webix = new WebixOp( (M) bind, op);
		return webix;
	}
	
	public static enum Op {
		insert, delete, update;
	}
}
