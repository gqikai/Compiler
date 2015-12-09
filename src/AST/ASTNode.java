package AST;
import java.util.ArrayList;
import java.util.List;

import Lexer.Token;

public class ASTNode {
	public Token token;
	public List<ASTNode> childs = new ArrayList<ASTNode>();
	
	public ASTNode() {
		// TODO Auto-generated constructor stub
	}
	public ASTNode(Token token) {
		this.token = token;
	}



	public void addChild(ASTNode t) {
        if ( childs==null ) childs = new ArrayList<ASTNode>();
        childs.add(t);
    }

	public ASTNode getChild(int index){
		return index <= childs.size()?childs.get(index):null;
	}
	public String toString(int level) {
		String info = "ASTNode [token=" + token + ",childs(" + 
				(childs.size() + "")+")\n";
		for(int j = 0; j < childs.size(); j++){
			for(int i = 0; i <level; i ++){
				info += "  ";
			}
			info += childs.get(j).toString(level + 1);
		}
		
		return info;
	}
	@Override
	public String toString() {
		return "ASTNode [token=" + token + ", childs=" + childs + "]";
	}
	
	
}
