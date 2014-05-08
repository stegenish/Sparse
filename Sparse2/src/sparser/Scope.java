package sparser;

import java.util.HashMap;
import java.util.Map;
import static sparser.SparseBoolean.toSparseBoolean;

public class Scope {

	private Map<Symbol, Entity> bindings = new HashMap<Symbol, Entity>();
	private Scope shadowedScope;
	private Scope globalScope;
	private Scope importingScope;

	public Scope() {
		globalScope = this;
	}

	public Scope(Scope containingScope, Scope globalScope) {
		this.shadowedScope = containingScope;
		this.globalScope = globalScope;
	}

	public Scope(Scope containingScope, Scope globalScope, Scope importingScope) {
		this(containingScope, globalScope);
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
		if(this.isGlobalScope()) {
			return null;
		}
		if(this.isShadowingScope()) {
			return shadowedScope;
		}
		return globalScope;
	}

	public void bind(Symbol symbol, Entity value) {
		bindings.put(symbol, value);
	}

	public Scope createFunctionScope() {
		return new Scope(null, globalScope);
	}
	
	public Scope createShadowScope() {
		return new Scope(this, globalScope);
	}

	private boolean isGlobalScope() {
		return globalScope == this;
	}
	
	private boolean isShadowingScope() {
		return shadowedScope != null;
	}

	public Entity isBound(Symbol symbol) {
		return toSparseBoolean(getBinding(symbol) != null);
	}

	public Scope GetGlobalScope() {
		return globalScope;
	}
	
	public Entity export(Symbol symbol) {
		Entity exportedValue = getBinding(symbol);
		importingScope.bind(symbol, exportedValue);
		return exportedValue;
	}
}
