package week1;

import common.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeepFirstSearchTest {
    private Graph connectedGraph;
    private Graph graphWhere2comp;
    private DeepFirstSearch dfs;

    @Before
    public void setup() {
        connectedGraph = getConnectedGraph();
        graphWhere2comp = getGraphWhere2comp();
    }

    @Test
    public void DFS_ConnectedGraph_AllVertexMarked() {
        dfs = new DeepFirstSearch(connectedGraph);
        dfs.dfs(0);

        Assert.assertTrue(dfs.isVisited(6));

        dfs = new DeepFirstSearch(connectedGraph);
        dfs.notRecurciveDfs(0);

        Assert.assertTrue(dfs.isVisited(6));
    }

    @Test
    public void DFS_NotConnectedGraphWhere2Components_VertexFromAnotherCompNotFound() {
        dfs = new DeepFirstSearch(graphWhere2comp);
        dfs.dfs(0);

        Assert.assertFalse(dfs.isVisited(6));

        dfs = new DeepFirstSearch(graphWhere2comp);
        dfs.notRecurciveDfs(0);

        Assert.assertFalse(dfs.isVisited(6));
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
