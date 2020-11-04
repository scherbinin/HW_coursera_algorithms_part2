package common;

import java.util.LinkedList;

/*
Simple Unweighted Graph
 */
public class Graph {
    private final int v;
    private final boolean isDirected;
    private int e;
    private final LinkedList<Integer>[] G;

    public Graph(int VertexValue, boolean isDirected) {
        this.v = VertexValue;
        this.isDirected = isDirected;
        this.G = new LinkedList[VertexValue];

        for (int i = 0; i< this.v; i++)
            G[i] = new LinkedList<>();
    }

    public void addEdge(int v1, int v2) {
        G[v1].add(v2);

        if(!isDirected)
            G[v2].add(v1);

        e++;
    }

    public Iterable<Integer> adj(int v) {
        return G[v];
    }

    public int V() {
        return v;
    }

    public int E() {
        return e;
    }
}
