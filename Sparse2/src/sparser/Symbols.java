package sparser;

import java.util.HashMap;
import java.util.Map;

public class Symbols {
	private Map<String, Symbol> symbols = new HashMap<String, Symbol>();

	public void putSymbol(String string, Symbol symbol) {
		symbols.put(string, symbol);
	}

	public Symbol getSymbol(String str) {
		Symbol symbol = symbols.get(str);
		if(symbol == null) {
			symbol = new Symbol(str);
			putSymbol(str, symbol);
		}
		return symbol;
	}
}
