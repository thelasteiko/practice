package eiko.collections;

/**
 * Implementation of a linked list.
 * @author Melinda Robertson
 * @version 20160818
 * @param <T>
 */
public class EikoList<T extends Comparable<T>> {
	
	Node head;
	Node tail;
	int size;
	
	public EikoList() {
		this.head = null;
		this.tail = null;
		size = 0;
	}
	
	public int size() {return size;}
	
	public void add(T value) {
		if (head == null) {
			head = new Node(null, value, null);
			tail = head;
			size++;
		} else {
			tail = new Node(tail, value, null);
			size++;
		}
	}
	
	public void insertAt(int index, T value) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Node temp = getNodeAt(index);
		insertBefore(value, temp);
		size++;
	}
	public T removeFrom(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Node temp = getNodeAt(index);
		size--;
		return remove(temp).value;
	}
	public T get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return getNodeAt(index).value;
	}
	public T get(T value) {
		Node temp = head;
		while (temp != null && !temp.value.equals(value)) {
			temp = temp.next;
		}
		return temp.value;
	}
	public void insertSorted(T value) {
		Node temp = head;
		while (temp != null && temp.value.compareTo(value) < 0) {
			temp = temp.next;
		}
		if (temp == null) add(value);
		else insertBefore(value, temp);
	}
	
	private Node getNodeAt(int index) {
		Node temp = head;
		int i = 0;
		while (temp != null && i < index) {
			temp = temp.next;
			i++;
		}
		return temp;
	}
	
	private Node getNode(T value) {
		Node temp = head;
		while (temp != null) {
			if (temp.value.equals(value)) return temp;
			temp = temp.next;
		}
		return null;
	}
	
	private void insertBefore(T value, Node b) {
		new Node(b.prev, value, b);
	}
	private void insertAfter(T value, Node b) {
		new Node(b, value, b.next);
	}
	private Node remove(Node a) {
		if(a.prev != null) a.prev.next = a.next;
		if(a.next != null) a.next.prev = a.prev;
		a.prev = null;
		a.next = null;
		return a;
	}

	
	private Node removeBefore(Node a) {
		Node temp = a.prev;
		if (temp != null) {
			a.prev = temp.prev;
			if (a.prev != null) a.prev.next = a;
			size--;
		}
		return temp;
	}
	
	private Node removeAfter(Node a) {
		Node temp = a.next;
		if (temp != null) {
			a.next = temp.next;
			if (a.next != null) a.next.prev = a;
			size--;
		}
		return temp;
	}
	
	private class Node {
		Node next;
		Node prev;
		T value;
		
		public Node(Node prev, T value, Node next) {
			if (prev != null) prev.next = this;
			if (next != null) next.prev = this;
			this.next = next;
			this.prev = prev;
			this.value = value;
		}
	}

}
