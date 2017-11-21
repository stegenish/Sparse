package Tokeniser;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import java.util.Vector;

public class TokenGetter {
	private TokeniserStrategy strategy;
	private Reader source;
	public Vector<Token> tokens = new Vector<>();
	
	private int currentLine = 1;  /*current line*/
	private int currentColumn = 1; /*current column*/
	private int currentTokenLine = 1; /*The start of the currently parsed token*/
	private int currentTokenColumn = 1;  /*The start of the currently parsed token*/
	private Stack<Integer> columnStack = new Stack<Integer>(); /*holds previous max values of column*/
	private StringBuffer readBuffer = new StringBuffer();
	private int charsRead = 0;
	private int currentReadBufferPosition = 0;
	
	public TokenGetter(TokeniserStrategy strategy, Reader source) {
		this.strategy = strategy;
		this.source = source;
	}
	
	public Token nextToken() throws IOException {
		int readChar;
		char c;
		while((readChar = nextChar()) != -1)
		{
			c = (char)readChar;
			
			boolean haveToken = strategy.nextLetter(c);
			
			calculateLineAndColumn(c);
			
			if(haveToken)
			{
				return processToken();
			}
		}
		
		return endOfInput();
	}
	
	public int nextChar() throws IOException {
		if (currentReadBufferPosition < charsRead) {
			return readBuffer.charAt(currentReadBufferPosition++);
		}
		int read = source.read();
		updateReadBuffer((char)read);
		return read;
	}
	
	private void updateReadBuffer(char c) {
		readBuffer.append(c);
		currentReadBufferPosition++;
		charsRead++;
	}
	
	private Token processToken() throws IOException {
		Token tok;
		tok = strategy.createToken();
		tok.setLine(currentTokenLine);
		tok.setColumn(currentTokenColumn);
		
		strategy.reset();
		
		handleGoBack();
		
		tokens.add(tok);
		return tok;
	}
	
	private void calculateLineAndColumn(char c) {
		if(c == '\n')
		{
			columnStack.push(currentColumn);
			currentColumn = 0;
			currentLine++;
		}
		currentColumn++;
	}
	
	public void handleGoBack() throws IOException {
		int backtrack = strategy.goBack();
		currentReadBufferPosition -= backtrack;
		if(backtrack < 0)
		{
			throw new TokeniserException("goBack cannot return "+
					"negative value");
		}
		
		currentColumn -= backtrack;
		while(currentColumn < 1)
		{
			int tmp = columnStack.pop().intValue();
			currentColumn = tmp + currentColumn;
			currentLine--;
		}
		
		currentTokenLine = currentLine;
		currentTokenColumn = currentColumn;
	}
	
	private Token endOfInput() {
		Token tok;
		tok = strategy.endOfInput();
		if(tok != null)
		{
			tok.setLine(currentTokenLine);
			tok.setColumn(currentTokenColumn);
			tokens.add(tok);
		}
		
		return tok;
	}
}