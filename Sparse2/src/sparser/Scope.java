package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private Map<Symbol, Entity> bindings = new HashMap<Symbol, Entity>();
	private Scope containingScope;
	private Scope moduleScope;
	private Scope importingScope;

	public Scope() {
		moduleScope = this;
	}

	public Scope(Scope containingScope, Scope moduleScope) {
		this.containingScope = containingScope;
		this.moduleScope = moduleScope;
	}

	public Scope(Scope containingScope, Scope moduleScope, Scope importingScope) {
		this(containingScope, moduleScope);
		this.importingScope = importingScope;
	}

	public Entity getBinding(Symbol symbol) {
		Entity value = bindings.get(symbol);
		if(value == null && getParentScope() != null) {
			value = getParentScope().getBinding(symbol);
		}
		return value;
	}

	private Scope getParentScope() {
		if(this.isModuleScope()) {
			return null;
		}
		if(this.isContained()) {
			return containingScope;
		}
		return moduleScope;
	}

	public void bind(Symbol symbol, Entity value) {
		bindings.put(symbol, value);
	}
	
	public void moduleBind(Symbol symbol, Entity value) {
		moduleScope.bindings.put(symbol, value);
	}
	
	public Entity set(Symbol symbol, Entity value) {
		if(bindings.containsKey(symbol)) {
			bindings.put(symbol, value);
			return value;
		} else if (isContained()) {
			containingScope.set(symbol, value);
			return value;
		}
		
		throw new SparseException(String.format("Symbol %s is not bound", symbol.getName()));
	}
	
	public Scope createLexicalScope() {
		return new Scope(this, moduleScope);
	}

	private boolean isModuleScope() {
		return moduleScope == this;
	}
	
	private boolean isContained() {
		return containingScope != null;
	}

	public Entity isBound(Symbol symbol) {
		return toSparseBoolean(getBinding(symbol) != null);
	}

	public Scope GetGlobalScope() {
		return moduleScope;
	}
	
	public Entity export(Symbol symbol) {
		Entity exportedValue = getBinding(symbol);
		importingScope.bind(symbol, exportedValue);
		return exportedValue;
	}
}
