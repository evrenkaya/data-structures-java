package comp2402a5;

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

	public static void main(String[] args) {
		final int numTests = 20;
		final int numElements = 262144;
		System.out.println("CORRECTNESS TESTS - Dary heap(d=3)");
		System.out.println("----------------------------------");
		correctnessTests();
		
		// Creation of large binary heap vs. dary heap
		System.out.println("CREATION TESTS - Adding 262144 elements (20 trials)");
		System.out.println("---------------------------------------------------");
		createManyBinaryHeapTests(numTests, numElements);

		createManyHeapTests(2, numTests, numElements);
		createManyHeapTests(3, numTests, numElements);
		createManyHeapTests(5, numTests, numElements);
		createManyHeapTests(10, numTests, numElements);
		System.out.println();
		
		// Now test the add/remove operations for binary heap and dary heap
		// averaged over 20 trials
		System.out.println("ADD/REMOVE TESTS - Roughly Maintaining Heap Size");
		System.out.println("------------------------------------------------");
		System.out.println("Performing " + numElements +
				" adds/removes for binary heap, over " + numTests + " trials...");
		testBinaryHeapOperations(numTests, numElements);
		
		System.out.println("The following tests perform " + numElements +
				" adds/removes for various dary heaps,\nall averaged over " + numTests + " trials:\n");
		System.out.print("d=2: ");
		testHeapOperations(2, numTests, numElements);
		
		System.out.print("d=3: ");
		testHeapOperations(3, numTests, numElements);
		
		System.out.print("d=5: ");
		testHeapOperations(5, numTests, numElements);
		
		System.out.print("d=10: ");
		testHeapOperations(10, numTests, numElements);
	}
	
	/*
	 * Functions used for testing either binary heap or dary heap in main()
	 */
	
	private static void testBinaryHeapOperations(int numTests, int numElements) {
		BinaryHeap<Integer> h = generateRandomBinaryHeap(numElements);
		Random r = new Random();
		long start = System.currentTimeMillis();
		long elapsed;
		
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < numElements; j++) {
				if (r.nextBoolean()) h.add(r.nextInt());
				else h.remove();
			}
		}
		
		elapsed = (System.currentTimeMillis() - start) / numTests;
		System.out.println("Time taken: " + elapsed + "ms\n");
	}
	
	private static void testHeapOperations(int d, int numTests, int numElements) {
		BinaryHeap<Integer> h = generateRandomHeap(d, numElements);
		Random r = new Random();
		long start = System.currentTimeMillis();
		long elapsed;
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < numElements; j++) {
				if (r.nextBoolean()) h.add(r.nextInt());
				else h.remove();
			}
		}
		
		elapsed = (System.currentTimeMillis() - start) / numTests;
		System.out.println(elapsed + "ms\n");
	}
	
	private static void createManyBinaryHeapTests(int numTests, int numElements) {
		long start = System.currentTimeMillis();
		long elapsed;
		for(int i = 0; i < numTests; i++) {
			generateRandomBinaryHeap(numElements);
		}
		elapsed = (System.currentTimeMillis() - start) / numTests;
		System.out.println("Average time to create binary heap: " + elapsed + "ms");
	}
	
	private static void createManyHeapTests(int d, int numTests, int numElements) {
		long start = System.currentTimeMillis();
		long elapsed;
		for(int i = 0; i < numTests; i++) {
			generateRandomHeap(d, numElements);
		}
		elapsed = (System.currentTimeMillis() - start) / numTests;
		System.out.println("Average time to create d-ary(d=" + d +") heap: " + elapsed + "ms");
	}
	
	private static BinaryHeap<Integer> generateRandomBinaryHeap(int numElements) {
		BinaryHeap<Integer> h = new BinaryHeap<Integer>(Integer.class);
		Random r = new Random();
		for(int i = 0; i < numElements; i++) {
			h.add(r.nextInt());
		}
		
		return h;
	}
	
	private static DaryHeap<Integer> generateRandomHeap(int d, int numElements) {
		DaryHeap<Integer> h = new DaryHeap<Integer>(Integer.class, d);
		Random r = new Random();
		for(int i = 0; i < numElements; i++) {
			h.add(r.nextInt());
		}
		
		return h;
	}
	
	private static void correctnessTests() {
		// Create a 3-ary heap
		DaryHeap<Integer> h = new DaryHeap<Integer>(Integer.class, 3);
		Random r = new Random();
		
		System.out.println("Adding 10 random integers...");
		for(int i = 0; i < 10; i++) {
			h.add(r.nextInt(20));
		}

		System.out.println("Heap:  " + h);
		System.out.println("Removing all elements...");
		while(!h.isEmpty()) {
			System.out.println("Removed: " + h.remove());
		}
		System.out.println();
	}
	
	

}
