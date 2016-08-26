package eiko.testable;

import eiko.drive.Util;

public class QuickSort extends AbstractRunnableTest {
	
	private Timer timer;
	private Integer[] data;
	
	public QuickSort() {
		timer = new Timer();
		data = null;
	}
	
	@Override
	public void run() {
		if (data == null || data.length <= 0) return;
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
	
	public static <T extends Comparable<? super T>> void sort(T[] data) {
		sort(data, 0, data.length-1);
	}
	
	public static <T extends Comparable<? super T>> void sort(T[] data, int a, int b) {
		if (a >= b) return;
		int mid = (a+b)/2;
		//preliminary swaps help a bit i guess
		if(data[mid].compareTo(data[a]) < 0)
			Util.swap(data, mid,a);
		if(data[b].compareTo(data[a]) < 0)
			Util.swap(data, a,b);
		if(data[b].compareTo(data[mid]) < 0)
			Util.swap(data, mid,b);
		Util.swap(data,mid,b-1);
		T pivot = data[b-1];
		
		//this is using Hoare's partition
		int i, j;
		for(i = a, j = b-1;;) {
			while(data[++i].compareTo(pivot) < 0); //go right until i >= pivot
			while(data[--j].compareTo(pivot) > 0); //go left until j <= pivot
			if(i >= j) break; //you went too far
			Util.swap(data, i, j); //swap i and j, now they are in the right place relative to the pivot
		}
		Util.swap(data, i, b-1);
		sort(data, a, i-1);
		sort(data, i+1, b);
	}	

}
