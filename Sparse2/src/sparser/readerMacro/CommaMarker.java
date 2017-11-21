package sparser.readerMacro;

import sparser.Entity;
import sparser.Scope;
import sparser.SparseBoolean;
import sparser.SparseNull;

public class CommaMarker implements Entity {

	private String name;
	public static final CommaMarker atMarker = new CommaMarker("#commaAt-marker");
	public static final CommaMarker commaMarker = new CommaMarker("#comma-marker");

	public CommaMarker(String name) {
		this.name = name;
	}
	
	@Override
	public Entity execute(Scope scope) {
		return SparseNull.theNull;
	}

	@Override
	public String createString() {
		return name;
	}

	@Override
	public SparseBoolean equal(Object other) {
		return SparseBoolean.toSparseBoolean(this == other);
	}
}