package jds;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class BulkArrayDeque<T> extends ArrayDeque<T> {
	
	public BulkArrayDeque(Class<T> clazz) {
		super(clazz);
	}
	
	// Removes elements in range i(inclusive) - j(exclusive)
	public void removeRange(int i, int j) {
		if (i < 0 || i >= size() || j < 0 || j > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (i >= j) {
			// i must be less than j, so throw an exception here
			throw new IllegalArgumentException();
		}
		
		int numToRemove = j - i;
		int numToRemain = size() - numToRemove;

		// Need to create new array and copy remaining elements over
		T[] newArray = factory.newArray(numToRemain);

		for (int k = 0; k < i; k++) {
			newArray[k] = backingArray[circularIndex(k)];
		}
		
		for (int k = i; k < numToRemain; k++) {
			int shiftedIndex = k + numToRemove;
			newArray[k] = backingArray[circularIndex(shiftedIndex)];
		}
		
		backingArray = newArray;
		headIndex = 0;
		n = backingArray.length;
	}
	
	public static void doIt(BufferedReader r, PrintWriter w){
		// Testing code
		BulkArrayDeque<String> deque = new BulkArrayDeque<>(String.class);
		for (int i = 0; i < 100; i++) {
			deque.add(i, Integer.toString(i));
		}
		
		deque.removeRange(0, 100);
		/*System.out.println("SIZE: " + deque.size());
		for (int i = 0; i < deque.size(); i++) {
			System.out.println(deque.get(i));
		}*/
	}
	
	
	/**
	 * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
	 * and System.out or from filenames specified on the command line, then call doIt.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader r;
			PrintWriter w;
			if (args.length == 0) {
				r = new BufferedReader(new InputStreamReader(System.in));
				w = new PrintWriter(System.out);
			} else if (args.length == 1) {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(System.out);				
			} else {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(new FileWriter(args[1]));
			}
			long start = System.nanoTime();
			doIt(r, w);
			w.flush();
			long stop = System.nanoTime();
			System.err.println("Execution time: " + 10e-9 * (stop-start));
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
}
