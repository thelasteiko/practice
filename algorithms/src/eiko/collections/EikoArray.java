package eiko.collections;

/**
 * It's an array; not guaranteed to keep ordering of elements.
 * @author Melinda Robertson
 * @version 20160818
 */
public class EikoArray<E extends Comparable<? super E>> extends
	AbstractArray<E>{
	
	@SuppressWarnings("unchecked")
	public EikoArray() {
		elements = (E[]) new Comparable[START_SIZE];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	/**
	 * Add a new element.
	 * @param element is the element to add.
	 */
	public void add(E element) {
		check_size(size+1);
		elements[size++] = element;
	}
	/**
	 * Removes the specified element from the array.
	 * @param element is the element to remove.
	 * @return if the item is found, returns the item removed.
	 * 			Otherwise returns null.
	 */
	public E remove(E element) {
		E temp = null;
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(element)) {
				temp = elements[i];
				fill(i);
			}
		}
		return temp;
	}
	/**
	 * Removes the element at the indicated index.
	 * @param index is the location of the element to remove.
	 * @return the removed element.
	 * @throws ArrayIndexOutOfBoundsException if the index is less than zero or
	 * 											greater than the size.
	 */
	public E remove(int index) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException();
		E temp = elements[index];
		fill(index);
		return temp;
	}
	/**
	 * Retrieves the element at the indicated index.
	 * @param index is the location of the element to retrieve.
	 * @return the element.
	 * @throws ArrayIndexOutOfBoundsException if the index is less than zero or
	 * 											greater than the size.
	 */
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException();
		return elements[index];
	}
	
	/**
	 * Small utility function that fills in an empty space when
	 * an element is removed.
	 * @param i is the index to fill.
	 */
	private void fill(int i) {
		elements[i] = elements[size-1];
		size--;
	}
}
