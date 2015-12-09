package Lexer;
 
public class Token {
    public Type type;
    public String text;
    public double value;
    public String func;

    public Token(Type type, double value) {
        this.type=type; this.value=value;
    }

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Token(Type type) {
        this.type = type;
    }

    @Override
	public String toString() {
		return "[" + type + ", text=" + text +
				", value=" + value + ", func=" + func + "]";
	}

}
