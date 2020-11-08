package week2;

import week2.common.Edge;
import week2.common.EdgeWeightedGraph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {
    public List<Edge> prim(EdgeWeightedGraph graph) {
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();
        List<Edge> result = new LinkedList<>();
        boolean[] visited = new boolean[graph.V()];

        // Start growing tree from vertex == 0;
        edgesPQ.addAll(graph.adj(0));
        visited[0] = true;

        while (!edgesPQ.isEmpty() && result.size() < graph.V()) {
            Edge edge = edgesPQ.remove();
            int v = edge.getFisrtVert();
            int w = edge.getSecondVert(v);

            if (visited[v] && visited[w]) {
                continue;// That edge is creating a loop
            } else {
                result.add(edge);
                if (visited[v]) {
                    visited[w] = true;
                    for (Edge e : graph.adj(w)) {
                        if(!visited[e.getSecondVert(w)])
                            edgesPQ.add(e);
                    }
                } else {
                    visited[v] = true;
                    for (Edge e : graph.adj(v)) {
                        if(!visited[e.getSecondVert(v)])
                            edgesPQ.add(e);
                    }
                }
            }
        }
        return result;
    }
}
