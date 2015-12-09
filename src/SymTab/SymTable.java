package SymTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymTable {
	public HashMap<String,Double> symTable = new HashMap<String,Double>();
	
	
	public SymTable(){
		symTable.put("originX", 0.0);
		symTable.put("originY", 0.0);
		symTable.put("scaleX", 1.0);
		symTable.put("scaleY", 1.0);
		symTable.put("rot", 0.0);
		symTable.put("T", 0.0);
	}
	
	public Double get(String key){
		return symTable.get(key);
	}
	

	public Double set(String key,Double value){
		return symTable.put(key, value);
	}

	@Override
	public String toString() {
		return "SymTable [symTable=" + symTable + "]";
	}



}
