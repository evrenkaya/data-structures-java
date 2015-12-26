package jds;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * An implementation of binary trees that stores Integers 
 * (as well as other numbers for traversals)
 * based on the ODS BinaryTree class by  P. Morin.
 *
 */
public class BinaryTree {
	
	// A variable that is used for node labeling for any one of the traversals
	private int orderingNum;
	
	/**
	 * The root of this tree
	 */
	protected Node r;

	/**
	 * This tree's "null" node
	 */
	public Node nil;

	/**
	 * Create an empty binary tree
	 */
	public BinaryTree() {}
	
	/**
	 * Create a binary tree with specified root value
	 */
	public BinaryTree(Integer data) {
		r = new Node(data);
		r.parent = nil;
		r.left = nil;
		r.right = nil;
	}

	/**
	 * Create a binary tree with specified root value
	 * (assume u's left/right children are already specified)
	 */
	public BinaryTree(Node u) {
		r = u;
		r.parent = nil;
	}

	
	
	/**
	 * Compute the depth (distance to the root) of u
	 * @param u
	 * @return the distanct between u and the root, r
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}
	
	/**
	 * Compute the size (number of nodes) of this tree
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}
	
	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int size(Node u) {
		if (u == nil) return 0;
		return 1 + size(u.left) + size(u.right);
	}
	
	
	/**
	 * Compute the maximum depth of any node in this tree
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}
	
	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == nil) return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	
	/**
	 * @return
	 */
	public boolean isEmpty() {
		return r == nil;
	}
	
	/**
	 * Make this tree into the empty tree
	 */
	public void clear() {
		r = nil;
	}

	/**
	 * clears the preorderN, postorderN and inorderN 
	 * values in the tree to -1.
	 */
	public void reset(){
		// do not change this method
		resetHelper(r);
	}

	protected void resetHelper(Node u){
		// do not change this method
		if (u == nil) return;
		u.preorderN = u.postorderN = u.inorderN = -1;
		resetHelper(u.left);
		resetHelper(u.right);

	}
	
	/*
	 * Recursive helper functions
	 */
	protected void preOrderHelper(Node u) {
		if(u == nil) return;
		u.preorderN = orderingNum++;
		preOrderHelper(u.left);
		preOrderHelper(u.right);
	}
	
	protected void postOrderHelper(Node u) {
		if(u == nil) return;
		postOrderHelper(u.left);
		postOrderHelper(u.right);
		u.postorderN = orderingNum++;
	}
	
	protected void inOrderHelper(Node u) {
		if(u == nil) return;
		inOrderHelper(u.left);
		u.inorderN = orderingNum++;
		inOrderHelper(u.right);
	}


	/********************************************************
	 * these methods set the preorderN, postOrderN and inorderN
	 * values for the nodes in the tree rooted at r
	 *******************************************************/

	public void preOrderNumbers(){
		orderingNum = 0;
		preOrderHelper(r);
	}


	public void postOrderNumbers(){
		orderingNum = 0;
		postOrderHelper(r);
	}

	public void inOrderNumbers(){
		orderingNum = 0;
		inOrderHelper(r);
	}


	public void preOrderNumbersNonRecursive(){
		orderingNum = 0;
		Deque<Node> nodes = new ArrayDeque<>();

		if(r != nil) nodes.add(r);
		while(!nodes.isEmpty()) {
			// Visit the node sitting at the top of the stack
			Node n = nodes.removeLast();
			n.preorderN = orderingNum++;
			
			// Add the right node (if possible) first so that left
			// nodes are popped off stack first and visited before right nodes
			if(n.right != nil) nodes.add(n.right);
			if(n.left != nil) nodes.add(n.left);
		}
		
	}


	public void postOrderNumbersNonRecursive(){
		orderingNum = 0;
		// I found a general algorithm for post order traversal of a tree from the website:
		// http://articles.leetcode.com/2010/10/binary-tree-post-order-traversal.html
		
		// The first stack keeps track of the nodes in a pre-order traversal
		Deque<Node> firstStack = new ArrayDeque<>();
		// The second stack contains the same nodes
		// but just in reverse order of the first stack
		Deque<Node> secondStack = new ArrayDeque<>();
		
		// Do a "reverse" pre-order traversal
		if(r != nil) firstStack.add(r);
		while(!firstStack.isEmpty()) {
			Node n = firstStack.removeLast();
			// Push the node from the top of the first stack
			// to the top of this stack
			secondStack.add(n);
			
			// Add left nodes first so that the right nodes 
			// get pushed onto the second stack first
			if(n.left != nil) firstStack.add(n.left);
			if(n.right != nil) firstStack.add(n.right);
		}
		
		// Now the second stack contains the post-ordered nodes
		while(!secondStack.isEmpty()) {
			secondStack.removeLast().postorderN = orderingNum++;
		}
	}

	public void inOrderNumbersNonRecursive(){
		orderingNum = 0;
		Deque<Node> nodes = new ArrayDeque<>();
		Node currNode = r;
		// I found a general algorithm for in order traversal of a tree from the website:
		// http://articles.leetcode.com/2010/04/binary-search-tree-in-order-traversal.html
		// and I modified it to label the nodes
		
		// Travel down to left of tree until we reach a leaf and 
		// then set the label for that node properly
		// If there is a right node then jump to that node and repeat
		while(!nodes.isEmpty() || currNode != nil) {
			if(currNode != nil) {
				nodes.add(currNode);
				currNode = currNode.left;
			}
			else {
				Node n = nodes.removeLast();
				n.inorderN = orderingNum++;
				currNode = n.right;
			}
		}
	}

}
