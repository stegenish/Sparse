/*
 * Created on 09-Feb-2004
 *
 * Components as members of society. 
 */
package Tokeniser;

/**
 * @author Thomas Stegen 200111876
 * Used to hold a reference to a TrieNode and the accumulated String value
 * associated with this TrieNode.
 */
public class NodePtr
{
    /**
     * The node this NodePtr is currently pointing to.
     */
    private TrieNode node;
    
    /**
     * The string currently represented by this node
     */
    private StringBuffer str;
    
    /**
     * Construct a new NodePtr
     *
     */
    public NodePtr(char c)
    {
        str = new StringBuffer();
        str.append(c);
    }

    /**
     * Set a new node to point to.
     * 
     * Probably not needed.
     * 
     * @param n The new node.
     */    
    public void setNode(TrieNode n)
    {
        node = n;
    }
    
    /**
     * @return The node currently pointed at.
     */
    public TrieNode getNode()
    {
        return node;
    }
    
    /**
     * Adds a character to this NodePtr and goes to the child which
     * corresponds to the given character. If no child exists no change
     * is made.
     * @param c The character to add
     * @return True if there is a corresponding child. False if there is no
     *         child.
     */
    public boolean addChar(char c)
    {
        TrieNode tmp = node.getChild(c);
        if(tmp == null)
        {
            /*No child at given branch*/
            return false; 
        }
        node = tmp;
        str.append(c);
        return true;
    }
    
    /**
     * @return The accumulated string associated with the node pointed
     * to by this 
     */
    public String getString()
    {
        return str.toString();
    }
}
