package sparser.readerMacro;

import sparser.Entity;
import sparser.SparseList;
import sparser.Sparser;

public abstract class ReaderMacro {

	abstract public Entity call(Sparser sparser);

	protected SparseList quoteForm(Entity quotedForm, Sparser parser) {
		SparseList sparseList = new SparseList();
		sparseList.append(parser.getSymbol("quote"));
		sparseList.append(quotedForm);
		return sparseList;
	}
}
