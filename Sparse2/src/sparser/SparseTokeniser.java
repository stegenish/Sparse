/*
 * Created on Aug 6, 2004
 */
package sparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Tokeniser.StrategyTokeniser;

/**
 * @author Thomas Stegen
 */
public class SparseTokeniser extends StrategyTokeniser 
{
    public SparseTokeniser(File codefile) throws IOException,
                                                   FileNotFoundException
    {
        super(new SparseTokeniserStrategy());
        BufferedReader file = new BufferedReader(new FileReader(codefile));
        StringBuffer str = new StringBuffer();
        int c;
        
        c = file.read();
        while(c != -1)
        {
            str.append((char)c);
            c = file.read();
        }
        setString(str.toString());
    }
    
    public SparseTokeniser(String str)
    {
        super(new SparseTokeniserStrategy(), str);
    }
    
}
