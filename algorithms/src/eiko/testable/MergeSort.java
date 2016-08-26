package eiko.testable;

import eiko.drive.Util;

public class MergeSort extends AbstractRunnableTest {
	
	private Timer timer;
	private Integer[] data;
	
	public MergeSort() {
		timer = new Timer();
		data = null;
	}
	
	@Override
	public void run() {
		if (data == null || data.length == 0) return;
		timer.start();
		sort(data);
		timer.stop();
		cb.test_end(data.length, timer);
	}

	@Override
	public void setData(String parsable) {
		if (parsable == null) {
			data = Util.createSortTestList(20, 2, 10);
		} else {
			data = Util.parsecsv(parsable);
		}
	}

	@Override
	public void reset() {
		timer.reset();
		data = null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> void sort(T[] data) {
		T[] temp = (T[]) new Comparable[data.length];
		sort(data, temp, 0, data.length-1);
	}
	
	public static <T extends Comparable<? super T>> void sort(
			T[] data, T[] temp, int left, int right) {
		if (left < right) {
			int center = (left+right) / 2;
			sort(data, temp, left, center);
			sort(data, temp, center+1, right);
			merge(data, temp, left, center+1, right);
		}
	}
	
	private static <T extends Comparable<? super T>> void merge(
			T[] data, T[] temp, int left, int right, int rightEnd) {
		int leftEnd = right-1;
		int t = left;
		int numElements = rightEnd-left+1;
		
		while(left <= leftEnd && right <= rightEnd) {
			if(data[left].compareTo(data[right]) <= 0)
				temp[t++] = data[left++];
			else
				temp[t++] = data[right++];
		}
		while(left <= leftEnd)
			temp[t++] = data[left++];
		while(right <= rightEnd)
			temp[t++] = data[right++];
		
		for(int i = 0; i < numElements; i++, rightEnd--)
			data[rightEnd] = temp[rightEnd];
	}
}
