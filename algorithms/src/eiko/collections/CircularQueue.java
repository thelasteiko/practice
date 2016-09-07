package eiko.collections;

public class CircularQueue<E extends Comparable<? super E>>
	extends AbstractCircularArray<E> {
	
	@SuppressWarnings("unchecked")
	public CircularQueue() {
		elements = (E[]) new Comparable[START_SIZE];
		size = 0;
		start = 0;
		stop = 0;
	}
	
	public void enqueue(E element) {
		check_size(size+1);
		elements[stop] = element;
		stop = (stop+1) % elements.length;
		size++;
	}
	
	public E dequeue() {
		E element = elements[start];
		elements[start] = null;
		start = (start+1) % elements.length;
		size--;
		return element;
	}
}
