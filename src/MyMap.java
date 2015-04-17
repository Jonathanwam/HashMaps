import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class MyMap<K,V> implements Iterable<MyMap.MyEntry<K,V>> {
        int collision; // maintain the current count of collisions here.
		public LinkedList<MyEntry<K, V>>[] bucketArray;
        
	/**
	 * Create a MyMap instance with the specified number of 
	 * buckets.
	 * 
	 * @param buckets the number of buckets to make in this map
	 */
	@SuppressWarnings("unchecked")
	public MyMap(int buckets) {
		bucketArray = (LinkedList<MyEntry<K,V>> []) new LinkedList[buckets];	
		
		// initially everything should be null
		for (int i = 0; i < buckets; i++){
			bucketArray[i] = null;
		}
	}
	
	/**
	 * Puts an entry into the map.  If the key already exists, 
	 *  it's value is updated with the new value and the previous
	 *  value is returned.
	 *
	 * @param key the object used as a key to retrieve the value
	 * @param value the object stored in association with the key
	 * 
	 * @return the previously stored value or null if the key is new
	 */
	public V put(K key, V value) {
	        // don't forget hashcodes can be any integer value.  You'll
	        // need to compress them to ensure they give you a valid bucket.
		
		// get hashcode
		int index = key.hashCode();
		LinkedList<MyEntry<K, V>> newLL = new LinkedList<MyEntry<K, V>>();
        MyEntry<K, V> newElement = new MyEntry<K, V>(key, value);
		LinkedList<MyEntry<K,V>> bucket = bucketArray[index];
		
        
		if (bucket == null){
			bucket = newLL;
			newLL.add(newElement);
			}
		else {
			// check to see if it is in the LL
			// bucket.size() returns number of elements in the bucket
			for (int i = 0; i < bucket.size(); i++){
				MyEntry<K, V> tempNode = bucket.get(i);
				if (key.equals(tempNode.key)){
					V toReturn = tempNode.value; // found key, get ready to return old value
					tempNode.value = value; // replace value
					return toReturn;
				}
				// else it's a collision!
				else {
					collision++;
				}
			
			} // end of for loop. LL is not empty and does not contain key. Make new element.
			
			bucket.add(newElement);			
			
		}
		
		bucketArray[index] = bucket;
		
		return null; // new key
	}
	
	/**
	 * Retrieves the value associated with the specified key.  If 
	 * it exists, the value stored with the key is returned, if no 
	 * value has been associated with the key, null is returned.
	 * 
	 * @param key the key object whose value we wish to retrieve
	 * @return the associated value, or null
	 */
	public V get(K key) {
		// get hashcode
		int index = key.hashCode();

		LinkedList<MyEntry<K,V>> bucket = bucketArray[index];
		if (bucket == null) {
			return null; // bucket is empty
		}
		for (int i = 0; i < bucket.size(); i++){
			MyEntry<K, V> tempNode = bucket.get(i);
			if (key.equals(tempNode.key)){
				return tempNode.value; // found key, returning value
			}
		}
		
		return null; // bucket is not empty but there is no node for key
	}
	
	/**
	 *
	 * I've implemented this method, however, you must correctly 
	 * maintain the collisions member variable.
	 *
	 * @return the current count of collisions thus far.
	 */
	public int currentCollisions() {
		return collision;
	}
	/**
	 * Looks through the entire bucket where the specified key
	 * would be found and counts the number of keys in this bucket
	 * that are not equal to the current key, yet still have the
	 * same hash code.
	 * 
	 * @param key
	 * @return a count of collisions
	 */
	public int countCollisions(K key) {
		//get the hash
		int index = key.hashCode(); // index is the hash
		int count = 0;
		
		LinkedList<MyEntry<K,V>> bucket = bucketArray[index];
		for (int i = 0; i < bucket.size(); i++){
			MyEntry<K, V> tempNode = bucket.get(i);
			if (!key.equals(tempNode.key)){
				count++;
			}
		}
		return count;
	}
	/**
	 * Removes the value associated with the specified key, if it exists.
	 * @param key the key used to find the value to remove.
	 * @return the value if the key was found, or null otherwise.
	 */
	// find it (hashcode and traverse map and list)
	// copy the value in the node then delete the node's value and then return the value
	// else return null if key not found ...
	public V remove(K key) {
		// get the hash
		int index = key.hashCode(); // index is the hash
		
		LinkedList<MyEntry<K,V>> bucket = bucketArray[index];
		for (int i = 0; i < bucket.size(); i++){
			MyEntry<K, V> tempNode = bucket.get(i);
			if (key.equals(tempNode.key)){
				V oldVal = tempNode.value;
				tempNode.value = null;
				return oldVal;
			}
		}
		return null; // key not found
	}
	/**
	 * Returns the number of entries in this map
	 * @return the number of entries.
	 */
	// number of elements I presume...
	public int size() {
		int size = 0;
		// may need to remove the '- 1' ... will test when put and get actually works
		for (int i = 0; i < bucketArray.length - 1; i++){
			LinkedList<MyEntry<K,V>> bucket = bucketArray[i];
			if (bucket != null){
				size += bucket.size();
			}
		}
		return size;
	}
	
	/**
	 * Creates and returns a new Iterator object that 
	 * iterates over the keys currently in the map. The iterator 
	 * should fail fast, and does not need to implement the remove
	 * method.
	 * 
	 * @return a new Iterator object  
	 */
	public Iterator<MyEntry<K,V>> iterator() {
		return null;
	}
	
	public static class MyEntry<K,V> {
		K key;
		V value;
		
		public MyEntry(K k, V v) {
			key = k;
			value = v;
		}
	}

	
	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	public static void main(String [] args) throws FileNotFoundException{
		MyMap map = new MyMap(999999); // new hashmap with 9999 buckets, plenty more than I need
		int myVal;
		
		/*
		 * smalltext.txt includes
		 * hello hello hello no no no no yes please very much so thank you this is perfect
		 * So after using put() on each word, I should have...
		 * get(hello) = 3, get(no) = 4, and the rest 1 each
		 */
		Scanner in = new Scanner(new FileReader("bigtext.txt"));
		
		while (in.hasNext()){
			myVal = 0;
			Word newWord = new Word(in.next());
			
			// get old value			
			if (map.get(newWord) == null) myVal = 0;
			else myVal = (int) map.get(newWord);
			// create new value
			myVal++;
			
			map.put(newWord, myVal); // this is theoretically what I want to do...
			
		}
		
		Word the = new Word("the");
		System.out.println(map.get(the)); // should return 71744
		Word cat = new Word("cat");
		System.out.println(map.get(cat)); // should return 4
		Word hello = new Word("hello");
		System.out.println(map.get(hello)); // should return 1
		Word notinlist = new Word("thisshouldntbeinthelistatall");
		System.out.println(map.get(notinlist)); // should return null
		
		System.out.println("num of collisions = " + map.collision); // 184249 total collisions
		
		System.out.println(map.countCollisions(the)); // Has 1 collision
		System.out.println(map.countCollisions(hello)); // has 2 collisions
		System.out.println(map.countCollisions(cat)); // has 1 collision
	}


}