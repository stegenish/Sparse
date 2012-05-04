package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Scope {

	private Map<Symbol, Entity> bindings = new HashMap<Symbol, Entity>();
	private Scope shadowedScope;
	private Scope globalScope;

	public Scope() {
		globalScope = this;
	}

	private Scope(Scope containingScope, Scope globalScope) {
		this.shadowedScope = containingScope;
		this.globalScope = globalScope;
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

	public void exposeType(Class<? extends Entity> type, Symbols symbols) {
		Method[] methods = type.getMethods();
		for (Method method : methods) {
			Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
			for (int i = 0; i < declaredAnnotations.length; i++) {
				Annotation annotation = declaredAnnotations[i];
				if(annotation instanceof ExposedSparseFunction) {
					ExposedSparseFunction exposedFunction = (ExposedSparseFunction) annotation;
					bind(symbols.getSymbol(exposedFunction.name()), new ExposedFunction(exposedFunction, method));
				}
			}
		}
	}
}
