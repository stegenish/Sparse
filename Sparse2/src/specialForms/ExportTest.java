package specialForms;

import sparser.Code;
import sparser.Scope;
import sparser.SparseInt;
import sparser.SparseSourceRunner;
import sparser.Sparser;
import sparser.SparserTestCase;
import sparser.Symbols;
import sparser.builtins.ExportedBindings;

public class ExportTest extends SparserTestCase {
	
	public ExportTest(String testName) {
		
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		exportedBindings = new ExportedBindings();
		super.setUp();
	}
	
	public void testExportSymbols() throws Exception {
		Scope importScope = new Scope();
		executeString("(bind 42 a) (export a)");
		exportedBindings.export(importScope);
		assertEquals(SparseInt.valueOf(42), importScope.getBinding(symbols.getSymbol("a")));
	}
	
	public void testExportManySymbols() throws Exception {
		Scope importScope = new Scope();
		executeString("(bind 42 a) " +
				      "(bind 43 b) " +
				      "(export a) " +
				      "(export b)");
		exportedBindings.export(importScope);
		assertEquals(SparseInt.valueOf(42), importScope.getBinding(symbols.getSymbol("a")));
		assertEquals(SparseInt.valueOf(43), importScope.getBinding(symbols.getSymbol("b")));
	}

}
