package week1;

import common.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a digraph GG, design an efficient algorithm to find a directed cycle with the minimum number of edges
 * (or report that the graph is acyclic). The running time of your algorithm should be at most proportional
 * to V(E + V)V(E+V) and use space proportional to E + VE+V, where VV is the number of vertices and EE is the number of edges.
 */
public class DirectedCycleSearcher {
    private Graph g;
    private char[] vertexMaker;
    private int[] comeFrom; // memorizing the path
    private int cycleMin;
    private int cycledVertex;
    static char GO_FORWARD = '.';
    static char GO_BACK = 'X';

    public DirectedCycleSearcher(Graph g) {
        this.g = g;
        cycleMin = g.E() + 1;
        comeFrom = new int[g.V()];
        vertexMaker = new char[g.V()];
    }

    public void search(int vertex) { // We already went through this vertex ! It's a cycle
        vertexMaker[vertex] = GO_FORWARD;

        for (int v : g.adj(vertex)) {
            if (vertexMaker[v] == GO_FORWARD) {
                comeFrom[v] = vertex;
                int cycleLength = getCycleLength(v);
                if (cycleLength < cycleMin) {
                    cycleMin = cycleLength;
                    cycledVertex = v;
                }
            } else if (vertexMaker[v] != GO_BACK) {
                comeFrom[v] = vertex;
                search(v);
            }
        }
        vertexMaker[vertex] = GO_BACK;
    }

    public int getMinCycleLength() {
        return cycleMin > g.V() ? 0 : cycleMin;
    }

    public List<Integer> getMinCycle() {
        LinkedList<Integer> res = new LinkedList<>();

        if(cycleMin <= g.V()) {
            for (int vertex = comeFrom[cycledVertex]; cycledVertex != vertex; vertex = comeFrom[vertex])
                res.addFirst(vertex);
            res.add(cycledVertex);
        }

        return res;
    }

    private int getCycleLength(int vertex) {
        int v = vertex;
        int count = 0;

        while (comeFrom[v] != vertex) {
            count++;
            v = comeFrom[v];
        }

        return ++count;
    }
}
