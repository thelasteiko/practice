package eiko.test;

import eiko.collections.CharacterGraph;

public class TestDijGraph {

	public static void main(String[] args) {
		CharacterGraph graph = new CharacterGraph();
		//7
		for (char c = 'a'; c <= 'g'; c++) {
			graph.add(c);
		}
		//a,b,c,d,e,f,g
		//a2b,a3c,b2d,c2d,b3e,e4f,d1f,e2g,f3g
		//a,g
		/*   b---e---g
		 *  / \   \ /
		 * a   \   f
		 *  \   \ /
		 *   c---d
		 */
		
		graph.link('a','b', 2, false);
		graph.link('a','c', 3, false);
		graph.link('b','d', 2, false);
		graph.link('c','d', 2, false);
		graph.link('b','e', 3, false);
		graph.link('e','f', 4, false);
		graph.link('d','f', 1, false);
		graph.link('e','g', 2, false);
		graph.link('f','g', 3, false);
		
		System.out.println(graph.dijkstra('a', 'g'));
	}
}
