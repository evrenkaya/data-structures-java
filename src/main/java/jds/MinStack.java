package jds;

import java.util.LinkedList;


/**
 * An implementation of a Stack that also can efficiently return  
 * the minimum element in the current Stack.  Every operation -
 * push, pop, peek, and min - must run in constant time
 *
 * @param <T> the class of objects stored in the queue
 */
public class MinStack<T extends Comparable<T>> extends SLListStack<T> {
	
	// A second "stack" that keeps track of the minimum values
	// The current minimum object is at the head of this list
	private LinkedList<T> minData = new LinkedList<>();
	
	
	public T min(){
		if(minData.isEmpty()) return null;
		
		return minData.getFirst();
	}
	
	@Override
	public T push(T x) {
		
		if(minData.isEmpty() || x.compareTo(minData.getFirst()) <= 0) {
			// If x is smaller than the current min object
			// or if the min data stack is empty, need to add one element to it
			minData.addFirst(x);
		}
		
		// Now let the base class add to the main stack
		return super.push(x);
	}

	@Override
	public T pop() {
		// Get the object on top of the main stack
		T topData = super.peek();
		
		if(!minData.isEmpty() && topData.compareTo(minData.getFirst()) == 0) {
			// If the object on top is equal to the current min object
			// remove it from the min data stack
			minData.remove();
		}
		// Now let the base class remove from the main stack
		return super.pop();
	}

}
