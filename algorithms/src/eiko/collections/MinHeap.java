package eiko.collections;

public class MinHeap<E extends Comparable<? super E>> extends
	AbstractArray<E> {

	//indexed at 0
	@SuppressWarnings("unchecked")
	public MinHeap() {
		elements = (E[]) new Comparable[START_SIZE];
		size = 0;
	}
	@Override
	public void add(E element) {
		check_size(size+1);
		//TODO
	}
	
	private int parent(int index) {
		return (index-1)/2;
	}
	private int right(int index) {
		return (2*index+2);
	}
	private int left(int index) {
		return (2*index+1);
	}
	

}
