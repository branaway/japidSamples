import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class MiscTest{

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testVarParams() {
    	Object[] objects = new Object[]{1, 2, 3};
		takeVar(objects);
    }
    
    @Test
    public void testMap() {
    	Map<String, Object> map = new HashMap<>();
    	map.put("a", 123);
    	map.put("b", 343);
    	String o = map.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining("|"));
    	System.out.println(o);
    }
    
    private void takeVar(Object... ooo){
    	assertEquals(3,  ooo.length);
    }
}
