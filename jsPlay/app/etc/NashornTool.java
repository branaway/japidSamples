package etc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.ExpressionStatement;
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
	
	private static String ANON_FUNC = "\\s*function\\s*\\(.*\\).*";
	static Pattern PA = Pattern.compile(ANON_FUNC, Pattern.DOTALL);
	
	
	public static class FunctionInfo {
		public String name;
		public List<String> parameterNames;
		
		@Override
		public String toString(){
			return name + ":" + parameterNames;
		}
	}

	/**
	 * extract top level function definitions. 
	 * 
	 * @param srcName
	 * @param sourceCode
	 * @return
	 */
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

		return statements.stream().filter(
				st -> 
				st instanceof VarNode
				).map(st -> {
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
	
	public static FunctionInfo extractAnonymous(String src){
		Matcher m = PA.matcher(src);
		if (!m.matches())
			return null;
		
		Options options = new Options("nashorn");
		options.set("anon.functions", true);
		options.set("parse.only", true);
		options.set("scripting", true);

		ErrorManager errors = new ErrorManager();
		Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
		Source source = Source.sourceFor("anon", src);
		Parser parser = new Parser(context.getEnv(), source, errors);
		FunctionNode functionNode = parser.parse();
		Block block = functionNode.getBody();
		List<Statement> statements = block.getStatements();
		if (statements.size() > 0) {
			Statement stmt = statements.get(0);
			if(stmt instanceof ExpressionStatement) {
				ExpressionStatement exst = (ExpressionStatement) stmt;
				Expression expr = exst.getExpression();
				if (expr instanceof FunctionNode) {
					FunctionNode fn = (FunctionNode) expr;
					FunctionInfo fi = new FunctionInfo();
					fi.name = "<anonymous>";
					List<IdentNode> params = fn.getParameters();
					fi.parameterNames = params.stream().map(in -> in.getName()).collect(Collectors.toList());
					return fi;				
				}
				return null;
			}
	
		}
		return null;
	
	}
	
	public static String getAst(String code){
		Options options = new Options("nashorn");
		options.set("anon.functions", true);
		options.set("parse.only", true);
		options.set("scripting", true);

		ErrorManager errors = new ErrorManager();
		Context contextm = new Context(options, errors, Thread.currentThread().getContextClassLoader());
		Context.setGlobal(contextm.createGlobal());
		String json = ScriptUtils.parse(code, "<unknown>", false);
		return json;
	}
}
