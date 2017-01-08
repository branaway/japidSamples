package etc;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jdk.nashorn.api.scripting.JSObject;
import play.exceptions.UnexpectedException;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

/**
 * a messenger to carry arguments from JavaScript to Java controller
 * 
 * @author ran
 *
 */
public class RenderJapid{
    public Object[] args;
    public Object arguments;
    
	public RenderJapid(Object o) {
    	this.arguments = o;
    	if (o instanceof JSObject) {
    		JSObject jso = (JSObject)o;
    		Collection<Object> vs = jso.values();
    		args = vs.toArray();
    	}
    	else if(o instanceof Object[]){
    		args = (Object[]) o;
    	}
    	else {
    		args = new Object[]{o};
    	}
    }

	public RenderJapid(Object... o) {
		this.args = o;
	}
}
