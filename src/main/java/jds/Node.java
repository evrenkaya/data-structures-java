package jds;


/**
 * An implementation of binary trees based on the
 * Node class from the ODS library (by P. Morin)
 */
public class Node {
	public Node parent;     // parent node
 	public Node left;       // left child
	public Node right;      // right child

	public Integer data;    // data value stored in node

	public int postorderN;  // postorder number for this node
	public int preorderN;   // preorder number for this node
	public int inorderN;    // inorder number for this node

	public Node(){
		parent = left = right = null;
		data = null;
		postorderN = preorderN = inorderN = -1;	
	}

	public Node(Integer value){
		this();
		data = value;
	}

}