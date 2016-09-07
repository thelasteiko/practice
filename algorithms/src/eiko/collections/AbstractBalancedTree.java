/**
 * 
 */
package eiko.collections;

/**
 * Defines shared resources for binary balanced trees.
 * @author Melinda Robertson
 * @version 20160902
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
	/**
	 * Finds and returns the node corresponding to the key.
	 * @param key is the key.
	 * @return a node or null if there is no node with the key.
	 */
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
	/**
	 * Removes a node while ensuring the properties of a BST.
	 * @param n is the node to remove.
	 */
	protected void removeNode(Node n) {
		if (n.leaf()) {
			replaceParentChild(n, null);
		}
		/*
		 *   n
		 *  /
		 * t
		 */
		else if (n.right == null && n.left != null) {
			Node temp = n.left;
			replaceParentChild(n, temp);
		}
		/*
		 * n
		 *  \
		 *   t
		 */
		else if (n.left == null && n.right != null) {
			Node temp = n.right;
			replaceParentChild(n, temp);
		}
		else if (n.left != null && n.right != null) {
			Node temp = successor(n);
			n.key = temp.key;
			n.value = temp.value;
			removeNode(temp);
		}
	}
	
	/**
	 * Determines which child n is of n's parent and replaces
	 * that child with r while making r's parent n's parent.
	 * @param n is the child to replace.
	 * @param r is the replacement child.
	 */
	protected void replaceParentChild(Node n, Node r) {
		if (r != null) r.parent = n.parent;
		if (n.parent == null) {
			root = r;
			return;
		}
		if (n.parent.left.equals(n)) {
			n.parent.left = r;
		} else if (n.parent.right.equals(n)) {
			n.parent.right = r;
		}
	}
	/**
	 * Gets a node that can reasonably replace the given one
	 * while maintaining the properties of a BST.
	 * @param n is a node to replace.
	 * @return a replacement node; either the min on the right,
	 * 						or the max on the left.
	 */
	protected Node successor(Node n) {
		Node m = findMin(n.right);
		if (m == null) m = findMax(n.left);
		return m;
	}
	/**
	 * Finds the key with the minimum value starting
	 * from node n.
	 * @param n is the starting node.
	 * @return the minimum node.
	 */
	protected Node findMin(Node n) {
		if (n == null) return n;
		Node temp = n;
		while (temp.left != null && !temp.leaf()) {
			temp = temp.left;
		}
		return temp;
	}
	/**
	 * Finds the key with the maximum value starting
	 * from node n.
	 * @param n is the starting node.
	 * @return the maximum node.
	 */
	protected Node findMax(Node n) {
		if (n == null) return n;
		Node temp = n;
		while (temp.right != null && !temp.leaf()) {
			temp = temp.right;
		}
		return temp;
	}
	/**
	 * Starting from temp, finds the parent node of the given key.
	 * If the key does not exist it returns the node which should be
	 * the key's parent.
	 * @param temp is the starting node.
	 * @param key is the key to find.
	 * @return the parent node of key.
	 */
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
	
	@Override
	public int size() {
		return size;
	}
	/**
	 * Used for binary trees.
	 * @author Melinda Robertson
	 * @version 20160902
	 */
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
