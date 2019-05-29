package korat.examples.trie;

import java.util.*;
import java.util.LinkedList;
import java.util.Set;

import korat.finitization.IArraySet;
import korat.finitization.IBooleanSet;
import korat.finitization.IFinitization;
import korat.finitization.IIntSet;
import korat.finitization.impl.FinitizationFactory;

import korat.finitization.IObjSet;

public class Trie {
	public static class Node {
        Character c;
        Node children[];
        boolean isWord;
     
        public Node() {}
        /*One can write a getNode(char) function to determine how many entries 
         * the children field has. Same with the root.
         */
        public Node(char c){
            this.c = c;
        }
    }
    Node root;
    int size; 
       
    public Trie() {
        root = new Node();
    }
    
    @SuppressWarnings("unchecked")
    public boolean repOK() {
        if (root == null)
            return size == 0;
        // checks that tree has no cycle
        Set visited = new HashSet();
        visited.add(root);
        LinkedList workList = new LinkedList();
        workList.add(root);
        while (!workList.isEmpty()) {
        	Node current = (Node) workList.removeFirst();
        	for(Node currentChild : current.children) {
        	    if(currentChild!=null) {
        	    	if(!visited.add(currentChild))
        	    		return false;
        	    	workList.add(currentChild);
        	    }
        	}
        }
        // checks that size is consistent
        return (visited.size() == size);
    }

    public static IFinitization finTrie(int size) {
        return finTrie(size, size, size);
    }

    public static IFinitization finTrie(int nodesNum, int minSize,
            int maxSize) {
        IFinitization f = FinitizationFactory.create(Trie.class);//singleton class instance of Finitization
        IObjSet nodes = f.createObjSet(Node.class, nodesNum, true); // field domain for nodes
        IObjSet chars= f.createObjSet(Character.class, 3, false); // field domain excluding null for c
        IIntSet arrayLength = f.createIntSet(0, 1, 3);// array length can range from 0 to 3 (max 3 children)   
        IArraySet childrenSet = f.createArraySet(Node[].class, arrayLength, nodes, 1/*what goes here?*/);  
        f.set("root", nodes);
        f.set("size", f.createIntSet(minSize, maxSize));
        f.set("Node.c", chars);
        f.set("Node.children", childrenSet);
        
        return f;
    }
}
