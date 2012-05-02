package sparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import sparser.builtins.Add;
import sparser.builtins.AssertEquals;
import sparser.builtins.ExportedBindings;
import sparser.builtins.Import;
import sparser.builtins.List;
import sparser.builtins.Multiply;
import sparser.builtins.Print;
import sparser.builtins.Subtract;
import specialForms.Bind;
import specialForms.Boundp;
import specialForms.DefSpecial;
import specialForms.Defun;
import specialForms.Eval;
import specialForms.Export;
import specialForms.If;
import specialForms.Let;
import specialForms.Quote;

public class SparseSourceRunner {

	private String source;
	static Symbols symbols = new Symbols();

	public SparseSourceRunner(String fileName) {
		this.source = loadFile(fileName);
	}
	
	public SparseSourceRunner(BufferedReader reader) {
		String fileBuffer = loadFile(reader);
		this.source = fileBuffer;
	}

	public static String loadFile(String fileName) {
		try {
			return loadFile(new BufferedReader(new FileReader(fileName)));
		} catch (FileNotFoundException e) {
			throw new SparseException(e);
		}
	}
	
	public static String loadFile(BufferedReader reader) {
		StringBuffer buffer = new StringBuffer(1024);
		try {
			String line = reader.readLine();
			while(line != null) {
				buffer.append(line).append('\n');
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileBuffer = buffer.toString();
		return fileBuffer;
	}

	public Entity run(ExportedBindings exports) {
		Scope fileLexicalScope = new Scope();
		
		Sparser parser = new Sparser(symbols);
		SparseSourceRunner.initialBindings(fileLexicalScope, symbols, exports);
		Code code = parser.parseString(source);
		try {
			return code.execute(fileLexicalScope);
		} catch (SparseException e) {
			System.err.println("Exception thrown and not caught:");
			System.err.println(e.getMessage());
			System.err.print(e.getTrace());
			e.printStackTrace();
			throw e;
		}
	}

	public static void initialBindings(Scope scope, Symbols symbols, ExportedBindings exports) {
		bindSymbol("assert", new AssertEquals(), scope, symbols);
		bindSymbol("add", new Add(), scope, symbols);
		bindSymbol("+", new Add(), scope, symbols);
		bindSymbol("-", new Subtract(), scope, symbols);
		bindSymbol("bind", new Bind(scope), scope, symbols);
		bindSymbol("defun", new Defun(), scope, symbols);
		bindSymbol("defspecial", new DefSpecial(), scope, symbols);
		bindSymbol("multiply", new Multiply(), scope, symbols);
		bindSymbol("*", new Multiply(), scope, symbols);
		bindSymbol("print", new Print(), scope, symbols);
		bindSymbol("quote", new Quote(), scope, symbols);
		bindSymbol("if", new If(), scope, symbols);
		bindSymbol("true", SparseBoolean.True, scope, symbols);
		bindSymbol("false", SparseBoolean.False, scope, symbols);
		bindSymbol("if", new If(), scope, symbols);
		bindSymbol("let", new Let(), scope, symbols);
		bindSymbol("list", new List(), scope, symbols);
		bindSymbol("eval", new Eval(), scope, symbols);
		bindSymbol("while", new While(), scope, symbols);
		bindSymbol("null", SparseNull.theNull, scope, symbols);
		bindSymbol("isbound", new Boundp(), scope, symbols);
		bindSymbol("import", new Import(), scope, symbols);
		bindSymbol("export", new Export(exports), scope, symbols);
		scope.exposeType(SparseList.class, symbols);
		scope.exposeType(SparseInt.class, symbols);
		scope.exposeType(Entity.class, symbols);
	}

	private static void bindSymbol(String symbolName, Entity entity, Scope scope, Symbols symbols) {
		scope.bind(symbols.getSymbol(symbolName), entity);
	}
}
