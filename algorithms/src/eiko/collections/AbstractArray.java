package eiko.collections;

import java.util.Arrays;

public abstract class AbstractArray<E extends Comparable<? super E>> {

	public static final int START_SIZE = 3;
	
	protected E[] elements;
	protected int size;
	
	abstract public void add(E element);
	
	public boolean isEmpty() {return size == 0;}
	protected void check_size(int n) {
		if (n > elements.length) {
			elements = Arrays.copyOf(elements, n+START_SIZE);
		}
	}
}
