package etc;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import play.db.jpa.JPABase;
import play.db.jpa.JPAContext;
import play.exceptions.UnexpectedException;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

/**
 * 200 OK with application/json
 */
public class RenderJackson extends Result {

	static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.addMixIn(JPABase.class, MixIn.class);
	}
	
	static abstract class MixIn {
		  @JsonIgnore abstract public JPAContext getJPAContext();  
	}
	
    public String json;
    public Object object;
    
    public RenderJackson(Object o) {
        try {
        	object = o;
        	json = objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
    }
//
//    public RenderJsonJackson(Object o, Type type) {
//        json = new Gson().toJson(o, type);
//    }

//    public RenderJsonJackson(Object o, JsonSerializer<?>... adapters) {
//        GsonBuilder gson = new GsonBuilder();
//        for (Object adapter : adapters) {
//            Type t = getMethod(adapter.getClass(), "serialize").getParameterTypes()[0];
//            gson.registerTypeAdapter(t, adapter);
//        }
//        json = gson.create().toJson(o);
//    }

    public RenderJackson(String jsonString) {
        json = jsonString;
    }

//    public RenderJsonJackson(Object o, Gson gson) {
//        if (gson != null) {
//            json = gson.toJson(o);
//        } else {
//            json = new Gson().toJson(o);
//        }
//    }

    @Override
    public void apply(Request request, Response response) {
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json; charset=" + encoding);
            response.out.write(json.getBytes(encoding));
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }

//    //
//    static Method getMethod(Class clazz, String methodName) {
//        Method bestMatch = null;
//        for (Method m : clazz.getDeclaredMethods()) {
//            if (m.getName().equals(methodName) && !m.isBridge()) {
//                if (bestMatch == null || !Object.class.equals(m.getParameterTypes()[0])) {
//                    bestMatch = m;
//                }
//            }
//        }
//        return bestMatch;
//    }

}
