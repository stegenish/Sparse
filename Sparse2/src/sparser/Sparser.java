/*
 * sparser.java
 *
 * Created on 07 August 2004, 10:13
 */

package sparser;

/**
  * @author  Administrator
  *
  */
public class Sparser
{
    private Symbols symbols;
	private SparseTokeniser tokens;

	public Sparser(Symbols symbols) {
		this.symbols = symbols;
	}

	public Code parseString(String code) {
		tokens = new SparseTokeniser(code);
		return parseCode();
	}

    private Code parseCode()
    {
    	Code code = new Code();
    	while(tokens.hasMore()) {
    		Entity entity = parseNextForm(true);
    		code.appendEntity(entity);
    	}
        return code;
    }

	public Entity parseNextForm(boolean canEnd) {
		
		SparseToken token = nextToken(canEnd);
		Entity entity;
		switch(token.getType())
		{
		    case SparseToken.LBRACKET:
		        entity = parseList();
		        break;
		    case SparseToken.SYMBOL:
		    	entity = parseSymbol(token);
		        break;
		    case SparseToken.INTEGER:
		    	entity = parseInteger(token);
		        break;
		    case SparseToken.RBRACKET:
		    	entity = null;
		    	break;
		    case SparseToken.STRING:
		    	entity = parseString(token);
		        break;
		    case SparseToken.READER_MACRO:
		    	entity = readerMacro();
		    	break;
		    default:
		        throw new SyntaxErrorException(token, "Unexpected token. " +
		                            "Expected a list, symbol or string");
		}
		return entity;
	}

	private Entity parseInteger(SparseToken token) {
		return new SparseInt(token.getToken());
	}

	private Entity readerMacro() {
		SparseList sparseList = new SparseList();
		sparseList.append(getSymbol("quote"));
		sparseList.append(parseNextForm(false));
		return sparseList;
	}

	private SparseList parseList()
    {
        SparseList list = new SparseList();
        boolean elementAppended = true;
        while(elementAppended) {
        	elementAppended = appendNextListElement(list);
        }
        return list;
    }

	private boolean appendNextListElement(SparseList list) {
		Entity entity = parseNextForm(false);
		boolean addingElement = entity != null;
		if(addingElement) {
			list.append(entity);
		}
		return addingElement;
	}

    private Symbol parseSymbol(SparseToken token)
    {
        String str = token.getToken();
        return symbols.getSymbol(str);
    }

    private Entity parseString(SparseToken token)
    {
        return new SparseString(token.getToken().substring(1, token.getToken().length() - 1));
    }

    private SparseToken nextToken(boolean canEnd)
    {
        SparseToken tok = (SparseToken)tokens.next();
        if(tok == null && !canEnd)
        {
            throw new SyntaxErrorException(tok, "Premature end of input.");
        }
        return tok;
    }

	public Symbol getSymbol(String symName) {
		return symbols.getSymbol(symName);
	}
}

