package week1;

import common.Graph;

import java.util.Deque;
import java.util.LinkedList;

/*
Implement depth-first search in an undirected graph without using recursion.
*/
public class DFS {
    private boolean[] visited;
    private Deque<Integer> deque = new LinkedList<>();
    private final int StartPoint = 0;

    public DFS(Graph graph) {
        visited = new boolean[graph.V()];
        deque.addFirst(0);


        while (!deque.isEmpty()) {
            int currV = deque.removeLast();

            proccess(currV);

            Iterable<Integer> adj = graph.adj(currV);
            visited[currV] = true;

            for (Integer adjV : adj) {
                if (visited[adjV]) continue;
                deque.addFirst(adjV);
                visited[adjV] = true;
            }

        }
    }

    private void proccess(int currV) {
        System.out.println("Vertex: " + currV);
    }
}
