package eiko.collections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A program to create a list of employees and their friendships
 * and print out an adjacency list of all of them.
 * 
 * Things I would do different next time:
 * 	- Probably wouldn't use a hash map. I thought it would be fast for looking up the employees,
 * 		which it is but a TreeMap may have been a better solution because of insertion time and
 * 		memory usage (O(log n) and O(n) vs. O(n) and O(M) for the hash map).
 * 		Or at least I'd try it cause it sounds interesting.
 * 
 * @author Melinda Robertson
 * @version 20160831
 */
public class EmployeeFriends {

	//For an adjacency graph we need an array of lists
	//we don't know how many employees there are
	//so we need to make the array resizable
	//I should also make the employees indexed according to
	//their id to make it easier to look them up. In which
	//case a hash map would probably be better.
	
	//as for the linked lists, we just need to be able to insert
	//at this point, not need to account for removal because it
	//isn't part of the problem

	/**
	 * Runs the main program. Reads in two csv files, parses the data
	 * and spits out a list of friends for employees.
	 * 
	 * Overall complexity:
	 *  n is the number of employees.
	 *  m is the number of friendships.
	 *  --> O(n+m)
	 * @param args are the args.
	 */
	public static void main(String[] args) {
		EmployeeFriends employees = parseEmployee("resource/employees.csv"); //O(n)
		parseFriendList(employees, "resource/friendships.csv"); //O(m)
		System.out.println(employees.toString()); //O(n^2) but if I know m, then O(n+m)
	}
	
	/**
	 * This parses the employee data by iterating through each line in the
	 * file.
	 * If there are n lines then the runtime is O(n) because each line has
	 * to be read.
	 * @param filename is the filename for employee information.
	 * @return a list (hash map) of employees.
	 */
	public static EmployeeFriends parseEmployee(String filename) {
		EmployeeFriends employees = new EmployeeFriends();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine(); //consume headers
			while ((line = br.readLine()) != null) {
				employees.add(employees.new Employee(line));
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file! Uh-oh.");
		} catch (IOException e) {
			System.out.println("I wish I knew what happened.");
		}
		return employees;
	}
	/**
	 * Parses the friends list and links each pair together.
	 * Also O(n).
	 * @param employees are the list of employees read in previously.
	 * @param filename is the filename for the friends list.
	 */
	public static void parseFriendList(EmployeeFriends employees, String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine(); //consume headers
			while ((line = br.readLine()) != null) {
				int i = line.indexOf(',');
				if (i > 0)
					employees.befriend(
						Integer.parseInt(line.substring(0, i)),
						Integer.parseInt(line.substring(i+1)));
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file! Uh-oh.");
		} catch (IOException e) {
			System.out.println("I wish I knew what happened.");
		}
	}
	/**
	 * Load capacity of the hash map.
	 */
	public static final float LOAD_FACTOR = 0.5f;
	/**
	 * The initial capacity. (16)
	 */
	public static final int INITIAL_CAPACITY = 1 << 4;
	/**
	 * List of employees.
	 */
	Employee[] elements;
	/**
	 * Current capacity used for increasing the size and rehashing.
	 */
	int M;
	/**
	 * The number of employees in the hash map.
	 */
	int size;
	/**
	 * Why use a hashmap? I thought it would be quicker to look
	 * the employees up when trying to add to their list of friends.
	 * In retrospect I'm not sure I would have.
	 * 
	 * This creates the new hash map of employees. It uses quadratic
	 * collision resolution.
	 */
	public EmployeeFriends() {
		elements = new Employee[INITIAL_CAPACITY];
		size = 0;
		M = INITIAL_CAPACITY;
	}
	/**
	 * Adds an employee to the list.
	 * Adding a new employee means that the list could need to grow.
	 * In this case, 2M space is required and O(n) time to transfer
	 * all the elements.
	 * @param e is a new employee.
	 */
	public void add(Employee e) {
		if (!check_size(size+1)) return;
		int h = hash(e);
		int j = 0;
		while (elements[h] != null && j < size) {
			j++;
			h = (h+j*j) % (M-1);
		}
		if(elements[h] != null) return;
		elements[h] = e;
		size++;
	}
	/**
	 * 'Befriends' two employees by adding the id's of each
	 * employee to the other's friend list.
	 * Finding the employees should be constant O(1).
	 * Linking them depends on how big each employee's friend list is.
	 * If	k = e1's friend list
	 * 		t = e2's friend list
	 * then linking them should take about O(k+t) time.
	 * @param e1 is the id of employee #1.
	 * @param e2 is the id of employee #2.
	 */
	public void befriend(int e1, int e2) {
		try {
			Employee emp1 = elements[getIndex(e1)];
			Employee emp2 = elements[getIndex(e2)];
			emp1.link(emp2);
			emp2.link(emp1);
		} catch (IndexOutOfBoundsException e) {
			return; //fail
		}
	}
	/**
	 * Converts the information in the employee list
	 * to a string where each employee is in the form of:
	 * Employee_id: Friend1, Friend2
	 * 
	 * Ok! So time complexity here depends on how many employees (n)
	 * and how many friends each employee has (e1 + e2 + ... + en).
	 * Each employee cannot have more than n-1 friends. So if each
	 * employee had the maximum amount of friends...that's n(n-1)
	 * so O(n^2)
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null) continue;
			sb.append(elements[i].toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	/**
	 * Finds the index of an employee.
	 * Finding an employee in a hash map has the run time of O(1).
	 * @param e is the employee id.
	 * @return an index.
	 */
	private int getIndex(int e) {
		int h = hash(e);
		int j = 0;
		while(j < size) {
			if(elements[h] != null &&
					elements[h].id == e) 
				return h;
			j++;
			h = (h+j*j) % M;
		}
		return -1;
	}
	/**
	 * Computes an index for an employee. Essentially
	 * just uses their id and modulus (M-1).
	 * @param e is the employee.
	 * @return an index.
	 */
	private int hash(Employee e) {
		return hash(e.hashCode());
	}
	/**
	 * Gets the index for an employee id.
	 * @param e is the employee id.
	 * @return an index.
	 */
	private int hash(int e) {
		return (e) % (M-1);
	}
	/**
	 * Makes sure that the size of the hash map is big enough
	 * for a new entry. If it isn't it creates a new array with
	 * double the capacity and rehashes the values in it.
	 * @param new_size is the new size of the array.
	 * @return true if it's safe to add new elements,
	 * 			false otherwise.
	 */
	private boolean check_size(int new_size) {
		if (((float)new_size / (float)elements.length) > LOAD_FACTOR) {
			M <<= 1;
			Employee[] temp = rehash();
			if (temp != null)
				elements = temp;
			else return false;
		}
		return true;
	}
	/**
	 * Creates a new hash that is M size and transfers all
	 * elements to the new hash map.
	 * Creating a new hash map takes twice as much space as the original.
	 * To transfer all the elements takes O(n) time.
	 * @return a new array of employees.
	 */
	private Employee[] rehash() {
		Employee[] t = new Employee[M];
		for(int i = 0, j = 0; i < elements.length; i++, j = 0) {
			if (elements[i] == null) continue;
			int h = hash(elements[i]);
			while (t[h] != null && j < size) {
				j++;
				h = (h+j*j) % M;
			}
			if (j >= size) return null;
			t[h] = elements[i];
		}
		return t;
	}	

