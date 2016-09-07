package eiko.collections;

import java.util.Arrays;

public abstract class AbstractCircularArray<E extends Comparable<? super E>> {
	
	public static final int START_SIZE = 3;
	
	protected E[] elements;
	protected int size;
	protected int start;
	protected int stop;
	
	public boolean isEmpty() {return size == 0;}
	public int size() {return size;}
	
	protected void check_size(int n) {
		if (n > elements.length) {
			if (start < stop)
				elements = Arrays.copyOf(elements, n+START_SIZE);
			else {
				E[] temp = (E[]) new Comparable[n+START_SIZE];
				int count = 0;
				int i = start;
				while (count < size) {
					temp[count] = elements[i];
					i = (i+1) % elements.length;
					count++;
				}
				start = 0;
				stop = size;
				elements = temp;
			}
			
		}
	}

}
