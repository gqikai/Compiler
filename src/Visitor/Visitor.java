package Visitor;

import java.util.ArrayList;

import AST.ASTNode;
import Lexer.Type;
import SymTab.SymTable;

public class Visitor {
	public SymTable table = new SymTable();
	public ArrayList<int[]> xPointsList = new ArrayList<int[]>();
	public ArrayList<int[]> yPointsList = new ArrayList<int[]>();

	public void visit(ASTNode tree) {
		switch (tree.token.type) {
		case Root: {
			for (int i = 0; i < tree.childs.size(); i++) {
				visit(tree.getChild(i));
			}
			break;
		}
		case Origin: {
			table.set("originX", calc(tree.getChild(0)));
			table.set("originY", calc(tree.getChild(1)));
			break;
		}
		case Rot: {
			table.set("rot", calc(tree.getChild(0)));
			break;
		}
		case Scale: {
			table.set("scaleX", calc(tree.getChild(0)));
			table.set("scaleY", calc(tree.getChild(1)));
			break;
		}
		case Draw: {
			double from = tree.getChild(0).token.value;
			double to = tree.getChild(1).token.value;
			double step = tree.getChild(2).token.value;

			ArrayList<Double> xPoints = new ArrayList<Double>();
			ArrayList<Double> yPoints = new ArrayList<Double>();

			int drawTimes = (int) ((to - from) / step);
			for (int i = 0; i < drawTimes; i++) {
				int t = (int) (from + i * step);
				assignT(tree.getChild(3), t);
				xPoints.add(calc(tree.getChild(3)) * table.get("scaleX") + table.get("originX"));
				assignT(tree.getChild(4), t);
				yPoints.add(calc(tree.getChild(4)) * table.get("scaleY") + table.get("originY"));
			}

			int xSize = xPoints.size();
			int[] xIntPoints = new int[xSize];
			for (int i = 0; i < xSize; i++) {
				xIntPoints[i] = xPoints.get(i).intValue();
			}

			xPointsList.add(xIntPoints);

			int ySize = xPoints.size();
			int[] yIntPoints = new int[ySize];
			for (int i = 0; i < ySize; i++) {
				yIntPoints[i] = yPoints.get(i).intValue();
			}

			yPointsList.add(yIntPoints);

			break;
		}
		}
	
	}

	public Double calc(ASTNode tree) {
		switch (tree.token.type) {
		case Const:
			case Var: {
			return tree.token.value;
		}
            case Operator:{
			double LNum, RNum;
			if (tree.getChild(0).childs.size() == 0) {
				LNum = tree.getChild(0).token.value;
			} else {
				LNum = calc(tree.getChild(0));
			}

			if (tree.getChild(1).childs.size() == 0) {
				RNum = tree.getChild(1).token.value;
			} else {
				RNum = calc(tree.getChild(1));
			}
			switch (tree.token.text) {
			case "+": {
				return LNum + RNum;
			}
			case "-": {
				return LNum - RNum;
			}
			case "*": {
				return LNum * RNum;
			}
			case "/": {
				return LNum / RNum;
			}
			}
		}
		}
		return null;
	}

	void assignT(ASTNode tree, double value) {

		if (tree.token.type == Type.Var) {
			tree.token.value = value;
			System.out.println(tree);
		} else {
			for (int i = 0; i < tree.childs.size(); i++) {
				ASTNode childNode = tree.childs.get(i);
				assignT(childNode, value);
			}
		}
	}
}
