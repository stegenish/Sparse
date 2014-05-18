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

	private Entity handleBackquotedList(SparseList listForm, Sparser sparser) {
		SparseList newForm = new SparseList().append(sparser.getSymbol("list"));
		boolean spliced = false;
		for(Entity entity : listForm) {
			if (entity instanceof SparseList) {
				SparseList listEntity = (SparseList)entity;
				if (isCommaMarked(listEntity)) {
					Entity firstElement = getMarkedElement(listEntity);
					newForm = append(newForm, firstElement, sparser);
				} else if (isAtMarked(listEntity)) {
					Entity firstElement = getMarkedElement(listEntity);
					newForm = splice(newForm, firstElement, sparser);
					spliced = true;
				} else {
					newForm.append(handleBackquotedList(listEntity, sparser));
				}
			} else {
				newForm.append(quoteForm(entity, sparser));	
			}
		}
		
		return newForm;
	}

	private SparseList append(SparseList newForm, Entity firstElement, Sparser sparser) {
		SparseList appendCall = new SparseList().append(sparser.getSymbol("append"));
		appendCall.append(newForm).append(firstElement);
		return appendCall;
		//newForm.append(firstElement);
	}

	private Entity getMarkedElement(SparseList listEntity) {
		return listEntity.getSecondElement();
	}

	private boolean isAtMarked(SparseList listEntity) {
		return listEntity.getFirstElement() == CommaMarker.atMarker;
	}

	private boolean isCommaMarked(SparseList listEntity) {
		return listEntity.getFirstElement() == CommaMarker.commaMarker;
	}

	private SparseList splice(SparseList newForm, Entity firstElement, Sparser sparser) {
		SparseList list = new SparseList().append(sparser.getSymbol("concat"));
		list.append(newForm);
		SparseList spliced = list.append(firstElement);
		return spliced;
		//return newForm.concat(firstElement.execute(sparser.globalScope));
	}

}
