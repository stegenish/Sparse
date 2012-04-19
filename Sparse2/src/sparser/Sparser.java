/*
 * sparser.java
 *
 * Created on 07 August 2004, 10:13
 */

package sparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import sparser.builtins.Add;
import sparser.builtins.AssertEquals;
import sparser.builtins.List;
import sparser.builtins.Multiply;
import sparser.builtins.Print;
import specialForms.Bind;
import specialForms.DefSpecial;
import specialForms.Defun;
import specialForms.Eval;
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
	private SparseTokeniser tokens;

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
		bindSymbol("quote", new Quote(), scope);
		bindSymbol("if", new If(), scope);
		bindSymbol("true", SparseBoolean.True, scope);
		bindSymbol("false", SparseBoolean.False, scope);
		bindSymbol("if", new If(), scope);
		bindSymbol("let", new Let(), scope);
		bindSymbol("list", new List(), scope);
		bindSymbol("eval", new Eval(), scope);
		
		exposeType(SparseList.class);
		exposeType(SparseInt.class);
	}
	
	public void bindSymbol(String string, Entity entity, Scope scope) {
		Symbol symbol = parseSymbol(new SparseToken(string, false));
		scope.bind(symbol, entity);
	}

	public Code parseString(String code) {
		tokens = new SparseTokeniser(code);
		return parseCode();
	}

    private Code parseCode()
    {
    	Code code = new Code();
    	while(tokens.hasMore()) {
    		Entity entity = parseNextToken(true);
    		code.appendEntity(entity);
    	}
        return code;
    }

	public Entity parseNextToken(boolean canEnd) {
		
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

	private Entity readerMacro() {
		SparseList sparseList = new SparseList();
		sparseList.append(getSymbol("quote"));
		sparseList.append(parseNextToken(false));
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
		Entity entity = parseNextToken(false);
		boolean addingElement = entity != null;
		if(addingElement) {
			list.append(entity);
		}
		return addingElement;
	}

    private Symbol parseSymbol(SparseToken token)
    {
        String str = token.getToken();
        Symbol s;
        try {
            s = SparseInt.valueOf(str);
        } catch(NumberFormatException e) {
        	s = symbols.getSymbol(str);
        }
        return s;
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

	public void exposeType(Class<? extends Entity> type) {
		Method[] methods = type.getMethods();
		for (Method method : methods) {
			Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
			for (int i = 0; i < declaredAnnotations.length; i++) {
				Annotation annotation = declaredAnnotations[i];
				if(annotation instanceof ExposedSparseFunction) {
					ExposedSparseFunction exposedFunction = (ExposedSparseFunction) annotation;
					bindSymbol(exposedFunction.name(), new ExposedFunction(exposedFunction, method), globalScope);
				}
			}
		}
	}
}

