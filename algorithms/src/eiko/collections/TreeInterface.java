/**
 * 
 */
package eiko.collections;

/**
 * @author Melinda Robertson
 * @version 20160827
 */
public interface TreeInterface<K extends Comparable<? super K>, V> {
	public String in_order();
	public String pre_order();
	public String post_order();
	public void add();
	public V get(K key);
	public V remove(K key);
	public void addAll(V[] list);
	public int size();
	public void clear();
	public boolean isEmpty();
}
