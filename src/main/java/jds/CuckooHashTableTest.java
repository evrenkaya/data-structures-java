package jds;

import java.util.Random;

public class CuckooHashTableTest {
	
	public static void main(String[] args) {
		Random r  = new Random();
		/*CuckooHashTable<Integer> hashTable = new CuckooHashTable<>(Integer.class, 
				r.nextInt(Integer.MAX_VALUE/2) * 2 + 1, r.nextInt(Integer.MAX_VALUE/2) * 2 + 1);*/
		
		/*Hashtable<Integer, Integer> hashTable = new Hashtable<>();*/
		
		LinearHashTable<Integer> hashTable = new LinearHashTable<>(-1);
		
		for(int i = 0; i < 100000; i++) {
			hashTable.add(i);
		}
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			hashTable.find(r.nextInt(100000));
		}
		
		for(int i = 0; i < 1000000; i++) {
			hashTable.find(r.nextInt(100000) + 100000);
		}
		long elapsed = System.currentTimeMillis() - start;
		
		System.out.println("Time: " + elapsed);

	}

}
