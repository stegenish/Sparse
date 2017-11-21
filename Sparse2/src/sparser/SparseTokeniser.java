/*
 * Created on Aug 6, 2004
 */
package sparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import Tokeniser.StrategyTokeniser;

/**
 * @author Thomas Stegen
 */
public class SparseTokeniser extends StrategyTokeniser 
{
	private SparseTokeniserStrategy sparseStrategy;

	public SparseTokeniser(Reader source) throws IOException, FileNotFoundException {
		super(new SparseTokeniserStrategy());
		
		sparseStrategy = (SparseTokeniserStrategy)strategy;
		setString(source);
	}
	
	public void addReaderMacroChar(char c) {
		sparseStrategy.addReaderMacroChar(c);
	}
}
