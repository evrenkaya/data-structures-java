package comp2402a2;

import java.util.AbstractList;

public class ArrayDeque<T> extends AbstractList<T> {

	// Instance of helper class for creating new generic arrays
	protected Factory<T> factory;
	
	// Backing array
	protected T[] backingArray;
	
	// Head index of the Deque in the backing array
	protected int headIndex;
	
	// Size of the Deque (i.e. # of elements)
	protected int n;
	
	public ArrayDeque(Class<T> t) {
		factory = new Factory<T>(t);
		backingArray = factory.newArray(16);
		headIndex = n = 0;
	}
	
	public T get(int i) {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException();
		}
		
		return backingArray[circularIndex(i)];
	}
	
	public T set(int i, T x) {
		T removed = get(i);
		backingArray[circularIndex(i)] = x;
		
		return removed;
	}
	
	public void add(int i, T x) {
		if (i < 0 || i > n) {
			throw new IndexOutOfBoundsException();
		}
		if (n + 1 > backingArray.length) {
			resize();
		}
		
		if (i < n/2) {
			// Shift first half of elements left one position
			// We need to change headIndex here
			headIndex = (headIndex == 0) ? backingArray.length - 1 : headIndex - 1;
			
			for (int k = 0; k <= i-1; k++) {
				backingArray[circularIndex(k)] = backingArray[circularIndex(k + 1)];
			}
			
		} else {
			// Shift second half of elements right one position
			// We don't need to change headIndex here
			for (int k = n; k > i; k--) {
				backingArray[circularIndex(k)] = backingArray[circularIndex(k - 1)];
			}
		}
		backingArray[circularIndex(i)] = x;
		n++;
	}
	
	public T remove(int i) {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException();
		}
		T x = get(i);
		if (i < n/2) {
			// Shift first half of elements right one position
			for (int k = i; k > 0; k--) {
				backingArray[circularIndex(k)] = backingArray[circularIndex(k - 1)];
			}
			// We need to increase headIndex by one here
			headIndex = circularIndex(1);
		} else {
			// Shift second half of elements left one position
			// We don't need to change headIndex here
			for (int k = i; k < n-1; k++) {
				backingArray[circularIndex(k)] = backingArray[circularIndex(k + 1)];
			}
		}
		n--;
		if (3 * n < backingArray.length) {
			resize();
		}
		return x;
	}
	
	public void clear() {
		n = 0;
		resize();
	}
	
	public int size() {
		return n;
	}
	
	// Returns headIndex + i wrapped around the backing array
	// So that we don't ever go out of the array's bounds
	protected int circularIndex(int i) {
		return (headIndex + i) % backingArray.length;
	}
	
	
	protected void resize() {
		// Create new array and copy everything over
		T[] newArray = factory.newArray(Math.max(2 * n, 1));
		for (int k = 0; k < n; k++) {
			newArray[k] = backingArray[circularIndex(k)];
		}
		// Now set this to point to the new resized array
		backingArray = newArray;
		headIndex = 0;
	}

	
	
	
	
	
	
	
	
}