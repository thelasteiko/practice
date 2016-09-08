package eiko.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class CharacterGraph {
	
	public static final int DEFAULT_SIZE = 26;
	public static final int OFFSET = -97;
	
	private Node[] nodes;
	private int size;
	
	public CharacterGraph() {
		nodes = new Node[DEFAULT_SIZE];
		size = 0;
	}
	
	public void add(Character c) {
		if (!validChar(c)) return;
		int index = Character.toLowerCase(c) + OFFSET;
		if (nodes[index] != null) return;
		Node n = new Node(Character.toLowerCase(c));
		nodes[index] = n;
		size++;
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (size != 0) {
			int i = 0;
			while (nodes[i] == null) i++;
			sb.append(nodes[i].toString());
			for (; i < nodes.length; i++) {
				if (nodes[i] != null) {
					sb.append(',');
					sb.append(nodes[i].toString());
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	public void link(Character from, Character to, int cost, boolean bi) {
		Node f = getNode(from);
		Node t = getNode(to);
		if (f != null && t != null) {
			f.link(t, cost);
			if (bi) t.link(f, cost);
		}
	}
	
	public String bfs(char from, char to) {
		StringBuilder sb = new StringBuilder();
		int distance = Integer.MAX_VALUE;
		CircularQueue<Node> a = new CircularQueue<Node>();
		int count = 0;
		Node current = getNode(from);
		current.distance = 0;
		a.enqueue(current);
		sb.append(current.c);
		while(!a.isEmpty() && count < size) {
			if (current.c == to)
				break;
			current = a.dequeue();
			sb.append(current.c);
			current.visited = true;
			for (Edge e: current.links) {
				if (e.to.visited) continue;
				e.to.distance = current.distance + e.cost;
				a.enqueue(e.to);
			}
			count++;
		}
		sb.append(" ");
		sb.append(distance);
		return sb.toString();
	}
	/**
	 * 1. Each node has INFINITY distance from root.
	 * 2. Each node is not visited. (optional)
	 * 3. Put root edges in queue in ascending order.
	 * 4. While queue is not empty
	 * 		4a. Dequeue next edge.
	 * 		4b. If the 'to' node's distance is greater than the
	 * 			'from' node's distance + the edge's cost
	 * 			4b1a. Replace the 'to' node's distance with 'from' + cost
	 * 			4b1b. Set 'to' node's parent to 'from'
	 * 		4c. Add the 'to' node's edges to queue in ascending order.
	 * 		4d. Set 'to' to visited. (optional)
	 * @param from
	 * @param to
	 * @return
	 */
	public String dijkstra(char from, char to) {
		Node prev = getNode(from);
		if (prev == null) return "";
		CircularQueue<Edge> q = new CircularQueue<Edge>();
		prev.distance = 0;
		prev.visited = true;
		prev.sortEdges();
		for (Edge e: prev.links) {
			q.enqueue(e);
		}
		while (!q.isEmpty()) {
			Edge current = q.dequeue();
			int cost = current.from.distance + current.cost;
			if (current.to.distance > cost) {
				current.to.distance = cost;
				current.to.parent = current.from;
			}
			current.to.sortEdges();
			for (Edge e: current.to.links) {
				q.enqueue(e);
			}
			current.to.visited = true;
		}
		prev = getNode(to);
		StringBuilder sb = new StringBuilder();
		sb.append(prev.distance);
		sb.append(" ");
		while (prev != null) {
			sb.append(prev.c);
			prev = prev.parent;
		}
		return sb.toString();
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private Node getNode(Character c) {
		if (!validChar(c)) return null;
		int index = Character.toLowerCase(c) + OFFSET;
		return nodes[index];
	}
	
	private boolean validChar(char c) {
		return ((c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z'));
	}
	
	private class Node implements Comparable<Node> {
		Character c;
		boolean visited;
		int distance;
		LinkedList<Edge> links;
		Node parent;
		
		public Node(Character c) {
			this.c = c;
			links = new LinkedList<Edge>();
			visited = false;
			distance = Integer.MAX_VALUE;
		}

		public void link(Node to, int cost) {
			Edge e = new Edge(this, to, cost);
			links.add(e);
		}

		@Override
		public int compareTo(Node n) {
			return (c.compareTo(n.c));
		}
		
		@Override
		public boolean equals(Object n) {
			if (n instanceof Node)
				return c.equals(((Node)n).c);
			else return false;
		}
		
		public void sortEdges() {
			Collections.sort(links);
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('(');
			sb.append(c);
			sb.append(':');
			Iterator<Edge> it = links.iterator();
			Edge e = it.next();
			if (e != null) sb.append(e.toString());
			while(it.hasNext()) {
				sb.append(',');
				e = it.next();
				sb.append(e.toString());
			}
			sb.append(')');
			return sb.toString();
		}
	}
	
	private class Edge implements Comparable<Edge> {
		Node from;
		Node to;
		int cost;
		
		public Edge(Node f, Node t, int cost) {
			this.from = f;
			this.to = t;
			this.cost = cost;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Edge) {
				Edge e = (Edge) o;
				return (this.to.equals(e.to)
						&& this.from.equals(e.from)
						&& this.cost == e.cost);
			}
			return false;
		}
		
		public String toString() {
			return cost + "->" + to.c;
		}

		@Override
		public int compareTo(Edge o) {
			return cost > o.cost ? 1 : (cost < o.cost ? -1 : 0);
		}
	}
}
