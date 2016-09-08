package eiko.collections;

/**
 * A min heap indexed at 0.
 * @author Melinda Robertson
 * @version 20160907
 * @param <E> is some comparable object.
 */
public class MinHeap<E extends Comparable<? super E>> extends
	AbstractArray<E> {

	@SuppressWarnings("unchecked")
	public MinHeap() {
		elements = (E[]) new Comparable[START_SIZE];
		size = 0;
	}
	@Override
	public void add(E element) {
		if (element == null) return;
		check_size(size+1);
		//i want to add to size then bubble it up
		elements[size] = element;
		rise(size);
		size++;
	}
	/**
	 * Removes the minimum element.
	 * @return the minimum element.
	 */
	public E remove() {
		E element = elements[0];
		elements[0] = elements[size-1];
		elements[size-1] = null;
		size--;
		fall(0);
		return element;
	}
	/**
	 * Returns but does not remove the minimum element.
	 * @return the minimum element.
	 */
	public E peek() {
		return elements[0];
	}
	
	public int size(){return size;}
	/**
	 * Moves the element at index i up until its parent is less than it.
	 * @param i is the index.
	 */
	private void rise(int i) {
		int pi = parent(i);
		if (pi < 0) return;
		E p = elements[pi];
		E c = elements[i];
		if (p.compareTo(c) >= 0) {
			swap(pi, i);
			rise(pi);
		}
	}
	/**
	 * Moves the element at index i down until its children are both more than it.
	 * @param i is the index.
	 */
	private void fall(int i) {
		int ri = right(i);
		int li = left(i);
		if (ri >= size && li >= size) return;
		int c = -1;
		if (li < size) {
			c = li;
			if (ri < size) {
				if (elements[c].compareTo(elements[ri]) >= 0)
					c = ri;
			}
		} else if (ri < size) {
			c = ri;
		} else return;
		if (c >= 0) {
			if (elements[c].compareTo(elements[i]) <= 0) {
				swap(c, i);
				fall(c);
			}
		}
	}
	/**
	 * Gets the index of the parent.
	 * @param index is the child index.
	 * @return the parent index.
	 */
	private int parent(int index) {
		if (index == 0) return -1;
		return (index-1)/2;
	}
	/**
	 * Gets the index of the right child.
	 * @param index is the parent index.
	 * @return the right child index.
	 */
	private int right(int index) {
		return (2*index+2);
	}
	/**
	 * Gets the index of the left child.
	 * @param index is the parent index.
	 * @return the left child index.
	 */
	private int left(int index) {
		return (2*index+1);
	}
	/**
	 * Swaps the positions of two elements in the heap.
	 * @param m is the index of the first element.
	 * @param n is the index of the second element.
	 */
	private void swap(int m, int n) {
		E temp = elements[m];
		elements[m] = elements[n];
		elements[n] = temp;
	}
	

}
