package Parser;
import AST.ASTNode;
import Lexer.Token;
import Lexer.Lexer;
import Lexer.Type;

public class Parser {
	private Lexer input;
	private Token next;
	
	public Parser(Lexer lexer) {
		this.input = lexer;
		next = input.nextToken();
	}

	public ASTNode start() throws Exception{
		ASTNode root = new ASTNode(new Token(Type.Root,"root"));
		
		while(next.type != Type.Eof){
			root.addChild(parseLine());
		}
		return root;
	}
	public ASTNode parseLine() throws Exception{
		
		if(next.text.equals("origin")) return originStat();
		else if(next.text.equals("rot")) return rotStat();
		else if(next.text.equals("scale")) return scaleStat();
		else if(next.text.equals("for")) return drawStat();
		else return null;
	}

	private ASTNode exprStat() throws Exception {
		if(next.type == Type.Operator) {
			match(Type.Operator);
			ASTNode negativeNode = new ASTNode(new Token(Type.Operator,"-"));
			negativeNode.addChild(new ASTNode(new Token(Type.Const,0)));
			negativeNode.addChild(exprStat());
			return negativeNode;
		}
		boolean bracked = false;

		if(next.type == Type.Lbracket){
			bracked = true;
			match(Type.Lbracket);
		}

		ASTNode exprNode = new ASTNode();

		Token LNum = (next.type == Type.Var)?match(Type.Var):match(Type.Const);

		if(next.type == Type.Operator){

			ASTNode LNode = new ASTNode(LNum);
			exprNode.addChild(LNode);

			exprNode.token = match(Type.Operator);
			exprNode.addChild(exprStat());
		}else{
			exprNode.token = LNum;
		}
		if(bracked){
			match(Type.Rbracket);
		}
		if(next.type == Type.Operator){
			ASTNode parent = new ASTNode(match(Type.Operator));
			parent.addChild(exprNode);
			parent.addChild(exprStat());
			return parent;
		}
		return exprNode;
	}


	private ASTNode rotStat() throws Exception {
		match("rot");
		ASTNode rotNode = new ASTNode(new Token(Type.Rot,"rot"));
		ASTNode xprNode;

		match("is");
		ASTNode exprNode = exprStat();
		match(Type.Semicolon);

		rotNode.addChild(exprNode);

		return rotNode;

	}

	private ASTNode originStat() throws Exception {
		ASTNode originNode = new ASTNode(new Token(Type.Origin,"origin"));
		match("origin");
		match("is");
		match(Type.Lbracket);
		originNode.addChild(exprStat());


		match(Type.Comma);
		originNode.addChild(exprStat());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return originNode;

	}

	private ASTNode scaleStat() throws Exception {
		ASTNode scaleNode = new ASTNode(new Token(Type.Scale,"scale"));
		match("scale");
		match("is");
		match(Type.Lbracket);
		scaleNode.addChild(exprStat());


		match(Type.Comma);
		scaleNode.addChild(exprStat());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return scaleNode;

	}

	private ASTNode drawStat() throws Exception {
		ASTNode drawNode = new ASTNode(new Token(Type.Draw,"draw"));
		match("for");
		match("T");
		match("from");
		drawNode.addChild(exprStat());
		match("to");
		drawNode.addChild(exprStat());
		match("step");
		drawNode.addChild(exprStat());
		match("draw");
		match(Type.Lbracket);
		drawNode.addChild(exprStat());
		match(Type.Comma);
		drawNode.addChild(exprStat());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return drawNode;

	}

	private ASTNode funcStat() throws Exception {
		ASTNode funcNode = new ASTNode(match(Type.Function));

		match(Type.Lbracket);
		funcNode.addChild(exprStat());
		match(Type.Rbracket);
		match(Type.Semicolon);
		return funcNode;
	}

	private Token match(Type type) throws Exception {
		Token token;
		if(next.type == type) {
			token = next;
			System.out.println(next + "matched");
			next = input.nextToken();
			return token;
		}
		else throw new Exception("wrong match");
	}

	private void match(String text) throws Exception {
		
		if(next.text.equals(text)) {
			
			System.out.println(next + "matched");
			next = input.nextToken();
		}
		else throw new Exception("wrong match");
	}
}
