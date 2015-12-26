package jds;

public class BinaryTreeTest {

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree(5);
		tree.r.left = new Node(6);
		tree.r.right = new Node(7);
		
		tree.r.left.left = new Node(8);
		tree.r.left.left.left = tree.nil;
		tree.r.left.left.right = tree.nil;
		
		tree.r.left.right = new Node(9);
		
		tree.r.left.right.left = new Node(12);
		tree.r.left.right.right = new Node(13);
		tree.r.left.right.left.left = tree.nil;
		tree.r.left.right.left.right = tree.nil;
		tree.r.left.right.right.left = tree.nil;
		tree.r.left.right.right.right = tree.nil;
		
		tree.r.right.left = new Node(10);
		tree.r.right.left.right = tree.nil;
		tree.r.right.right = new Node(11);
		
		tree.r.right.left.left = new Node(14);
		tree.r.right.right.left = new Node(15);
		tree.r.right.right.left.left = tree.nil;
		tree.r.right.right.left.right = tree.nil;
		tree.r.right.right.right = new Node(16);
		tree.r.right.right.right.left = tree.nil;
		tree.r.right.right.right.right = tree.nil;
		
		tree.r.right.left.left.left = tree.nil;
		tree.r.right.left.left.right = tree.nil;
		
		
		tree.inOrderNumbersNonRecursive();
		System.out.println(tree.r.inorderN);
	}

}
