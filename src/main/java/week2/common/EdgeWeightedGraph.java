package week2.common;

import java.util.*;

public class EdgeWeightedGraph {
    private final int v;
    private int e;
    private final LinkedList<Edge>[] adj;

    public EdgeWeightedGraph(int VertexValue) {
        this.v = VertexValue;
        this.adj = new LinkedList[VertexValue];

        for (int i = 0; i< this.v; i++)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(Edge edge) {
        adj[edge.getFisrtVert()].add(edge);
        adj[edge.getSecondVert(edge.getFisrtVert())].add(edge);
        e++;
    }

    public Iterable<Edge> edges() {
        Set<Edge> list = new HashSet<>();

        for(int vert = 0; vert < v; ++vert) {
            int selfLoops = 0;
            for (Edge e : adj[vert]) {
                if (e.getSecondVert(vert) > vert) {
                    list.add(e);
                } else if (e.getSecondVert(vert) == vert) {
                    if (selfLoops % 2 == 0) {
                        list.add(e);
                    }
                }
            }
        }

        return list;
    }

    public Collection<Edge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return v;
    }

    public int E() {
        return e;
    }
}
