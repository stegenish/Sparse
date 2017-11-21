package sparser.readerMacro;

import sparser.Entity;
import sparser.SparseList;
import sparser.Sparser;

public class QuoteReaderMacro extends ReaderMacro {

	public Entity call(Sparser parser) {
		Entity form = parser.parseNextForm(false);
		SparseList sparseList = quoteForm(form, parser);
		return sparseList;
	}
}
