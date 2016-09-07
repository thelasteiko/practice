package eiko.collections;

import eiko.drive.Util;

/**
 * @author Melinda Robertson
 * @version 20160901
 */
public class AVLTree extends AbstractBalancedTree<Integer, String> {

	public static final int BALANCE_FACTOR = 2;
	
	@Override
	public String in_order() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pre_order() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String post_order() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Integer key, String value) {
		AVLNode temp = root();
		temp = (AVLNode) getParentNode(temp, key);
		if (temp.key > key)
			temp.left = new Node(temp, key, value);
		else if (temp.key < key)
			temp.right = new Node(temp, key, value);
		balance(temp.parent);
	}
	
	private void balance(Node temp) {
		//check balance between two branches
		//if they are out of balance, rotate
		int r = heightDown(temp.right);
		int l = heightDown(temp.left);
		if (Math.abs(r-l) >= BALANCE_FACTOR) {
			if (temp.right != null) {
				if (temp.right.right != null)
					rotateLeft(temp);
				else if (temp.right.left != null)
					rotateRightLeft(temp);
			} else if (temp.left != null) {
				if (temp.left.right != null)
					rotateLeftRight(temp);
				else if (temp.left.left != null)
					rotateRight(temp);
			}
		}
	}

	@Override
	public String get(Integer key) {
		AVLNode temp = (AVLNode) getNode(key);
		return temp.value;
	}

	@Override
	public String remove(Integer key) {
		Node temp = getNode(key);
		if (temp != null) {
			Node p = temp.parent;
			removeNode(temp);
			size--;
			if (p != null) balance(p);
		}
		return temp.value;
	}

	@Override
	public void addAll(String[] list) {
		//go for adding per recursion
		addAllhelper(list, 0, list.length-1);
	}
	/**
	 * Adds items from a list by splitting in in halves each time.
	 * This hopefully prevents the tree from having to rotate a lot.
	 * @param list is the list.
	 * @param s is the starting index.
	 * @param e is the ending index.
	 */
	private void addAllhelper(String[] list, int s, int e) {
		if (s > e) return;
		int m = (s+e) / 2;
		add(m, list[m]);
		addAllhelper(list, s, m-1);
		addAllhelper(list, m+1, e);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
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
	
	private AVLNode root() {
		return (AVLNode) root;
	}
	
	private class AVLNode extends AbstractBalancedTree<Integer,String>.Node {
		int depth;
		public AVLNode(AVLNode parent, Integer key, String value) {
			super(parent, key, value);
			if (parent == null) depth = 0;
			else depth = parent.depth + 1;
		}
		
	}
}
