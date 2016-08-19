package eiko.collections;

import java.util.Comparator;

public interface EikoCollection<E> {

	public boolean add(E element);
	public E remove(E element);
	public E remove(int index);
	public E get(int index);
	public E sort(Comparator<E> c);
}
