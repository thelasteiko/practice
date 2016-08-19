package eiko.dynamic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import eiko.drive.Util;

/**
 * My attempt to recreate the knapsack problem using 0-1, limited quantity and unlimited
 * restrictions.
 * @author Melinda Robertson
 * @version 20160818
 */
public class Knapsack {
	
	private int limit;
	private ArrayList<Item> source;
	private ArrayList<Item> solution;
	
	public Knapsack () {
		limit = 0;
		source = new ArrayList<Item>();
		solution = new ArrayList<Item>();
	}
	
	/**
	 * Solution for knapsack problem that restricts items to zero or one each.
	 */
	public void zero_one() {
		solution.clear();
		Matrix temp = new Matrix(source.size(), (int) limit+1);
		for (int i = 0; i < source.size(); i++) {
			Item current = source.get(i);
			for (int j = 1; j < limit; j++) {
				Set t = temp.get(i-1,j);
				//keep the previous solution since this item can't be added
				if (current.weight > j) temp.set(i, j, t);
				else {
					//find the best between the previous optimal solution that accounts for the current weight
					//and a new solution that adds this item's weight while reducing the current solution's value
					// to add the current item's value
					int j2 = j-(int)(current.weight+0.5);
					Set t2 = temp.get(i-1, j2);
					Set u = new Set(i, j, (t2 == null ? 0 : t2.value) + current.value, current);
					//if the previous solution was chosen, do nothing
					if (t.value >= u.value) temp.set(i, j, t);
					//if this solution is chosen, link the new set into the matrix
					else temp.link(u, i-1, j2);
				}
			}
		}
		solution = temp.getSolution(source.size()-1, (int) limit-1);
	}
	
	public void zero_one2() {
		solution.clear();
		int[][] A = new int[source.size()+1][(int) limit+1];
		//if the limit is zero, cannot add any items.
		for(int i = 0; i < source.size()+1; i++) A[i][0] = 0;
		for (int j = 0; j < limit+1; j++) A[0][j] = 0;
		for (int i = 1; i < source.size()+1; i++) {
			Item current = source.get(i-1);
			for (int j = 1; j < limit+1; j++) {
				int t = A[i-1][j];
				//keep the previous solution since this item can't be added
				if ((current.weight+0.5) > j) A[i][j] = t;
				else {
					//find the best between the previous optimal solution that accounts for the current weight
					//and a new solution that adds this item's weight
					int j2 = j-(int)(current.weight+0.5);
					int t2 = A[i-1][j2] + current.value;
					//A[i][j] = Util.max(A[i-1][j], t2);
					if (t >= t2) A[i][j] = t;
					//if the previous solution was chosen, do nothing
					else A[i][j] = t2;
				}
			}
		}
		int j = limit;
		//follow back through the matrix to find the items selected
		for (int i = source.size(); i >= 1; i--) {
			Item current = source.get(i - 1);
			int j2 = j - (int) (current.weight); // location
			if (j2 < 0)
				break;
			int val = A[i - 1][j2]; // value
			if (A[i][j] == val + current.value) {
				solution.add(current);
				j = j2;
			}
		}
		//System.out.println(Util.matrix_toString(A));
	}
	
	public void reset() {
		source.clear();
		solution.clear();
		limit = 0;
	}
	public void manual_load(int limit, int[] weight, int[] value, int[] quantity) {
		reset();
		this.limit = limit;
		for (int i = 0; i < weight.length; i++) {
			source.add(new Item("item" + i, weight[i],value[i],quantity[i]));
		}
	}
	public void read(String filename) {
		reset();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				//TODO determine what format Imma use.
				//first int should have limit of knapsack
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Knapsack: " + this.limit + "\n");
		sb.append("Source:\n");
		sb.append(list_toString(source));
		sb.append("Solution:\n");
		sb.append(list_toString(solution));
		return sb.toString();
	}
	public String list_toString(ArrayList<Item> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for(Item i: list) {
			sb.append("\t" + i.name + "\n");
			sb.append("\t" + i.weight + "\n");
			sb.append("\t" + i.value + "\n");
			sb.append("\t" + i.quantity + "\n\n");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private class Item {
		String name;
		int weight;
		int value;
		int quantity;
		public Item(String name, int weight, int value, int quantity) {
			super();
			this.name = name;
			this.weight = weight;
			this.value = value;
			this.quantity = quantity;
		}
	}
	
	/**
	 * Constructs a 3D matrix where each 'box' is a linked listing of items that
	 * have been chosen. Following the solution Set at each i,j coordinate should
	 * give the list of items that were a solution for that 'box'.
	 * @author Melinda Robertson
	 *
	 */
	private class Matrix {
		Set[][] m;
		int i, j; //prolly don't need these
		public Matrix(int num_items, int limit) {
			m = new Set[num_items][limit];
			for(int i = 0; i < m[0].length; i++) m[0][i] = null;
			this.i = 0;
			this.j = 1;
		}
		public void set(int i, int j, int v, Item c) {
			m[i][j] = new Set(i, j, v, c);
			this.i = i;
			this.j = j;
		}
		public void set(int i, int j, Set s) {
			m[i][j] = s;
			this.i = i;
			this.j = j;
		}
		public Set get(int i, int j) {
			if(i < 0 || j < 0) return null;
			this.i = i;
			this.j = j;
			return m[i][j];
		}
		public Set get() {
			return m[i][j];
		}
		/**
		 * Link an item into the matrix using the coordinates of the last optimal
		 * solution that accommodates the current item.
		 * @param current is the Set object that holds the current item.
		 * @param i is the row of the last solution.
		 * @param j is the column of the last solution.
		 */
		public void link(Set current, int i, int j) {
			current.prev = m[i][j];
			this.i = current.i;
			this.j = current.j;
		}
		/**
		 * Returns the solution of the current coordinates.
		 * @return an array list of the optimal solution for the current i,j coordinates.
		 */
		public ArrayList<Item> getSolution(int i, int j) {
			ArrayList<Item> solution = new ArrayList<Item>();
			Set temp = m[i][j];
			while(temp != null) {
				solution.add(temp.current);
				temp = temp.prev;
			}
			return solution;
		}
	}

	private class Set {
		//at limit zero, there are zero solutions
		//greater than that, there could be multiple solutions
		//the limit on the matrix should be 1 greater than the limit
		//this means that the solution needs to print out the actual limit
		
		//concept is that the greater the limit, the more items can be stored
		
		int i, j;
		int value;
		Set prev; //this is setting up a linked list...which is fine
		Item current;
		//set all the things before sending to matrix
		public Set(int i, int j, int value, Item current) {
			super();
			this.i = i;
			this.j = j;
			this.value = value;
			this.current = current;
		}
	}
}
