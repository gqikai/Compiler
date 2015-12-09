package DrawUtils;

//DrawAxis.java

import java.awt.*;
import javax.swing.*;

import AST.ASTNode;
import Lexer.Lexer;
import Parser.Parser;
import Visitor.Visitor;

import java.util.ArrayList;

public class DrawAxis extends JFrame {
	public ArrayList<int[]> xPointsList = new ArrayList<int[]>(); 
	public ArrayList<int[]> yPointsList = new ArrayList<int[]>(); 
	
	public DrawAxis(ArrayList<int[]> xPointsList, ArrayList<int[]> yPointsList) {
		super("MyAxis");
		this.xPointsList = xPointsList;
		this.yPointsList = yPointsList;
		
		setSize(600, 800);
		setVisible(true);
	}

	public void paint(Graphics g) {

		for(int i = 0; i < xPointsList.size(); i ++){
			g.drawPolyline(xPointsList.get(i),yPointsList.get(i),
					xPointsList.get(i).length);
		}
		
	}

	public static void main(String[] args) throws Exception{
		ASTNode tree;
		String input =
				"origin is (300,300);"
				+ "rot is 0;"
				+ "scale is (1,1);"
				+ "for T from 0 to 200 step 3 draw (T,0);"
				+ "//fdslfdfdjsklfkdslf\n"
				+ "for T from 0 to 150 step 3 draw (0,T);"
				+ "for T from 0 to 120 step 3 draw (T,-T*T/40);";
		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);
		tree = parser.start();
		System.out.println(tree.toString(1));
		Visitor visitor = new Visitor();
		visitor.visit(tree);

		DrawAxis drawAxis = new DrawAxis(visitor.xPointsList, visitor.yPointsList);
		System.out.println(visitor.table);
	}
}