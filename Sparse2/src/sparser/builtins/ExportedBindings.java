package sparser.builtins;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sparser.Entity;
import sparser.Scope;
import sparser.Symbol;

public class ExportedBindings {

	Map<Symbol, Entity> exportedBindings = new HashMap<>();
	
	public void addExport(Symbol symbol, Entity value) {
		exportedBindings.put(symbol,  value);
	}

	public void export(Scope importScope) {
		for (Entry<Symbol, Entity> binding: exportedBindings.entrySet()) {
			importScope.bind(binding.getKey(), binding.getValue());
		}
	}
}
