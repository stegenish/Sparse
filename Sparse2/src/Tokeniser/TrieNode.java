/*
 * Created on 09-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Thomas Stegen 200111876
 * A node in a Trie.
 */
public class TrieNode
{
    /**
     * The children of this node
     */
    private Hashtable<Character, TrieNode> children;



    /**
     * Need to mark this node as being the end of an entry. This is
     * because some entries might have the same prefix. Example "an" and
     * "ant".
     */
    private boolean end = false;

    /**
     * Construct a new TrieNode.
     */
    public TrieNode()
    {
        children = new Hashtable<Character, TrieNode>();
    }

    /**
     * Get a child node of this node.
     * @param c The character which designates the child node
     * @return The associated node. If a node does not exist null is returned
     */
    public TrieNode getChild(char c)
    {
        return children.get(new Character(c));
    }

    /**
     * Specify whether or not an entry in the Trie ends here
     * @param e True if an entry ends here, false otherwise.
     */
    public void setEnd(boolean e)
    {
        end = e;
    }

    /**
     * Check if and entry ends at this node
     * @return True if an entry ends here, false otherwise.
     */
    public boolean getEnd()
    {
        /*System.out.println("END " + end);*/
        return end;
    }

    /**
     * Add an entry to this node and its subnodes
     * @param entry
     */
    public void addEntry(String entry)
    {
        TrieNode tmp;
        if(entry.length() >= 1)
        {
            /*More characters to add*/
            char c = entry.charAt(0);
            tmp = children.get(new Character(c));
            if(tmp == null)
            {
                tmp = new TrieNode();
                children.put(new Character(c), tmp);
            }
        }
        else
        {
            /*This represents the last character*/
            end = true;
            return;
        }
        tmp.addEntry(entry.substring(1));
    }

    /**
     * @param entry The entry to look for
     * @return True if entry is in the trie, false otherwise.
     */
    public boolean hasEntry(String entry)
    {
        if(entry.length() >= 1)
        {
            char c = entry.charAt(0);
            TrieNode tmp = children.get(new Character(c));
            if(tmp == null)
            {
                /*no child with that value so no entry*/
                return false;
            }
            else
            {
                return tmp.hasEntry(entry.substring(1));
            }
        }
        else
        {
            return end;
        }
    }

    public void printAll(String s)
    {
        if(end)
        {
            System.out.println(s);
        }

        Enumeration<Character> e = children.keys();
        while(e.hasMoreElements())
        {
            Character c = e.nextElement();
            children.get(c).printAll(s + c);
        }

    }
}
