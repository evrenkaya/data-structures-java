package jds;

import java.util.Comparator;
import java.util.Random;

/**
 * This class implements a priority queue as a class binary heap
 * stored implicitly in an array
 *
 * @param <T>
 */
public class DaryHeap<T> extends BinaryHeap<T> {
	
	/**
	 * The number value of d
	 */
	protected int d;

	/**
	 * Create a new empty binary heap
	 * @param clz
	 */
	public DaryHeap(Class<T> clz, int d) {
		super(clz, new DefaultComparator<T>());
		this.d = d;
	}

	public DaryHeap(Class<T> clz, Comparator<T> c0, int d) {
		super(clz, c0);
		this.d = d;
	}

	@Override
	protected void trickleDown(int i) {
		do {
			int j = -1;
			// Start at left child
			int childIndex = child(i, 0);
			
			if(childIndex < n && c.compare(a[childIndex], a[i]) <= 0) {
				j = childIndex;
			} else {
				childIndex = i;
			}
			
			// Find the minimum value of all the children to swap with
			for(int k = 1; k < d; k++) {
				if(child(i, k) < n && c.compare(a[child(i, k)], a[childIndex]) < 0) {
					j = child(i, k);
					childIndex = j;
				}
			}
			if (j >= 0)	swap(i, j);
			i = j;
		} while (i >= 0);
	}

	public int valueOfD(){
		return d;
	}


	/**
	 * Retrieves the index in the array of the parent of the value at index i
	 *
	 * @param i
	 * @return the index of the parent of the value at index i
	 */
	@Override 
	public int parent(int i) {
		return (i - 1) / d;
	}
	
	/**
	 * Retrieves the index of the jth child, 0 <= j < d, of the value at index i
	 * 
	 * @param i
	 * @param j
	 * @return the index of the jth child value at index i
	 */
	public int child(int i, int j) {
		return (d * i) + j + 1;
	}

}
