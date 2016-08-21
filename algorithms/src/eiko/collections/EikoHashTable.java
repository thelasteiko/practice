package eiko.collections;
import eiko.error.HashException;
import eiko.error.HashFullException;
import eiko.error.HashKeyException;

/**
 * 
 * @author Melinda Robertson
 * @version 20160819
 */
public class EikoHashTable<K extends Comparable<K>,V> {
	
	public static final float LOAD_FACTOR = 0.5f;
	public static final float LOW_LOAD_FACTOR = 0.1f;
	public static final int INITIAL_CAPACITY = 1 << 4;

	private Node[] table;
	private int M;
	private int size;
	
	public EikoHashTable() {
		table = new Node[INITIAL_CAPACITY];
		M = INITIAL_CAPACITY;
	}
	/**
	 * Inserts a new key, value pair into the hash table.
	 * Keys must be unique.
	 * @param key is the key.
	 * @param value is the value.
	 * @throws HashException if a key can't be found, basically meaning there's no room,
	 * 							or that key already exists.
	 */
	public void put(K key, V value) throws HashException {
		check_size(size+1);
		int h = hash(key);
		int j = 0;
		while(table[h] != null && j < size) {
			if (table[h].key.equals(key))
				throw new HashKeyException(h, key.toString());
			j++;
			h = (h+j*j) % (M-1);
		}
		if (table[h] != null) throw new HashKeyException(h, key.toString());
		table[h] = new Node<K, V>(key, value);
		size++;
	}
	/**
	 * Retrieves the value for the given key.
	 * @param key is the key.
	 * @return the value or null if not found.
	 */
	public V get(K key) {
		Node n = getNode(key);
		if (n != null) return (V) n.value;
		else return null;
	}
	/**
	 * Removes a key,value pair from the hash table.
	 * @param key is the key.
	 * @return the value associated with the key or null if it can't be found.
	 * @throws HashException if an exception occurs while rehashing.
	 */
	public V remove(K key) throws HashException {
		int h = getIndex(key);
		if (h < 0) return null;
		Object temp = table[h].value;
		table[h] = null;
		check_size(--size);
		return (V) temp;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		//find the first node that isn't null and start there
		int j = 0;
		Node<K, V> temp = table[j++];
		while (temp == null) temp = table[j++];
		sb.append(j-1);
		sb.append(table[j-1].toString());
		for(int i = j; i < table.length; i++) {
			if (table[i] != null) {
				sb.append(", ");
				sb.append(i);
				sb.append(table[i].toString());
			}
		}
		sb.append("}");
		return sb.toString();
	}
	/**
	 * Finds a node in the hash table.
	 * @param key is the key.
	 * @return a node.
	 */
	private Node getNode(K key) {
		int i = getIndex(key);
		if (i >= 0) return table[i];
		else return null;
	}
	/**
	 * Finds the index of the associated key.
	 * @param key is the key.
	 * @return an index.
	 */
	private int getIndex(K key) {
		int h = hash(key);
		int j = 0;
		while(j < size) {
			if(table[h] != null &&
					table[h].key.equals(key)) 
				return h;
			j++;
			h = (h+j*j) % M;
		}
		return -1;
	}
	/**
	 * Checks the size of the table to see if it needs to be expanded or
	 * collapsed.
	 * @param n is the new number of items in the table.
	 * @throws HashFullException if there is some error in rehashing the table.
	 */
	private void check_size(int n) throws HashFullException {
		//I need to see if the current load is greater than 75% of the capacity
		//size / length
		if ( ((float) n / (float) table.length) > LOAD_FACTOR) {
			M <<= 1;
			table = rehash();
		} else if (table.length > INITIAL_CAPACITY 
				&& ((float) n / (float) table.length) < LOW_LOAD_FACTOR) {
			M >>= 1;
			//now I need to hash objects from the original array to the copy
			//and then replace the original
			table = rehash();
		}
		
	}
	
	/**
	 * Redistributes items in the table after a resize.
	 * @return a new table.
	 * @throws HashFullException if a slot cannot be found for an item.
	 */
	@SuppressWarnings("unchecked")
	private Node[] rehash() throws HashFullException {
		Node[] t = new Node[M];
		for(int i = 0, j = 0; i < table.length; i++, j = 0) {
			if (table[i] == null) continue;
			int h = hash(table[i].key);
			while (t[h] != null && j < size) {
				j++;
				h = (h+j*j) % M;
			}
			if (j >= size) throw new HashFullException(i);
			t[h] = table[i];
		}
		return t;
	}
	/**
	 * Creates an index for the key.
	 * @param key is the key.
	 * @return an index.
	 */
	private int hash(Object key) {
		int h = key.hashCode();
		//(a*x % 2^w) / 2^(w-M)
		//a is random number
		//w is word size (16)
		//h is hash code
		return ((3*h) >>> (16)) % (M-1);
	}
	
	private class Node<T extends K,T2 extends V> {
		T key;
		T2 value;
		public Node(T key, T2 value) {
			super();
			this.key = key;
			this.value = value;
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(":");
			sb.append(key);
			sb.append("=");
			sb.append(value);
			return sb.toString();
		}
	}
}
