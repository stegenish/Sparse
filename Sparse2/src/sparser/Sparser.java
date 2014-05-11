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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import sparser.builtins.Add;
import sparser.builtins.AssertEquals;
import sparser.builtins.Export;
import sparser.builtins.Import;
import sparser.builtins.List;
import sparser.builtins.Multiply;
import sparser.builtins.Print;
import sparser.builtins.Quit;
import sparser.builtins.Subtract;
import specialForms.AssertThrowsException;
import specialForms.Bind;
import specialForms.Boundp;
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
		bindSymbol("assert-throws-exception", new AssertThrowsException(), scope);
		bindSymbol("add", new Add(), scope);
		bindSymbol("+", new Add(), scope);
		bindSymbol("-", new Subtract(), scope);
		bindSymbol("bind", new Bind(scope), scope);
		bindSymbol("set", new specialForms.Set(), scope);
		bindSymbol("defun", new Defun(), scope);
		bindSymbol("defspecial", new DefSpecial(), scope);
		bindSymbol("multiply", new Multiply(), scope);
		bindSymbol("*", new Multiply(), scope);
		bindSymbol("print", new Print(), scope);
		bindSymbol("quote", new Quote(), scope);
		bindSymbol("if", new If(), scope);
		bindSymbol("true", SparseBoolean.True, scope);
		bindSymbol("false", SparseBoolean.False, scope);
		bindSymbol("let", new Let(), scope);
		bindSymbol("list", new List(), scope);
		bindSymbol("eval", new Eval(), scope);
		bindSymbol("while", new While(), scope);
		bindSymbol("null", SparseNull.theNull, scope);
		bindSymbol("import", new Import(this), scope);
		bindSymbol("isbound", new Boundp(), scope);
		bindSymbol("export", new Export(), scope);
		bindSymbol("quit", new Quit(), scope);
		
		exposeType(SparseList.class);
		exposeType(SparseInt.class);
		exposeType(Entity.class);
		exposeType(SparseBoolean.class);
	}
	
	public void bindSymbol(String string, Entity entity, Scope scope) {
		String str = new SparseToken(string, false).getToken();
		scope.bind(symbols.getSymbol(str), entity);
	}

	public Code parseString(String code) throws FileNotFoundException, IOException {
		tokens = new SparseTokeniser(new StringReader(code + " "));
		return parseCode();
	}

	public Code parseReader(Reader code) throws FileNotFoundException, IOException {
		setReader(code);
		return parseCode();
	}

	public void setReader(Reader code) throws IOException,	FileNotFoundException {
		tokens = new SparseTokeniser(code);
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

