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
	
	/**
	 * Rotates a node left so that the right child
	 * becomes the parent.
	 * n
	 *  \
	 *   b   -->  b
	 *    \      / \
	 *     c    n   c
	 * @param n is the node to rotate.
	 */
	protected void rotateLeft(Node n) {
		if (n == null) return;
		Node temp = n.right;
		if (n.parent != null) {
			temp.parent = n.parent;
			n.parent.right = temp;
		}
		if (temp.left != null) {
			n.left = temp.left;
			n.left.parent = n;
		}
		temp.left = n;
		n.parent = temp;
		n.right = null;
		if (n == root) {
			root = temp;
		} 
	}
	/**
	 * Rotates a node right so that the left child
	 * becomes the parent.
	 *     n
	 *    /
	 *   b   -->  b
	 *  /        / \
	 * c        c   n
	 * @param n is the node to rotate.
	 */
	protected void rotateRight(Node n) {
		if (n == null) return;
		Node temp = n.left;
		if (n.parent != null) {
			temp.parent = n.parent;
			n.parent.left = temp;
		}
		if (temp.right != null) {
			n.right = temp.right;
			n.right.parent = n;
		}
		temp.right = n;
		n.parent = temp;
		n.left = null;
		if (n == root) {
			root = temp;
		}
	}
	/**
	 * Rotates a node left then right.
	 *    c         b
	 *   /         / \
	 *  n    -->  n   c
	 *   \
	 *    b
	 * @param n is the node to rotate.
	 */
	protected void rotateLeftRight(Node n) {
		rotateLeft(n);
		if (n.parent != null) rotateRight(n.parent.parent);
	}
	/**
	 * Rotates a node right then left.
	 *  c           b
	 *   \         / \
	 *    n  -->  c   n
	 *   /
	 *  b
	 * @param n is the node to rotate.
	 */
	protected void rotateRightLeft(Node n) {
		rotateRight(n);
		if (n.parent != null) rotateLeft(n.parent.parent);
	}
	
	protected Node getNode(K key) {
		Node temp = root;
		while(temp != null && !temp.key.equals(key)) {
			if (temp.key.compareTo(key) > 0)
				temp = temp.right;
			else if (temp.key.compareTo(key) < 0)
				temp = temp.left;
		}
		return temp;
	}
	
	protected void removeNode(Node n) {
		if (n.leaf()) {
			if (n.parent.right.equals(n))
				n.parent.right = null;
			if (n.parent.left.equals(n))
				n.parent.left = null;
			return;
		}
		if (n.right == null && n.left != null) {
			Node temp = n.left;
			temp.parent = n.parent;
			if (n.parent.left.equals(n))
				n.parent.left = temp;
			else if (n.parent.right.equals(n))
				n.parent.right = temp;
		}
	}
	
	protected Node successor(Node n) {
		Node m = findMin(n.right);
		if (m == null) m = findMax(n.left);
		return m;
	}
	
	protected Node findMin(Node n) {
		if (n == null) return n;
		Node temp = n;
		while (!temp.leaf()) {
			temp = temp.left;
		}
		return temp;
	}
	
	protected Node findMax(Node n) {
		if (n == null) return n;
		Node temp = n;
		while (!temp.leaf()) {
			temp = temp.right;
		}
		return temp;
	}
	
	protected Node getParentNode(Node temp, K key) {
		if (temp.key.compareTo(key) > 0) {
			if(temp.right == null) return temp;
			else return getParentNode(temp.right, key);
		} else if (temp.key.compareTo(key) < 0) {
			if (temp.left == null) return temp;
			else return getParentNode(temp.left, key);
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
		
		public boolean leaf() {
			return (left == null && right == null);
		}
		public boolean root() {
			return parent == null;
		}
	}
}
