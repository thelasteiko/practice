package eiko.collections;

import java.util.ArrayList;

/**
 * Properties:
 * 	represents words
 * 	each node has 1 letter
 * 	each node has to have the ability to hold a next reference to a min of 26 other nodes
 * 	
 * @author Melinda Robertson
 * @version 20160831
 */
public class TrieTree {
	public static final int NUMBER_LETTERS = 26;
	private Node root;
	private int size; //number of words added
	
	public TrieTree() {
		root = new Node(null);
		size = 0;
	}
	/**
	 * Adds a new word to the tree.
	 * @param word is the word to add.
	 */
	public void add(String word) {
		char[] a = word.trim().toCharArray();
		Node temp = root;
		int count = 0;
		for (char c: a) {
			if (temp.isEmpty()) return;
			if(!validChar(c)) {
				count++;
				continue;
			}
			temp = temp.add(c);
		}
		if (count >= a.length) return;
		size++;
	}
	/**
	 * Determines if the prefix is in the tree.
	 * @param prefix is the series of letters to look for.
	 * @return true if the prefix can be found, false otherwise.
	 */
	public boolean contains(String prefix) {
		Node n = getNode(prefix);
		if (n == null) return false;
		return true;
	}
	
	public ArrayList<String> getAll() {
		return get(root);
	}
	public ArrayList<String> get(String prefix) {
		Node n = getNode(prefix);
		return get(n);
	}
	/**
	 * Gets the best match for the prefix. The best match
	 * is a word, starting with the prefix, that has been added
	 * to the tree the most amount of times.
	 * @param prefix is the series of letters to look for.
	 * @return the highest frequency word that starts with the prefix.
	 */
	public String bestMatch(String prefix) {
		//I want to return the string representing the max
		//frequency
		Node temp = getNode(prefix);
		StringBuilder sb = new StringBuilder(prefix);
		while (!(temp == null || temp.isEmpty())) {
			temp = temp.getMax();
			if (temp == null) break;
			sb.append(temp.c);
		}
		return sb.toString();
	}
	/**
	 * Adds the given list of strings to the tree.
	 * @param list is a list of strings to add.
	 */
	public void addAll(String[] list) {
		for(String s: list) add(s);
	}
	/**
	 * The number of words added.
	 * @return the number of words added.
	 */
	public int size() {
		return size;
	}
	/**
	 * Removes all words.
	 */
	public void clear() {
		root = new Node(null);
	}
	/**
	 * Determines if no words have been added.
	 * @return true if the tree is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root.isEmpty();
	}
	/**
	 * Supposed to get the node at the end of the prefix.
	 * @param prefix is a string.
	 * @return the node representing the last character in prefix.
	 */
	private Node getNode(String prefix) {
		char[] a = prefix.trim().toCharArray();
		Node temp = root;
		int count = 0;
		for (char c: a) {
			if (temp.isEmpty()) return null;
			if (!validChar(c)) {
				count++;
				continue;
			}
			int i = getIndex(c);
			if (temp.next[i] != null) {
				temp = temp.next[i];
			} else return null;
		}
		if (count >= a.length) return null;
		return temp;
	}
	/**
	 * Returns a list of all distinct words in the tree.
	 * @return a list of all words in the tree.
	 */
	private ArrayList<String> get(Node n) {
		ArrayList<String> words = new ArrayList<String>();
		if(n.isEmpty()) return words;
		ArrayList<CharacterArray> temp = new ArrayList<CharacterArray>();
		for (Node c: n.next) {
			if (c != null) {
				get(c, 0, temp);
			}
		}
		for(int i = 0; i < temp.size(); i++) {
			words.add(temp.get(i).toString());
		}
		return words;
	}
	
	private CharacterArray get(Node n, int size, ArrayList<CharacterArray> a) {
		if (n.isEmpty()) {
			//start a new string
			CharacterArray m = new CharacterArray(size);
			//add this character to that string
			m.add(n.c);
			return m;
		}
		CharacterArray t = null;
		for(Node c: n.next) {
			if (c != null) {
				 t = get(c,size++,a);
				t.add(n.c);
				if (t.isFull()) a.add(t);
			}
		}
		return t;
	}
	
	private int getIndex(char c) {
		int i = Character.toLowerCase(c) - 97;
		if (i < 0 || i > NUMBER_LETTERS-1)
			throw new IndexOutOfBoundsException();
		return i;
	}
	
	private boolean validChar(char c) {
		return ((c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z'));
	}
	
	private class Node {
		Character c;
		long frequency;
		Node[] next;
		long max_freq;
		int max_index;
		
		public Node(Character c) {
			this.c = c;
			next = null;
			frequency = 0;
			size = 0;
			max_freq = 0;
			max_index = -1;
		}
		public Node add(char c) {
			if (next == null)
				next = new Node[NUMBER_LETTERS];
			int i = getIndex(c);
			if (next[i] == null)
				next[i] = new Node(c);
			next[i].frequency++;
			if (max_freq < next[i].frequency) {
				max_freq = next[i].frequency;
				max_index = i;
			}
			return next[i];
		}
		public boolean isEmpty() {
			return next == null;
		}
		public Node getMax() {
			if (max_index < 0) return null;
			return next[max_index];
		}
	}
	
	private class CharacterArray {
		char[] list;
		int place;
		
		public CharacterArray(int size) {
			list = new char[size];
			place = size-1;
		}
		
		public void add(char c) {
			if (place < 0) throw new IndexOutOfBoundsException();
			list[place--] = c;
		}
		
		public String toString() {
			return String.copyValueOf(list);
		}
		public boolean isFull() {return place < 0; }
	}
}
