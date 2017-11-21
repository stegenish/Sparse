/*
 * sparser.java
 *
 * Created on 07 August 2004, 10:13
 */

package sparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import sparser.builtins.Import;
import sparser.readerMacro.ReaderMacro;

/**
  * @author  Administrator
  *
  */
public class Sparser
{
    private Symbols symbols;
	public Scope globalScope;
	private SparseTokeniser tokens;
	private HashMap<Character, ReaderMacro> readerMacros = new HashMap<>();

	public Sparser(Scope scope, Symbols symbols) {
		this.globalScope = scope;
		this.symbols = symbols;
		scope.bind(getSymbol("import"), new Import(this));
	}

	public Code parseString(String code) throws FileNotFoundException, IOException {
		setReader(new StringReader(code + " "));
		return parseCode();
	}

	public Code parseReader(Reader source) throws FileNotFoundException, IOException {
		setReader(source);
		return parseCode();
	}

	public void setReader(Reader source) throws IOException, FileNotFoundException {
		tokens = createTokeniser(source);
	}
	
	private SparseTokeniser createTokeniser(Reader code) throws IOException,	FileNotFoundException {
		SparseTokeniser tokeniser = new SparseTokeniser(code);
		for (Character c : readerMacros.keySet()) {
			tokeniser.addReaderMacroChar(c);
		}
		return tokeniser;
	}
	
    private Code parseCode()
    {
    	Code code = new Code();
    	Entity entity = null;
    	do {
    		 entity = parseNextForm(true);
    		 if (entity != null) {
    			 code.appendEntity(entity);
    		 }
    	} while (entity != null);
    	
        return code;
    }

	public Entity parseNextForm(boolean canEnd) {
		
		SparseToken token = nextToken(canEnd);
		if (token == null) {
			return null;
		}
		
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
		    	entity = readerMacro(token);
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

	private Entity readerMacro(SparseToken token) {
		char readerMacroChar = token.getToken().charAt(0);
		ReaderMacro macro = readerMacros.get(readerMacroChar);
		if (macro != null) {
			return macro.call(this);
		}
		
		throw new SparseException("Unrecognised reader_macro " + token);
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

    /**
     * Returns the next token from the tokeniser.
     * @param canEnd If true it is not a syntax error if there are no
     * more tokens. If false and there are no more tokens a SyntaxErrorException
     * will be thrown.
     */
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
	
	public int nextChar() throws IOException {
		return tokens.getTokenGetter().nextChar();
	}

	public void goBackChar(int n) {
		tokens.setGoBack(n);
		try {
			tokens.getTokenGetter().handleGoBack();
		} catch (IOException e) {
			throw new SparseException("Parser error, cannot handle go back", e);
		}
	}
	
	public void addReaderMacro(char c, ReaderMacro readerMacro) {
		readerMacros.put(c, readerMacro);
	}
}



