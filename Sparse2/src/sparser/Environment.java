package sparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import sparser.builtins.Add;
import sparser.builtins.AssertEquals;
import sparser.builtins.Export;
import sparser.builtins.List;
import sparser.builtins.Multiply;
import sparser.builtins.Print;
import sparser.builtins.Quit;
import sparser.builtins.Subtract;
import sparser.readerMacro.BackquoteReaderMacro;
import sparser.readerMacro.CommaReaderMacro;
import sparser.readerMacro.QuoteReaderMacro;
import specialForms.AssertThrowsException;
import specialForms.Bind;
import specialForms.Boundp;
import specialForms.DefSpecial;
import specialForms.Defmacro;
import specialForms.Defun;
import specialForms.Eval;
import specialForms.If;
import specialForms.Let;
import specialForms.Quote;

public class Environment {

	public Sparser sparser;
	public Scope scope;
	public Symbols symbols;
	
	public Environment() {
		scope = new Scope();
		symbols = new Symbols();
		sparser = new Sparser(scope, symbols);
		initialBindings(symbols, scope);
	}
	
	private void initialBindings(Symbols symbols, Scope scope) {
		bindSymbol("assert", new AssertEquals(), symbols, scope);
		bindSymbol("assert-throws-exception", new AssertThrowsException(), symbols, scope);
		bindSymbol("add", new Add(), symbols, scope);
		bindSymbol("+", new Add(), symbols, scope);
		bindSymbol("-", new Subtract(), symbols, scope);
		bindSymbol("bind", new Bind(scope), symbols, scope);
		bindSymbol("set", new specialForms.Set(), symbols, scope);
		bindSymbol("defun", new Defun(), symbols, scope);
		bindSymbol("defspecial", new DefSpecial(), symbols, scope);
		bindSymbol("multiply", new Multiply(), symbols, scope);
		bindSymbol("*", new Multiply(), symbols, scope);
		bindSymbol("print", new Print(), symbols, scope);
		bindSymbol("quote", new Quote(), symbols, scope);
		bindSymbol("if", new If(), symbols, scope);
		bindSymbol("true", SparseBoolean.True, symbols, scope);
		bindSymbol("false", SparseBoolean.False, symbols, scope);
		bindSymbol("let", new Let(), symbols, scope);
		bindSymbol("list", new List(), symbols, scope);
		bindSymbol("eval", new Eval(), symbols, scope);
		bindSymbol("while", new While(), symbols, scope);
		bindSymbol("null", SparseNull.theNull, symbols, scope);
		bindSymbol("isbound", new Boundp(), symbols, scope);
		bindSymbol("export", new Export(), symbols, scope);
		bindSymbol("quit", new Quit(), symbols, scope);
		bindSymbol("defmacro", new Defmacro(), symbols, scope);
		
		exposeInternalTypes(symbols, scope);
		
		addReaderMacros();
	}

	private void addReaderMacros() {
		sparser.addReaderMacro('\'', new QuoteReaderMacro());
		sparser.addReaderMacro('`', new BackquoteReaderMacro());
		sparser.addReaderMacro(',', new CommaReaderMacro());
	}

	private void exposeInternalTypes(Symbols symbols, Scope scope) {
		exposeType(SparseList.class, symbols, scope);
		exposeType(SparseInt.class, symbols, scope);
		exposeType(Entity.class, symbols, scope);
		exposeType(SparseBoolean.class, symbols, scope);
	}
	
	public void bindSymbol(String name, Entity entity, Symbols symbols, Scope scope) {
		scope.bind(symbols.getSymbol(name), entity);
	}

	public void exposeType(Class<? extends Entity> type, Symbols symbols, Scope scope) {
		Method[] methods = type.getMethods();
		for (Method method : methods) {
			Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
			for (int i = 0; i < declaredAnnotations.length; i++) {
				Annotation annotation = declaredAnnotations[i];
				if(annotation instanceof ExposedSparseFunction) {
					ExposedSparseFunction exposedFunction = (ExposedSparseFunction) annotation;
					scope.bind(symbols.getSymbol(exposedFunction.name()), new ExposedFunction(exposedFunction, method));
				}
			}
		}
	}
}
