package comp2402a3;


/**
 * An implementation of the List interface as a doubly-linked circular list. A
 * dummy node is used to simplify the code. This version has a reverse method.
 * 
 * @param <T> the type of elements stored in the list
 */
public class DLListReversible<T> extends DLList<T> {
	
	/**
	 * Reverses the order of the items in the list
	 */
	public void reverse() {
		
		if(size() > 1) {
			// Begin by swapping the head and tail of the list
			swapPointers(dummy);
			
			// newFirst is pointing to what used to be the end of the list
			Node newFirst = dummy.next;
			
			// newLast is pointing to what used to be the beginning of the list
			Node newLast = dummy.prev;

			int count = 0;
			int limit = (int)(size() / 2);
			
			// Loop about size() / 2 times
			while(count < limit) {
				swapPointers(newFirst);
				swapPointers(newLast);
				
				
				newFirst = newFirst.next;
				newLast = newLast.prev;
				if(newFirst == newLast) {
					// This means that the nodes are exactly in the middle of the list
					// This will only happen if the list has an odd number of elements
					
					// Swap using either reference
					swapPointers(newFirst);
					
					// Finished reversing the list, so just exit the loop
					break;
				}
				count++;
			}
			
		}

	}
	
	// Helper method that just swaps the next and previous pointers of a node
	private void swapPointers(Node node) {
		Node temp = node.next;
		node.next = node.prev;
		node.prev = temp;
	}

	public static void main(String[] args){
		
		DLList<Character> list = new DLListReversible<Character>();
		
		String datasequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < datasequence.length(); i++){
			list.add(datasequence.charAt(i));
		}

		System.out.println(list);
		
		((DLListReversible<Character>)list).reverse();

		System.out.println(list);

	}
}
