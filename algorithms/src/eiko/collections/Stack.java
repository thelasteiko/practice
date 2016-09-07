package eiko.collections;

public class Stack<E extends Comparable<? super E>> 
	extends AbstractCircularArray<E>{
	
	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[]) new Comparable[START_SIZE];
		size = 0;
		start = 0;
		stop = 0;
	}

	public void push(E element) {
		check_size(size+1);
		elements[stop] = element;
		stop++;
		size++;
	}
	
	public E pop() {
		size--;
		return elements[stop--];
	}
}
