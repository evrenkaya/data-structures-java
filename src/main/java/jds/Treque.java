package jds;

import java.util.AbstractList;
import java.util.List;

public class Treque<T> extends AbstractList<T> {
	
	// Front half - x[0], ..., x[n/2 -1]
	protected ArrayDeque<T> front;
	
	// Back half - x[n/2], ..., x[n]
	protected ArrayDeque<T> back;

	public Treque(Class<T> t) {
		front = new ArrayDeque<>(t);
		back = new ArrayDeque<>(t);
	}
	
	// Returns element at global index i
	public T get(int i) {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (i < front.size()) {
			return front.get(i);
		} else {
			return back.get(i - front.size());
		}
	}
	// Replaces elements at global index i with x and
	// returns elements that was replaced
	public T set(int i, T x) {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (i < front.size()) {
			return front.set(i, x);
		} else {
			return back.set(i - front.size(), x);
		}
	}
	
	// Adds a new element at global index i
	// and balance both halves of the Treque
	public void add(int i, T x) {
		if (i < 0 || i > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (i < front.size()) {
			front.add(i, x);
		} else {
			back.add(i - front.size(), x);
		}
		balanceDeques();
	}
	
	// Removes and returns element at global index i
	// and balance both halves of the Treque
	public T remove(int i) {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		T x = null;
		if (i < front.size()) {
			x = front.remove(i);
		} else {
			x = back.remove(i - front.size());
		}
		balanceDeques();
		
		return x;
	}
	
	public void clear() {
		front.clear();
		back.clear();
	}
	
	public int size() {
		return front.size() + back.size();
	}
	
	
	protected void balanceDeques() {
		final double scaleFactor = 1.00001;
        if (scaleFactor * front.size() < back.size()) {
			// Front deque is smaller than back
			// Need to move elements from front of back deque
			// to back of front deque
			while(front.size() < back.size()) {
				T removed = back.remove(0);
				front.add(front.size(), removed);
			}
        } else if (scaleFactor * back.size() < front.size()) {
            // Back deque is smaller than front
			// Need to move elements from back of the front deque
			// to front of back deque
			while(back.size() < front.size()) {
				T removed = front.remove(front.size() - 1);
				back.add(0, removed);
			}
        }
	}

}