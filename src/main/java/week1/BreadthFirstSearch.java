package week1;

import common.Graph;

import java.util.Deque;
import java.util.LinkedList;

public class BreadthFirstSearch {
    private boolean[] visited;
    private int[] comeFrom;
    private Graph g;

    public BreadthFirstSearch(Graph g) {
        visited = new boolean[g.V()];
        comeFrom = new int[g.V()];
        this.g = g;
    }

    public void bfs(int vertex) {
        int initialVertex = 0;
        Deque<Integer> queue = new LinkedList<>();
        queue.addFirst(initialVertex);

        while(!queue.isEmpty()) {
            int curr = queue.removeLast();
            visited[curr] = true;

            for (int v : g.adj(curr)) {
                if(!visited[v]) {
                    queue.addFirst(v);
                    comeFrom[v] = curr;
                }
            }
        }
    }

    public boolean isVisited(int currVert) {
        return visited[currVert];
    }
}
