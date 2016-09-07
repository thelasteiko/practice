package eiko.testable;

public class GraphDijkstra extends GraphBFS {

	@Override
	public void run() {
		if (graph.isEmpty() || to == '-' || from == '-')
			return;
		timer.start();
		dijkstra();
		timer.stop();
		cb.test_end(graph.size(), timer, result);
	}

	private void dijkstra() {
		result = graph.dijkstra(from, to);
	}
}
