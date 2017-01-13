package jsPlay;

import java.util.List;

import org.junit.Test;

import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

public class NashornApi {

	@Test
	public void test() {
		Options options = new Options("nashorn");
		options.set("anon.functions", true);
		options.set("parse.only", true);
		options.set("scripting", true);

		ErrorManager errors = new ErrorManager();
		Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
		Source source   = Source.sourceFor("test", "var a = 10; var b = a + 1;" +
		            "function someFunction() { return b + 1; } \n function foo(a, b) {}");
		Parser parser = new Parser(context.getEnv(), source, errors);
		FunctionNode functionNode = parser.parse();
		Block block = functionNode.getBody();
		List<Statement> statements = block.getStatements();
		for (Statement st : statements) {
			if (st instanceof VarNode) {
				VarNode vn = (VarNode) st;
				if (vn.isFunctionDeclaration()) {
					FunctionNode init = (FunctionNode) vn.getInit();
					String fname = init.getName();
					System.out.print(fname + "->" );
					List<IdentNode> params = init.getParameters();
					params.forEach( in -> 
						System.out.print(in.getName() + ", ")
					);
					System.out.println();
				}
			}
		}
		
		
	}

}
