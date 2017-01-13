package etc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

public class NashornTool {
	public static class FunctionInfo {
		public String name;
		public List<String> parameterNames;
	}

	public static List<FunctionInfo> extractFuncs(String srcName, String sourceCode) {
		Options options = new Options("nashorn");
		options.set("anon.functions", true);
		options.set("parse.only", true);
		options.set("scripting", true);

		ErrorManager errors = new ErrorManager();
		Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
		Source source = Source.sourceFor(srcName, sourceCode);
		Parser parser = new Parser(context.getEnv(), source, errors);
		FunctionNode functionNode = parser.parse();
		Block block = functionNode.getBody();
		List<Statement> statements = block.getStatements();

		return statements.stream().filter(st -> st instanceof VarNode).map(st -> {
			VarNode vn = (VarNode) st;
			if (vn.isFunctionDeclaration()) {
				FunctionInfo fi = new FunctionInfo();
				FunctionNode init = (FunctionNode) vn.getInit();
				String fname = init.getName();
				fi.name = fname;
				List<IdentNode> params = init.getParameters();
				fi.parameterNames = params.stream().map(in -> in.getName()).collect(Collectors.toList());
				return fi;
			} else
				return null;
		}).filter(fi -> fi != null).collect(Collectors.toList());

	}
}
