/*
 * sparser.java
 *
 * Created on 07 August 2004, 10:13
 */

package sparser;

import sparser.builtins.Add;
import sparser.builtins.AssertEquals;
import sparser.builtins.First;
import sparser.builtins.Multiply;
import sparser.builtins.Print;
import specialForms.Bind;
import specialForms.DefSpecial;
import specialForms.Defun;
import specialForms.If;
import specialForms.Let;
import specialForms.Quote;

/**
  * @author  Administrator
  *
  */
public class Sparser
{
    private Symbols symbols = new Symbols();
	private Scope globalScope;

	public Sparser(Scope scope) {
		this.globalScope = scope;
		initialBindings(globalScope);
	}

	private void initialBindings(Scope scope) {
		bindSymbol("assert", new AssertEquals(), scope);
		bindSymbol("add", new Add(), scope);
		bindSymbol("bind", new Bind(scope), scope);
		bindSymbol("defun", new Defun(), scope);
		bindSymbol("defspecial", new DefSpecial(), scope);
		bindSymbol("multiply", new Multiply(), scope);
		bindSymbol("print", new Print(), scope);
		bindSymbol("first", new First(), scope);
		bindSymbol("quote", new Quote(), scope);
		bindSymbol("if", new If(), scope);
		bindSymbol("true", SparseBoolean.True, scope);
		bindSymbol("false", SparseBoolean.False, scope);
		bindSymbol("if", new If(), scope);
		bindSymbol("let", new Let(), scope);
	}

	public Code parseString(String code) {
		SparseTokeniser toks = new SparseTokeniser(code);
		return parse(toks);
	}

    private Code parse(SparseTokeniser toks)
    {
    	Code code = new Code();
    	while(toks.hasMore()) {
    		SparseToken tok = nextToken(true, toks);
    		Entity entity = null;
    		entity = parseForm(tok, toks);
    		code.appendEntity(entity);
    	}
        return code;
    }

    public void bindSymbol(String string, Entity entity, Scope scope) {
		Symbol symbol = parseSymbol(new SparseToken(string));
		scope.bind(symbol, entity);
	}

	public Entity parseForm(SparseToken tok, SparseTokeniser toks) {
		Entity entity;
		switch(tok.getType())
        {
            case SparseToken.LBRACKET:
                entity = parseList(toks);
                break;
            case SparseToken.SYMBOL:
            	entity = parseSymbol(tok);
                break;
            case SparseToken.STRING:
            	entity = parseString(tok);
                break;
            default:
                throw new SyntaxErrorException(tok, "Unexpected token. " +
                                    "Expected a list, symbol or string");
        }
		return entity;
	}

    private SparseList parseList(SparseTokeniser toks)
    {
        SparseList list = new SparseList();
        SparseToken tok = nextToken(false, toks);
        while(tok != null)
        {
            switch(tok.getType())
            {
                case SparseToken.LBRACKET:
                    list.append(parseList(toks));
                    break;
                case SparseToken.SYMBOL:
                    list.append(parseSymbol(tok));
                    break;
                case SparseToken.RBRACKET:
                	return list;
                case SparseToken.STRING:
                    list.append(parseString(tok));
                    break;
                default:
                    throw new SyntaxErrorException(tok, "Unexpected token. " +
                    "Expected a list, symbol or string");
            }
            tok = nextToken(false, toks);
        }
        return list;
    }



    private Symbol parseSymbol(SparseToken tok)
    {
        String str = tok.getToken();
        Symbol s;
        try {
            s = SparseInt.valueOf(str);
        } catch(NumberFormatException e) {
        	s = symbols.getSymbol(str);
        }
        return s;
    }

    private SparseString parseString(SparseToken tok)
    {
        return new SparseString(tok.getToken().substring(1, tok.getToken().length() - 1));
    }

    /**
     * Returns the next token from the tokeniser.
     *
     * @param canEnd If true it is not a syntax error if there are no
     * more tokens. If false and there are no more tokens a SyntaxErrorException
     * will be thrown.
     */
    private SparseToken nextToken(boolean canEnd, SparseTokeniser toks)
    {
        SparseToken tok = (SparseToken)toks.next();
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
