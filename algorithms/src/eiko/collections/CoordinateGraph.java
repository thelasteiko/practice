package eiko.collections;

import eiko.error.HashException;

/**
 * This graph is represented by a matrix because it has x,y coordinates.
 * @author Melinda Robertson
 * @version 20160908
 */
public class CoordinateGraph {
	
	public static final int DEFAULT_SIZE = 4;
	
	private Node[][] nodes;
	/**
	 * Rows are listed before columns.
	 */
	public CoordinateGraph() {
		//4x4 grid
		nodes = new Node[DEFAULT_SIZE][DEFAULT_SIZE];
	}
	
	public CoordinateGraph(int rows, int cols) {
		nodes = new Node[rows][cols];
	}
	
	public void add(int x, int y) {
		nodes[x][y] = new Node(x, y);
	}
	
	public void remove(int x, int y) {
		Node n = nodes[x][y];
		if (n.child != null) n.child.parent = null;
		if (n.parent != null) n.parent.child = null;
		nodes[x][y] = null;
	}
	
	public static CoordinateGraph createGraph(int[][] graph) {
		CoordinateGraph cg = new CoordinateGraph(graph.length, graph[0].length);
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if(graph[i][j] == 1) cg.add(i, j);
			}
		}
		return cg;
	}
	
	/**
	 * A* requires:
	 *  - an open list (min heap)
	 *  - a closed list (hash map)
	 *  - a list of predetermined 'scores' for each node
	 * @param from
	 * @param to
	 * @return
	 */
	public String astar(int x1, int y1, int x2, int y2) throws Exception {
		//closed list
		//I need an easy way to look up nodes...to see if they are in the closed
		//list, the best thing would be by x,y coordinate b/c that's how each node
		//is represented.
		EikoHashTable<Point, Node> closed = new EikoHashTable<Point, Node>();
		//open list
		MinHeap<Node> open = new MinHeap<Node>();
		Node next = nodes[x1][y1];
		Node prev = null;
		Node target = nodes[x2][y2];
		next.estimate(null, target);
		loadN(next, target, open);
		while(!open.isEmpty() && next != target) {
			prev = next;
			closed.put(prev.p, prev);
			//this could be better, maybe not add the nodes at all...
			while (next != null && closed.contains(next.p)) next = open.remove();
			if (next == null) return "";
			if (next == target) break;
			if (!next.p.isNextTo(prev.p)) {
				prev = backtrack(next, closed);
			}
			//set parent and child
			prev.child = next;
			next.parent = prev;
			loadN(next, target, open);
		}
		return "";
	}
	/**
	 * Loads the neighbors of n to the heap and estimates the cost
	 * of getting to t.
	 * @param n is the starting node.
	 * @param t is the target node.
	 * @param heap is the heap.
	 */
	private void loadN(Node n, Node t, MinHeap<Node> heap) {
		Node m = getNode(n.p.get(0));
		if (m != null) heap.add(m.estimate(n,t));
		m = getNode(n.p.get(1));
		if (m != null) heap.add(m.estimate(n,t));
		m = getNode(n.p.get(2));
		if (m != null) heap.add(m.estimate(n,t));
		m = getNode(n.p.get(3));
		if (m != null) heap.add(m.estimate(n,t));
	}
	/**
	 * Gets all grid points that could be next to the given node
	 * and checks to see if they are in the closed list. If they are,
	 * it chooses the one with the least cost.
	 * @param n is the start node.
	 * @param closed is the list of visited nodes.
	 * @return the neighboring node with the lowest cost.
	 */
	private Node backtrack(Node n, EikoHashTable<Point,Node> closed) {
		//if the next node is not next to the previous node
		//I need to find a node in closed that IS next to next
		//and out of all in closed that are next to next,
		//I need the one with the lowest cost, f()
		Point[] temp = new Point[4];
		for (int i = 0; i < 4; i++) temp[i] = n.p.get(i);
		int min = Integer.MAX_VALUE;
		Node minNode = null;
		for (Point p: temp) {
			Node m = closed.get(p);
			if (m != null && m.f() < min) {
				min = m.f();
				minNode = m;
			}
		}
		return minNode;
	}
	/**
	 * Returns the node at point p.
	 * @param p is the point.
	 * @return a node or null.
	 */
	private Node getNode(Point p) {
		return nodes[p.r][p.c];
	}
	
	private class Node implements Comparable<Node> {
		Point p;
		Node parent;
		Node child;
		int h; //cost from origin
		int g; //cost to destination
		
		public Node(int x, int y) {
			p = new Point(x,y);
			parent = null;
			this.h = 0;
			this.g = Integer.MAX_VALUE;
		}
		/**
		 * Calculates the estimated cost of reaching the destination
		 * given the cost from the previous node.
		 * @param prev is the previous node.
		 * @param destination is the target node.
		 * @return this.
		 */
		public Node estimate(Node prev, Node destination) {
			if (prev == null) h = 0;
			else h = prev.h+1;
			int n = (destination.p.r - p.r) + (destination.p.c - p.c);
			g = Math.abs(n);
			return this;
		}
		
		public int f() {
			return h + g;
		}

		@Override
		public int compareTo(Node o) {
			return f() - o.f();
		}

	}
	
	private class Point {
		int r, c;
		
		/**
		 * 
		 * @param x is the row.
		 * @param y is the column.
		 */
		public Point(int x, int y) {
			this.r = x;
			this.c = y;
		}
		@Override
		public int hashCode() {
			return r + c;
		}
		/**
		 * n, s, e, w
		 * 0, 1, 2, 3
		 * @param dir
		 * @return
		 */
		public Point get(int dir) {
			switch (dir) {
			case 0:
				return new Point(r-1,c);
			case 1:
				return new Point(r+1,c);
			case 2:
				return new Point(r,c+1);
			case 3:
				return new Point(r,c-1);
			}
			return null;
		}
		public boolean isNextTo(int r, int c) {
			return ((this.r == r-1 && this.c == c)
					|| (this.r == r+1 && this.c == c)
					|| (this.r == r && this.c == c-1)
					|| (this.r == r && this.c == c+1));
		}
		
		public boolean isNextTo(Point p) {
			return ((this.r == p.r-1 && this.c == p.c)
					|| (this.r == p.r+1 && this.c == p.c)
					|| (this.r == p.r && this.c == p.c-1)
					|| (this.r == p.r && this.c == p.c+1));
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Point) {
				Point p = (Point) o;
				return (r == p.r && c == p.c);
			}
			return false;
		}
	}
}
