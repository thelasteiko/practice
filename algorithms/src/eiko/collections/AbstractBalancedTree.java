/**
 * 
 */
package eiko.collections;

import eiko.drive.Util;

/**
 * @author Melinda Robertson
 * @version 20160826
 */
public abstract class AbstractBalancedTree<K extends Comparable<? super K>, V> implements TreeInterface<K, V> {
	
	protected Node root;
	protected int size;
	
	protected void rotateLeft(Node n) {
		//a->b->c
		//1. b.left = a
		//2. a.parent = b
		Node temp = n.right;
		if (n.parent != null) {
			temp.parent = n.parent;
			n.parent.right = temp;
		}
		temp.left = n;
		n.parent = temp;
		n.right = null;
		if (n == root) {
			root = temp;
		} 
	}
	
	protected void rotateRight(Node n) {
		Node temp = n.left;
		if (n.parent != null) {
			temp.parent = n.parent;
			n.parent.left = temp;
		}
		temp.right = n;
		n.parent = temp;
		n.left = null;
		if (n == root) {
			root = temp;
		}
	}
	
	protected void rotateLeftRight(Node n) {
		
	}
	
	protected void rotateRightLeft(Node n) {
		
	}
	//TODO move the height to AVL when it's made
	protected int heightUp(Node n) {
		int h = 0;
		Node temp = n;
		while (temp != root) {
			temp = temp.parent;
			h++;
		}
		return h;
	}
	
	protected int heightDown(Node n) {
		if (n == null) return 0;
		if (n.right == null && n.left == null) return 1;
		return 1 + Util.max(heightDown(n.right), heightDown(n.left));
	}
	
	protected Node getNode(K key) {
		Node temp = root;
		while(temp != null || !temp.key.equals(key)) {
			if (temp.key.compareTo(key) > 0)
				temp = temp.right;
			else if (temp.key.compareTo(key) < 0)
				temp = temp.left;
		}
		return temp;
	}
	
	protected class Node {
		protected K key;
		protected V value;
		
		protected Node left;
		protected Node right;
		protected Node parent;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public Node(Node parent, K key, V value) {
			this.parent = parent;
			this.key = key;
			this.value = value;
		}
	}
}
