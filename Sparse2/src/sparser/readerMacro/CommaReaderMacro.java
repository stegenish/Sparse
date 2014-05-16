package sparser.readerMacro;

import java.io.IOException;

import sparser.Entity;
import sparser.SparseException;
import sparser.SparseList;
import sparser.Sparser;

public class CommaReaderMacro extends ReaderMacro {

	@Override
	public Entity call(Sparser sparser) {
		try {
			CommaMarker marker = getMarker(sparser);
			return new SparseList().append(marker).append(sparser.parseNextForm(true));
		} catch (IOException e) {
			throw new SparseException(e.getMessage());
		}
	}

	private CommaMarker getMarker(Sparser sparser) throws IOException {
		CommaMarker marker;
		int nextChar = sparser.nextChar();
		if (nextChar != -1 && (char)nextChar == '@') {
			marker = CommaMarker.atMarker;
		} else {
			sparser.goBackChar(1);
			marker = CommaMarker.commaMarker;
		}
		return marker;
	}

}
