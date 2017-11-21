package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import Tokeniser.Token;
import junit.framework.TestCase;
import sparser.SparseTokeniser;

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

    @Before
    public void setUp() throws Exception
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

    @Test
    public void testNext()
    {
        int i = 0;
        Token next;
        while((next = toks.next()) != null)
        {
			assertEquals(answers.get(i++), next.getToken());
        }
    }

	@Test
	public void testWithSingleQuote() throws Exception {
		SparseTokeniser tokeniser = new SparseTokeniser(new StringReader(" ' asd (''xcv) "));
		assertEquals("'", tokeniser.next().getToken());
		assertEquals("asd", tokeniser.next().getToken());
		assertEquals("(", tokeniser.next().getToken());
		assertEquals("'", tokeniser.next().getToken());
		assertEquals("'xcv", tokeniser.next().getToken());
		assertEquals(")", tokeniser.next().getToken());
	}
}
