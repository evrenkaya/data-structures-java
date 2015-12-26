package jds;

/**
 * An implementation of the Deque interface as a doubly-linked circular list 
 * of blocks of size b
 *
 * @param <T>  the type of elements stored in the list
 */
public class SEListDeque<T> extends SEList<T> {

	/** 
	 *  Creates a SEListDeque object with block size b
	 *
	 * @param b the block size of the BDeques in the nodes
	 * @param c the class (type) of data being stored in the deque
	 */	
	public SEListDeque(int b, Class<T> c) {
		super(b, c);
	}
	
	// Creates a new node and initializes the next and previous pointers
	private Node createAndInitNode(Node next, Node prev) {
		Node newNode = createNode();
		// Set next and previous references
		newNode.next = next;
		newNode.prev = prev;
		n++;
		
		return newNode;
		
	}
	
	// Sets the next and previous pointers for the dummy node
	private void setDummyPointers(Node next, Node prev) {
		dummy.next = next;
		dummy.prev = prev;
	}
	
	// Moves the tail element of "from" to the head of "to"
	// and then adds an element to the head of "from"
	private void shiftElementAddElement(Node from, Node to, T x) {
		// Remove tail of "from" and add it to head of "to"
		to.d.add(0, from.d.remove(from.d.size() - 1));
		// Now add x to the head of "from"
		from.d.add(0, x);
	}
	
	/** 
	 * Add to the front of the deque
	 *
	 * @param x the element to add to the front of the deque
	 */
	public void addFirst(T x) {
		Node firstNode = dummy.next;
		
		if(firstNode == dummy) {
			// First block is not created, so create a new block
			Node newNode = createAndInitNode(dummy, dummy);
			setDummyPointers(newNode, newNode);
			newNode.d.add(0, x);

		} else if(firstNode.d.size() == (b + 1)) {
			// First block is full, so create a new block
			// If second block does not exist

			if(n == 1) {
				Node secondNode = createAndInitNode(firstNode.next, firstNode);
				firstNode.next = secondNode;
				
				// Add the new node to the tail of the list
				dummy.prev = secondNode;
				
				shiftElementAddElement(firstNode, secondNode, x);
			} else {
				// Second block does exist
				Node secondNode = firstNode.next;
				
				if(secondNode.d.size() == (b + 1)) {
					// Second block is full, so create a new block
					Node newNode = createAndInitNode(firstNode.next, firstNode);
					
					// Add the new node after the first block
					firstNode.next = secondNode.prev = newNode;
					shiftElementAddElement(firstNode, newNode, x);
				} else {
					// Second block is not full
					shiftElementAddElement(firstNode, secondNode, x);
				}
				
			}
			
		} else {
			// First block is not full
			firstNode.d.add(0, x);
		}
		
		numElements++;
	}

	/** 
	 * Add to the back of the deque
	 *
	 * @param x the element to add to the back of the deque
	 */
	public void addLast(T x) {
		Node lastNode = dummy.prev;
		
		if(lastNode == dummy) {
			// First block is not created, so create a new block
			Node newNode = createAndInitNode(dummy, dummy);
			setDummyPointers(newNode, newNode);
			newNode.d.add(0, x);
			
		} else if(lastNode.d.size() == b + 1) {
			// Last block is full, so create a new block
			Node newNode = createAndInitNode(dummy, dummy.prev);
			
			// Add the new node to the tail of the list
			dummy.prev.next = newNode;
			dummy.prev = newNode;
			
			newNode.d.add(0, x);
		} else {
			// Add the element to the last position in the last block
			lastNode.d.add(lastNode.d.size(), x);
		}
		
		numElements++;
	}

	
	/** 
	 * Remove an item from the front of the deque
	 */
	public T removeFirst() {
		if(numElements == 0) {
			return null;
		}
		
		Node firstNode = dummy.next;
		T data = firstNode.d.remove(0);
		if(firstNode.d.size() == 0) {
			// Empty node/block
			// Remove it from the Deque
			dummy.next = firstNode.next;
			firstNode.next.prev = dummy;
			n--;
		}

		numElements--;
		return data;
	}

	/** 
	 * Remove an item from the back of the deque
	 */
	public T removeLast() {
		if(numElements == 0) {
			return null;
		}
		
		Node lastNode = dummy.prev;
		T data = lastNode.d.remove(lastNode.d.size() - 1);
		if(lastNode.d.size() == 0) {
			// Empty node/block
			// Remove it from the Deque
			dummy.prev = lastNode.prev;
			lastNode.prev.next = dummy;
			n--;
		}

		numElements--;
		return data;
	}
	
	public T getFirst() {
		return dummy.next.d.get(0);
	}
	
	public T getLast() {
		return dummy.prev.d.get(dummy.prev.d.size() - 1);
	}
	
	public void display() {
		Node currentNode = dummy.next;
		while(currentNode != dummy) {
			for(int i = 0; i < currentNode.d.size(); i++) {
				System.out.println(currentNode.d.get(i));
			}
			currentNode = currentNode.next;
		}
	}
}
