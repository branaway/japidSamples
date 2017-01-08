package bran;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import controllers.Application;

public class MiscTests {

	@Test
	public void test() {
		String regex = "\\bselect\\b|\\bdelete\\b|\\bupdate\\b";
		 
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher matcher = pattern.matcher("i select you");
		assertFalse(matcher.matches());
		assertTrue(matcher.find());
		
		matcher = pattern.matcher("select you");
		assertTrue(matcher.find());
		
		matcher = pattern.matcher("I SELECT you");
		assertTrue(matcher.find());
		
		matcher = pattern.matcher("I deleTE you");
		assertTrue(matcher.find());
		
		matcher = pattern.matcher("UPdate you");
		assertTrue(matcher.find());
		
		matcher = pattern.matcher("I UPdated you");
		assertFalse(matcher.find());
		
		matcher = pattern.matcher("I selected(you");
		assertFalse(matcher.find());

		matcher = pattern.matcher("I select(you");
		assertTrue(matcher.find());
		

	}
	
	@Test public void testModelParam() {
		String paramPatter = "(\\w+)\\s*\\[(.+)\\]";
		Pattern pattern = Pattern.compile(paramPatter);
		 Matcher matcher = pattern.matcher("user[name, age]");
		 assertTrue(matcher.matches());
		 assertEquals("user", matcher.group(1));
		 assertEquals("name, age", matcher.group(2));
		 
		 
	}

//	@Test public void testIdParam() {
//		Pattern pattern = Application.idPattern;
//		Matcher matcher = pattern.matcher("user ( 123)");
//		assertTrue(matcher.matches());
//		assertEquals("user", matcher.group(1));
//		assertEquals("123", matcher.group(2).trim());
//		
//		
//	}
	
	@Test public void testLinkedHashMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", "1");
		map.put("title", "3");
		map.put("year", null);
		System.out.println(map);
		Object[] array = map.keySet().toArray(new Object[3]);
		assertEquals("id", array[0]);
		assertEquals("title", array[1]);
		assertEquals("year", array[2]);
		 
	}
	
	
}
