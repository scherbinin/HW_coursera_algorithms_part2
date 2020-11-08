package week2;

import org.junit.Assert;
import org.junit.Test;
import week2.common.Edge;
import week2.common.EdgeWeightedGraph;

import java.util.List;

public class KraskulTest {
    @Test
    public void normal_KruskalTest() {
        Kraskul kraskul = new Kraskul();
        List<Edge> actual = kraskul.Kraskal(getGraph());

        Assert.assertArrayEquals(actual.toArray(), expected());
    }

    private EdgeWeightedGraph getGraph() {
        EdgeWeightedGraph edgeWeightedGraph = new EdgeWeightedGraph(8);
        edgeWeightedGraph.addEdge(new Edge(0,2, 0.26));
        edgeWeightedGraph.addEdge(new Edge(0,4, 0.38));
        edgeWeightedGraph.addEdge(new Edge(0,6, 0.58));
        edgeWeightedGraph.addEdge(new Edge(0,7, 0.16));
        edgeWeightedGraph.addEdge(new Edge(1,7, 0.19));
        edgeWeightedGraph.addEdge(new Edge(1,5, 0.32));
        edgeWeightedGraph.addEdge(new Edge(1,3, 0.29));
        edgeWeightedGraph.addEdge(new Edge(1,2, 0.36));
        edgeWeightedGraph.addEdge(new Edge(2,3, 0.17));
        edgeWeightedGraph.addEdge(new Edge(2,6, 0.4));
        edgeWeightedGraph.addEdge(new Edge(3,6, 0.52));
        edgeWeightedGraph.addEdge(new Edge(4,5, 0.35));
        edgeWeightedGraph.addEdge(new Edge(4,7, 0.38));
        edgeWeightedGraph.addEdge(new Edge(4,6, 0.93));
        edgeWeightedGraph.addEdge(new Edge(5,7, 0.28));

        return edgeWeightedGraph;
    }

    private Edge[] expected() {
        Edge[] res = new Edge[7];
        res[0] = new Edge(0,7,0.16);
        res[1] = new Edge(2,3,0.17);
        res[2] = new Edge(1,7,0.19);
        res[3] = new Edge(0,2,0.26);
        res[4] = new Edge(5,7,0.28);
        res[5] = new Edge(4,5,0.35);
        res[6] = new Edge(2,6,0.4);

        return res;
    }
}
