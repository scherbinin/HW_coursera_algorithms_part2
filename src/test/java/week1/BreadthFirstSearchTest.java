package week1;

import common.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BreadthFirstSearchTest {
    private Graph connectedGraph;
    private Graph graphWhere2comp;
    private BreadthFirstSearch bfs;

    @Before
    public void setup() {
        connectedGraph = getConnectedGraph();
        graphWhere2comp = getGraphWhere2comp();
    }

    @Test
    public void BFS_ConnectedGraph_AllVertexMarked() {
        bfs = new BreadthFirstSearch(connectedGraph);
        bfs.bfs(0);

        Assert.assertTrue(bfs.isVisited(6));
    }

    @Test
    public void BFS_NotConnectedGraphWhere2Components_VertexFromAnotherCompNotFound() {
        bfs = new BreadthFirstSearch(graphWhere2comp);
        bfs.bfs(0);

        Assert.assertFalse(bfs.isVisited(6));
    }

    private Graph getConnectedGraph() {
        Graph g = new Graph(8, false);

        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,4);
        g.addEdge(1,5);
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,7);
        g.addEdge(4,6);
        g.addEdge(4,7);
        g.addEdge(5,7);
        //3 below just check, they are extra items
        g.addEdge(7,4);
        g.addEdge(7,5);
        g.addEdge(7,3);

        return g;
    }

    private Graph getGraphWhere2comp() {
        Graph g = new Graph(9, false);

        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(1,3);
        g.addEdge(1,4);
        g.addEdge(4,5);
        g.addEdge(6,8);
        g.addEdge(6,7);

        return g;
    }
}
