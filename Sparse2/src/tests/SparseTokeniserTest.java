package tests;

import junit.framework.TestCase;
import sparser.*;
import java.util.*;
import java.io.*;

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
    private int                    numAnswers = 0;
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
        toks = new SparseTokeniser(new File(PATH + "tokeniser"));
        answers = new Vector<String>();
        line = file.readLine();
        while(line != null)
        {
            numAnswers++;
            answers.add(line);
            line = file.readLine();
        }
        
        file.close();
    }

    public void testNext()
    {
        int i = 0;
        while(toks.hasMore())
        {
            assertEquals(answers.get(i++), toks.next().getToken());
        }
    }

    public void testHasMore()
    {
        while(toks.hasMore())
        {
            toks.next();
            numAnswers--;
        }
        assertTrue(numAnswers == 0);
    }

    public void testCountTokens()
    {
        assertEquals(numAnswers, toks.countTokens());
    }

}
