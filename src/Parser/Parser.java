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

	private ASTNode Expression() throws Exception {
        ASTNode rootNode = Term();
        while(next.type == Type.Plus||next.type == Type.Minus){
            ASTNode leftNode = rootNode;

            rootNode = new ASTNode(match(next.type));
            ASTNode rightNode = Term();

            rootNode.addChild(leftNode);
            rootNode.addChild(rightNode);
        }
        return rootNode;
    }

    public ASTNode Term() throws Exception {
        ASTNode rootNode = Factor();
        while(next.type == Type.Operator && (next.text.equals("*") || next.text.equals("/"))){
            ASTNode leftNode = rootNode;

            rootNode = new ASTNode(match(next.type));
            ASTNode rightNode = Factor();

            rootNode.addChild(leftNode);
            rootNode.addChild(rightNode);
        }
        return rootNode;
    }

    public ASTNode Factor() throws Exception {
        if(next.type == Type.Operator && next.text.equals("+")){
            match(Type.Operator);
            return Factor();
        }else if(next.type == Type.Operator && next.text.equals("-")){
            ASTNode minusNode = new ASTNode(match(Type.Operator));
            minusNode.addChild(new ASTNode(new Token(Type.Const,0)));
            minusNode.addChild(Factor());
            return minusNode;
        }else return Component();
    }

	public ASTNode Component() throws Exception {
		ASTNode rootNode = Atom();
		if(next.type == Type.Power){
            ASTNode leftNode = rootNode;
			rootNode = new ASTNode(match(Type.Power));
			ASTNode rightNode = Component();
            rootNode.addChild(leftNode);
            rootNode.addChild(rightNode);
		}
        return rootNode;
	}

	public ASTNode Atom() throws Exception {
		switch(next.type){
			case Const :{
				return new ASTNode(match(Type.Const));
			}
			case Var: {
				return new ASTNode(match(Type.Var));
			}
			case Function:{
				ASTNode functionNode = new ASTNode(match(Type.Function));
				match(Type.Lbracket);
				functionNode.addChild(Expression());
				match(Type.Rbracket);
				return functionNode;
			}
			case Lbracket:{
				match(Type.Lbracket);
				ASTNode node =  Expression();
				match(Type.Rbracket);
				return node;
			}
		}
		return null;
	}
	private ASTNode rotStat() throws Exception {
		match("rot");
		ASTNode rotNode = new ASTNode(new Token(Type.Rot,"rot"));
		ASTNode xprNode;

		match("is");
		ASTNode exprNode = Expression();
		match(Type.Semicolon);

		rotNode.addChild(exprNode);

		return rotNode;

	}

	private ASTNode originStat() throws Exception {
		ASTNode originNode = new ASTNode(new Token(Type.Origin,"origin"));
		match("origin");
		match("is");
		match(Type.Lbracket);
		originNode.addChild(Expression());


		match(Type.Comma);
		originNode.addChild(Expression());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return originNode;

	}

	private ASTNode scaleStat() throws Exception {
		ASTNode scaleNode = new ASTNode(new Token(Type.Scale,"scale"));
		match("scale");
		match("is");
		match(Type.Lbracket);
		scaleNode.addChild(Expression());
		match(Type.Comma);
		scaleNode.addChild(Expression());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return scaleNode;

	}

	private ASTNode drawStat() throws Exception {
		ASTNode drawNode = new ASTNode(new Token(Type.Draw,"draw"));
		match("for");
		match("T");
		match("from");
		drawNode.addChild(Expression());
		match("to");
		drawNode.addChild(Expression());
		match("step");
		drawNode.addChild(Expression());
		match("draw");
		match(Type.Lbracket);
		drawNode.addChild(Expression());
		match(Type.Comma);
		drawNode.addChild(Expression());
		match(Type.Rbracket);
		match(Type.Semicolon);

		return drawNode;

	}

	private ASTNode funcStat() throws Exception {
		ASTNode funcNode = new ASTNode(match(Type.Function));

		match(Type.Lbracket);
		funcNode.addChild(Expression());
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
