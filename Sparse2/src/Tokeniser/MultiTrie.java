/*
 * Created on 09-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Thomas Stegen 200111876
 *
 *  Supports the 7 bit ascii character set. This is a
 */
public class MultiTrie
{
    /**
     * The root children.
     */
    private Hashtable<Character, TrieNode> children;

    /**
     * The current lookups going on
     */
    private LinkedList<NodePtr> lookups;
    /**
     * Holds candidates for searches. A set since a NodePtr
     * only prints one item.
     */
    private Hashtable<NodePtr, String>  candidates;

    public MultiTrie()
    {
        children = new Hashtable<Character, TrieNode>();
        candidates = new Hashtable<NodePtr, String>();
        lookups = new LinkedList<NodePtr>();
    }

    public void addEntry(String entry)
    {
        Character c = new Character(entry.charAt(0));

        TrieNode tmp = getTrieNode(c);

        if(entry.length() != 1)
        {
            tmp.addEntry(entry.substring(1));
        }
        else
        {
            tmp.setEnd(true);
        }
    }

	private TrieNode getTrieNode(Character c) {
		TrieNode tmp = children.get(c);
        if(tmp == null)
        {
            tmp = new TrieNode();
            children.put(c, tmp);
        }
		return tmp;
	}

    public boolean hasEntry(String entry)
    {
        Character c = new Character(entry.charAt(0));
        TrieNode tmp = children.get(c);

        if(tmp == null)
        {
            /*no such entry*/
            return false;
        }
        else if(entry.length() > 1)
        {
            return tmp.hasEntry(entry.substring(1));
        }
        else
        {
            /*entry is a one character string which is here, check
              if the node has end set*/
            return tmp.getEnd();
        }

    }

    public void addLookup(char ch)
    {
        Character c = new Character(ch);
        NodePtr tmp;
        /*First handle all existing lookups*/
        Iterator<NodePtr> iter = lookups.listIterator(0);

        while(iter.hasNext())
        {
            tmp = iter.next();
            if(!tmp.addChar(ch))
            {
                if(tmp.getNode().getEnd())
                {
                     candidates.remove(tmp);
                     candidates.put(tmp, tmp.getString());
                }
                if(candidates.containsKey(tmp))
                {
                    System.out.println(candidates.get(tmp));
                    candidates.remove(tmp);
                }
                iter.remove();
            }
            else
            {
                if(tmp.getNode().getEnd())
                {
                    if(!candidates.containsKey(tmp))
                    {
                        candidates.remove(tmp);
                        candidates.put(tmp, tmp.getString());
                    }
                }
            }
        }

        /* Handle roots last so they won't be advanced twice */
        TrieNode node = children.get(c);
        if(node != null)
        {
            tmp = new NodePtr(ch);
            tmp.setNode(node);
            if(node.getEnd())
            {
                /*A candidate item for this NodePtr*/
                candidates.put(tmp, tmp.getString());
            }
            lookups.add(tmp);
        }
    }

    public void printAll()
    {
        Enumeration<Character> e = children.keys();
        while(e.hasMoreElements())
        {
            Character c = e.nextElement();
            children.get(c).printAll("" + c.charValue());
        }
    }
}
