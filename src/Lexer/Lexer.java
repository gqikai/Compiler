package Lexer;

public class Lexer {
	public State state = State.Begin;

	public static final char EOF = (char) -1;

	private String input;
	private int p;
	protected char c;
	
	public Lexer(String input) {
		this.input = input;
		this.c = input.charAt(0);
		this.p = 0;
		}

	public Token nextToken() {
		while (c != EOF) {
			if(c == ' '|| c == '\t'|| c == '\n'|| c == '\r') consume();
			switch (state) {
				case Begin: {
					switch (c) {
						case '+': case '-' :case '*': {
							Token token = new Token(Type.Operator,c + "");
							consume();
							return token;
						}
						case '/': {
							consume();
							state = State.InPreComment;
							break;
						}

						case ',': {
							consume();
							return new Token(Type.Comma);
						}
						case '(': {
							consume();
							return new Token(Type.Lbracket);
						}
						case ')': {
							consume();
							return new Token(Type.Rbracket);
						}
						case ';': {
							consume();
							return new Token(Type.Semicolon);
						}

						default: if(isLetter(c)){
							state = State.InWord;
						}else if(isConst(c)){
							state = State.inConst;
						}
					}
					break;
				}
				case InPreComment:
					switch(c){
						case '/':{
							consume();
							this.state = State.InComment;
							break;
						}
						default:{
                            state = State.Begin;
							return new Token(Type.Operator,"/");
						}
					}
				case InComment:{
					while(c != '\n') consume();
					consume();
					state = State.Begin;
					break;
				}
				case InWord:{
                    state = State.Begin;
					return getWord();
				}
				case inConst:{
                    state = State.Begin;
					return getConst();
				}
			}
		}
		return new Token(Type.Eof);
	}

	private Token getConst() {
		StringBuilder builder = new StringBuilder();
		do {
			builder.append(c);
			consume();
		} while (isConst(c));
		return new Token(Type.Const, Double.valueOf(builder.toString()));
	}

	Token getWord() {
		StringBuilder builder = new StringBuilder();
		do {
			builder.append(c);
			consume();
		} while (isLetter(c));
        String str = builder.toString();
        if(Reserved.isFunction(str)) return new Token(Type.Function,str);
        else if(str.equals("T")) return new Token(Type.Var,str);
		return new Token(Type.Word,str);

	}

	public void consume() {
//		System.out.println(c + " consumed!");
		p++;
		if (p >= input.length()) {
			this.c = EOF;
		} else {
			this.c = input.charAt(p);
		}
	}

	boolean isLetter(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	boolean isConst(char c) {
		return c >= '0' && c <= '9' || c == '.';
	}


}
