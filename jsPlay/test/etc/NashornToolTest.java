package etc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import etc.NashornTool.FunctionInfo;

public class NashornToolTest {

	@Test
	public void testScript1() {
		List<FunctionInfo> pss = NashornTool.extractFuncs("test", "var a = 10; var b = a + 1;" +
	            "function someFunction() { return b + 1; } \n function foo(a, b) {}");
		assertEquals(2, pss.size());
		assertEquals("someFunction", pss.get(0).name);
		assertEquals(0, pss.get(0).parameterNames.size());
		assertEquals("foo", pss.get(1).name);
		assertEquals("a", pss.get(1).parameterNames.get(0));
		assertEquals("b", pss.get(1).parameterNames.get(1));
	}

	@Test
	public void testScript2() {
		List<FunctionInfo> pss = NashornTool.extractFuncs("s2", "function foo(a, b){}");
		assertEquals(1, pss.size());
		assertEquals("foo", pss.get(0).name);
		assertEquals("a", pss.get(0).parameterNames.get(0));
		assertEquals("b", pss.get(0).parameterNames.get(1));
	}
	
}
