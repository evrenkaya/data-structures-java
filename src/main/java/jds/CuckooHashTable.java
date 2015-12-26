package comp2402a4;

import java.util.Iterator;
import java.util.Random;

/**
 * This class implements the cuckoo hash 
 * 
 * See: Rasmus Pagh, Flemming Friche Rodler, Cuckoo Hashing, Algorithms - ESA 2001, 
 * Lecture Notes in Computer Science 2161, Springer 2001, ISBN 3-540-42493-8
 *
 * @param <T>
 */
public class CuckooHashTable<T> implements USet<T> {
	
	protected Factory<T> f;
	
	/**
	 * The two hash tables
	 */
	protected T[] t1;
	protected T[] t2;

	/**
	 * The "dimension" of the virtual table (t1.length = t2.length = 2^d)
	 */
	int d;

	/**
	 * The number of elements in the hash table
	 */
	int n;
		
	/**
	 * The multipliers for the two hash functions
	 */
	public int z1;
	public int z2;

	/**
	 * The number of bits in an int
	 */
	protected static final int w = 32;
	
	/**
	 * Create a new empty hash table with hash tables
	 * h1(x) using z1
	 * h2(2) using z2
	 */
	public CuckooHashTable(Class<T> t, int z1, int z2) {
		f = new Factory<>(t);
		
		clear();
		
		this.z1 = z1;
		this.z2 = z2;
		
	}
	
	/**
	 * Resize the tables so they each have size 2^d 
	 */
	protected void resize() {
		// 3 * n accounts for the whole "Hash Table" 
		// but there are 2 hash table arrays, so we divide by 2
		//System.out.println("Resizing");
		final int max = 3 * n / 2;
		d = 1;
		while((1 << d) < max) d++;
		T[] oldT1 = t1;
		T[] oldT2 = t2;
		// Create new tables each with size 2^d
		t1 = f.newArray(1 << d);
		t2 = f.newArray(1 << d);
		//System.out.println("old t1 length: " + oldT1.length);
		//System.out.println("t1 length: " + t1.length);
		//rehash(null);
		
		for(int k = 0; k < oldT1.length; k++) {
			if(oldT1[k] != null) {
				// Found a non-null element that needs to be transferred to the new t1
				int h1 = hash1(oldT1[k]);
				while(t1[h1] != null) {
					// Find the correct position to insert into new t1 array
					h1 = (h1 == t1.length - 1) ? 0 : h1 + 1;
				}
				t1[h1] = oldT1[k];
			}
			
			if(oldT2[k] != null) {
				// Found a non-null element that needs to be transferred to the new t2
				int h2 = hash2(oldT2[k]);
				while(t2[h2] != null) {
					// Find the correct position to insert into new t1 array
					h2 = (h2 == t2.length - 1) ? 0 : h2 + 1;
				}
				t2[h2] = oldT2[k];
			}
		}
	}

	/**
	 * Clear the tables
	 */
	public void clear() {
		d = 1;
		n = 0;
		t1 = f.newArray(1 << d);
		t2 = f.newArray(1 << d);
	}
	
	/**
	 * Return the number of elements stored in this hash table
	 */
	public int size() {
		return n;
	}

	public boolean add(T x) {
		if(find(x) != null) return false;
		if(n > (t1.length + t2.length)/2) resize();
		
		int h1 = hash1(x);
		int h2;
		//System.out.println("x: " + x);
		//System.out.println("h1: " + h1 + "\n");
		// Store a pointer to the element that originally occupied the position
		T originalElement = t1[h1];
		
		// Insert x into the position regardless of 
		// whether another element was already there
		t1[h1] = x;
		n++;
		if(originalElement != null) {
			
			// Need to do some "ejections" here because the position is occupied
			int numEjections = 1;
			// Keep track of the current element that is being "ejected"
			T currElement = originalElement;
			
			while(currElement != null) {
				//System.out.println("currElement: " + currElement);
				if(numEjections % 2 == 0) {
					// Even number of ejections, so we are in t1
					
					// Get the hashcode for the current element in t1
					h1 = hash1(currElement);
					
					if(t1[h1] == null) {
						// Found a valid place to store it
						t1[h1] = currElement;
						currElement = null;
					} else {
						// Need to switch places and keep searching
						T temp = t1[h1];
						t1[h1] = currElement;
						currElement = temp;
						numEjections++;
					}
					
				} else {
					// Odd number of ejections, so we are in t2
					
					// Get the hashcode for the current element in t2
					h2 = hash2(currElement);
					
					if(t2[h2] == null) {
						// Found a valid place to store it
						t2[h2] = currElement;
						currElement = null;
					} else {
						// Need to switch places and keep searching
						T temp = t2[h2];
						t2[h2] = currElement;
						currElement = temp;
						numEjections++;
					}
				}
				
				
				if(numEjections > n) {
					//System.out.println("Rehashing");
					//System.out.println("currElement: " + currElement);
					rehash(currElement);
					break;
				}
			}
			
		}
		
		
		return true;	
	}
		
	
	public T remove(T x) {
		Location l = findLocation(x);
		if(l == null) return null;
		
		l.hashTable[l.index] = null;
		n--;
		
		return x;
	}

	/**
	 * Get the copy of x stored in this table.
	 * @param x - the item to get 
	 * @return - the element y stored in this table such that x.equals(y)
	 * is true, or null if no such element y exists
	 */
	@SuppressWarnings("unchecked")
	public T find(Object x) {
		if(findLocation(x) != null) return (T) x;
		return null;
	}

	/**
	 * iterator for the hash table.  you do not need to implement this (but you can if you wish)
	 *
	 */
	public Iterator<T> iterator() {
		return null;
	}
	
	private void rehash(T floatingElement) {
		Random r = new Random();
		z1 = r.nextInt(Integer.MAX_VALUE/2) * 2 + 1;
		z2 = r.nextInt(Integer.MAX_VALUE/2) * 2 + 1;
		
		T[] oldTable1 = t1;
		T[] oldTable2 = t2;
		
		t1 = f.newArray(1 << d);
		t2 = f.newArray(1 << d);
		
		if(floatingElement != null) {
			add(floatingElement);
			n--;                                            
		}
		//System.out.println("old t1 length: " + oldTable1.length);
		//System.out.println("t1 length: " + t1.length);
		
		for(int i = 0; i < oldTable1.length; i++) {
			
			if(oldTable1[i] != null) {
				add(oldTable1[i]);
				n--;
			}
			
		}
		
		for(int i = 0; i < oldTable2.length; i++) {
			if(oldTable2[i] != null) {
				add(oldTable2[i]);
				n--;
			}
			
		}
	}
	
	private int hash1(Object x) {
		return (z1 * (x.hashCode())) >>> (w - d);
	}
	
	private int hash2(Object x) {
		return (z2 * (x.hashCode())) >>> (w - d);
	}
	
	private Location findLocation(Object x) {
		int h1 = hash1(x);
		int h2;
		if(t1[h1] == x) return new Location(t1, h1);
		
		h2 = hash2(x);
		if(t2[h2] == x) return new Location(t2, h2);
		
		return null;
	}
	
	public void printTable() {
		System.out.println("Table 1: ");
		for(int i = 0; i < t1.length; i++) {
			if(t1[i] != null)
				System.out.println(t1[i]);
		}
		
		System.out.println("\nTable 2: ");
		for(int i = 0; i < t2.length; i++) {
			if(t2[i] != null)
				System.out.println(t2[i]);
		}
	}

	private final class Location {
		public T[] hashTable;
		public int index;
		
		public Location(T[] table, int i) {
			hashTable = table;
			index = i;
		}
	}
	
	
}
