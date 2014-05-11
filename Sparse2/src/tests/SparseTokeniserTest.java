package tests;

import junit.framework.TestCase;
import sparser.*;

import java.util.*;
import java.io.*;

import Tokeniser.Token;

/*
 * Created on Aug 6, 2004
 */

/**
 * @author Thomas Stegen
 */
public class SparseTokeniserTest extends TestCase
{
    private static final String PATH = "testSources/";

    private SparseTokeniser        toks;
    private Vector<String>         answers;
    /**
     * Constructor for SparseTokeniserTest.
     * @param arg0
     */
    public SparseTokeniserTest(String arg0)
    {
        super(arg0);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(SparseTokeniserTest.class);
    }

        /*
         * @see TestCase#setUp()
         */
    protected void setUp() throws Exception
    {
        super.setUp();
        String line;

        BufferedReader file =
        new BufferedReader(
        new FileReader(new File(PATH + "tokeniseranswer")));
        
        answers = new Vector<String>();
        line = file.readLine();
        while(line != null)
        {
            answers.add(line);
            line = file.readLine();
        }
        
        file.close();
        
        BufferedReader file2 = new BufferedReader(new FileReader(new File(PATH + "tokeniser")));
        StringBuffer fileContent = new StringBuffer();
        int c;
        while ((c = file2.read()) != -1) {
        	fileContent.append((char)c);
        }
        
        toks = new SparseTokeniser(new StringReader(fileContent.toString()));
		
		file2.close();
    }

    public void testNext()
    {
        int i = 0;
        Token next;
        while((next = toks.next()) != null)
        {
			assertEquals(answers.get(i++), next.getToken());
        }
    }
}
