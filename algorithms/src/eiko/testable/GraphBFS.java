/**
 * 
 */
package eiko.testable;

import eiko.collections.CharacterGraph;

/**
 * @author Melinda Robertson
 * @version 20160904
 */
public class GraphBFS extends AbstractRunnableTest {
	
	protected CharacterGraph graph;
	protected char from;
	protected char to;
	protected String result;
	
	public GraphBFS() {
		reset();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (graph.isEmpty() || from == '-' || to == '-')
			return;
		timer.start();
		bfs();
		timer.stop();
		cb.test_end(graph.size(), timer, result);
	}

	/* (non-Javadoc)
	 * @see eiko.testable.AbstractRunnableTest#setData(java.lang.String)
	 */
	@Override
	public void setData(String parsable) {
		//one line with letters, another with pairs a<3b,b2>c,d9>e
		//< bidirectional, > unidirectional
		int i1 = parsable.indexOf('\n');
		if (i1 > 0) {
			String line = parsable.substring(0, i1);
			String[] a = line.split(",");
			for (int i = 0; i < a.length; i++) {
				graph.add(a[i].charAt(0));
			}
			int i2 = parsable.indexOf('\n', i1+1);
			line = parsable.substring(i1+1, i2);
			a = line.split(",");
			i1 = i2;
			//parameters of each pair
			//begins and ends with one alpha character
			//if ch[1] == '<', it's bi
			for (int i = 0; i < a.length; i++) {
				i2 = a[i].indexOf('<');
				char c1 = a[i].charAt(0);
				char c2 = a[i].charAt(a[i].length()-1);
				int cost = 0;
				if (i2 > 0) {
					cost = Integer.parseInt(
							a[i].substring(i2+1, a[i].length()-1));
					graph.link(c1, c2, cost, true);
				} else {
					i2 = a[i].indexOf('>');
					if (i2 > 0) {
						cost = Integer.parseInt(a[i].substring(1, i2));
						graph.link(c1, c2, cost, false);
					}
				}
			}
			i1 = parsable.indexOf('\n', i1+1);
			//from,to
			if (i1 > 0) {
				line = parsable.substring(i1+1);
				a = line.split(",");
				from = a[0].charAt(0);
				to = a[1].charAt(0);
			}
		}
	}

	/* (non-Javadoc)
	 * @see eiko.testable.AbstractRunnableTest#reset()
	 */
	@Override
	public void reset() {
		graph = new CharacterGraph();
		from = '-';
		to = '-';
	}
	
	private void bfs() {
		result = graph.bfs(from, to);
	}

}