	/**
	 * An employee class that holds information about the
	 * employee and also a list of friends in the form of id's.
	 * @author Melinda Robertson
	 * @version 20160831
	 */
	private class Employee {
		/**Probably more important than other things.**/
		int id;
		String name; //I have these but I don't care about them
		String department;
		IntNode friends; //it's like a linked list, a simple linked list
		/**
		 * Creates a new employee using a csv line.
		 * employee_id,name,department
		 * @param line is the line.
		 */
		public Employee(String line) {
			int i = line.indexOf(',');
			if(i > 0) {
				id = Integer.parseInt(line.substring(0, i));
				int i1 = line.indexOf(',',i+1);
				if (i1 > 0) {
					name = line.substring(i+1,i1);
					department = line.substring(i1+1);
				}
			} else {
				
			}
			friends = null;
		}
		/**
		 * So the main program finds the friend in the
		 * data structure and asks this one to link to it.
		 * The main program will call link on both employees
		 * so that they end up in each other's friend list.
		 * @param friend is the friend.
		 */
		public void link(Employee f) {
			if (friends == null) {
				friends = new IntNode(null, f.id);
			} else {
				IntNode temp = friends;
				while (temp.next != null) {
					temp = temp.next;
				}
				temp.next = new IntNode(temp, f.id);
			}
		}
		@Override
		public int hashCode() {
			return (int) id;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(id);
			sb.append(": ");
			IntNode temp = friends;
			if (temp == null) sb.append("None");
			while (temp != null) {
				sb.append(temp.value);
				if (temp.next != null)
					sb.append(", ");
				temp = temp.next;
			}
			return sb.toString();
		}
	}
	/**
	 * A utility class for keeping track of friends of employees.
	 * It's meant to hold employee id's.
	 * @author Melinda Robertson
	 * @version 20160831
	 */
	private class IntNode {
		/**Value is the employee id.**/
		int value;
		/**Next is the next friend's id, or null if there are no more.**/
		IntNode next;
		/**
		 * Creates a new node.
		 * @param prev is the previous node or null.
		 * @param value is the value.
		 */
		public IntNode(IntNode prev, int value) {
			if (prev != null) prev.next = this;
			this.value = value;
			next = null;
		}
	}
}
