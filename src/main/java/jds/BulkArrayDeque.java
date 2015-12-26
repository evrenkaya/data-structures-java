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
}
