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
	public SparseTokeniser(Reader source) throws IOException, FileNotFoundException {
		super(new SparseTokeniserStrategy());
		
		setString(source);
	}
}
