package week2;

import org.junit.Test;
import org.junit.Assert;
import week2.common.Edge;
import week2.common.EdgeWeightedDigraph;

import java.util.Arrays;
import java.util.List;


/**
 * Don't support negative weights
 * Implemented with using Min Priority Queue to provide complexity E*log(V)
 */
public class DijkstraApiTest {
    private DijkstraApi testedObj;

    @Test
    public void testSimplePath() {
        testedObj = new DijkstraApi(getGraph(), 0);

        // Check all paths from 0 to all vertexes
        Assert.assertTrue(new Double(0).equals(testedObj.getPathLength(0)));
        Assert.assertTrue(new Double(5).equals(testedObj.getPathLength(1)));
        Assert.assertTrue(new Double(14).equals(testedObj.getPathLength(2)));
        Assert.assertTrue(new Double(17).equals(testedObj.getPathLength(3)));
        Assert.assertTrue(new Double(9).equals(testedObj.getPathLength(4)));
        Assert.assertTrue(new Double(13).equals(testedObj.getPathLength(5)));
        Assert.assertTrue(new Double(25).equals(testedObj.getPathLength(6)));
        Assert.assertTrue(new Double(8).equals(testedObj.getPathLength(7)));

        // Check path from 0 to 6
        List<Edge> path = testedObj.getPath(6);
        Assert.assertArrayEquals(path.toArray(), getExpectedShortestPathToVertSix().toArray());
    }

    private EdgeWeightedDigraph getGraph() {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(8);

        graph.addEdge(new Edge(0, 1, 5));
        graph.addEdge(new Edge(0, 4, 9));
        graph.addEdge(new Edge(0, 7, 8));

        graph.addEdge(new Edge(1, 2, 12));
        graph.addEdge(new Edge(1, 3, 15));
        graph.addEdge(new Edge(1, 7, 4));

        graph.addEdge(new Edge(2, 3, 3));
        graph.addEdge(new Edge(2, 6, 11));

        graph.addEdge(new Edge(3, 6, 9));

        graph.addEdge(new Edge(4, 5, 4));
        graph.addEdge(new Edge(4, 6, 20));
        graph.addEdge(new Edge(4, 7, 5));

        graph.addEdge(new Edge(5, 2, 1));
        graph.addEdge(new Edge(5, 6, 13));

        graph.addEdge(new Edge(7, 2, 7));
        graph.addEdge(new Edge(7, 5, 6));

        return graph;
    }

    private List<Edge> getExpectedShortestPathToVertSix() {
        return Arrays.asList(new Edge(0, 4, 9),
                new Edge(4, 5, 4),
                new Edge(5, 2, 1),
                new Edge(2, 6, 11));
    }
}
