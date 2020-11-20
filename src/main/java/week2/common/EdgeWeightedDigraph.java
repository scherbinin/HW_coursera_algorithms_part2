package week2.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by scher on 14.11.2020.
 */
public class EdgeWeightedDigraph {
    private final int v;
    private int e;
    private final LinkedList<Edge>[] adj;

    public EdgeWeightedDigraph(int VertexValue) {
        this.v = VertexValue;
        this.adj = new LinkedList[VertexValue];

        for (int i = 0; i< this.v; i++)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(Edge edge) {
        if(edge.getFisrtVert() == edge.getSecondVert(edge.getFisrtVert()))
            return;

        adj[edge.getFisrtVert()].add(edge);
        e++;
    }

    public Iterable<Edge> edges() {
        Set<Edge> list = new HashSet<>();

        for(int vert = 0; vert < v; ++vert) {
            list.addAll(adj[vert]);
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
