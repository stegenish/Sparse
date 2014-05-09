package sparser;

import java.util.HashMap;
import java.util.Map;
import static sparser.SparseBoolean.toSparseBoolean;

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

	public Scope createStrangeScope() {
		return new Scope(null, moduleScope);
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
