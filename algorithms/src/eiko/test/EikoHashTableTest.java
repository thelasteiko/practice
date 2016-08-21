package eiko.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import eiko.collections.EikoHashTable;
import eiko.error.HashException;

public class EikoHashTableTest {
	
	private EikoHashTable<String,String> test;
	
	private static final String test_key = "key";
	private static final String test_value = "value";

	@Before
	public void setUp() throws Exception {
		test = new EikoHashTable<String, String>();
	}

	@Test
	public void testPut() {
		try {
			test.put(test_key, test_value);
			test.put("Melinda", "Robertson");
			test.put("Dylan", "Cate");
			test.put("Joey", "Sim");
			test.put("Margaret", "Robertson");
			test.put("test1", "input1");
			test.put("test2", "input2");
			test.put("test3", "input3");
			test.put("test4", "input4");
			test.put("test5", "input5");
			test.put("test6", "input6");
		} catch (HashException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGet() {
		testPut();
		assertEquals(test_value, test.get(test_key));
		System.out.println(test.toString());
	}
	
	@Test
	public void testRemove() {
		testPut();
		String v;
		try {
			v = test.remove(test_key);
			assertEquals(test_value, v);
			assertNull(test.remove(test_key));
		} catch (HashException e) {
			fail(e.getMessage());
		}
		System.out.println(test.toString());
	}

}
