package AST;
import Parser.Parser;

public class ASTBuilder {
	private ASTNode tree;
	Parser input;
	
	public ASTBuilder(Parser lexer) {
		input = lexer;
	}

	public ASTNode build() throws Exception {
		tree = input.parseLine();
		return tree;
	}

}
