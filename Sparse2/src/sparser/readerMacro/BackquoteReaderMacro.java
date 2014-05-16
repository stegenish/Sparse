package sparser.readerMacro;

import sparser.Entity;
import sparser.SparseList;
import sparser.Sparser;

public class BackquoteReaderMacro extends ReaderMacro {

	@Override
	public Entity call(Sparser sparser) {
		Entity form = sparser.parseNextForm(false);
		if (form instanceof SparseList) {
			return handleBackquotedList((SparseList)form, sparser);
		}
		
		return quoteForm(form, sparser);
	}

	private  Entity handleBackquotedList(SparseList listForm, Sparser sparser) {
		SparseList newForm = new SparseList().append(sparser.getSymbol("list"));
		for(Entity entity : listForm) {
			if (entity instanceof SparseList) {
				SparseList listEntity = (SparseList)entity;
				Entity firstElement = ((SparseList)listEntity.rest()).getFirstElement();
				if (listEntity.getFirstElement() == CommaMarker.commaMarker) {
					newForm.append(firstElement);
				}
				
				if (listEntity.getFirstElement() == CommaMarker.atMarker) {
					newForm.concat(firstElement.execute(sparser.globalScope));
				}
			} else {
				newForm.append(quoteForm(entity, sparser));	
			}
		}
		
		return newForm;
	}

}
