package week1;

import week1.common.Graph;

import java.util.Deque;
import java.util.LinkedList;

public class DeepFirstSearch {
    private boolean[] visited;
    private int[] comeFrom;
    private Graph g;

    public DeepFirstSearch(Graph g) {
        visited = new boolean[g.V()];
        comeFrom = new int[g.V()];
        this.g = g;
    }

    public void notRecurciveDfs(int startVert) {
        Deque<Integer> stack = new LinkedList<>();
        stack.addFirst(startVert);

        while(!stack.isEmpty()) {
            int currVertex = stack.getFirst();
            visited[currVertex] = true;

            for(int v : g.adj(currVertex)) {
                if(!visited[v]) {
                    stack.addFirst(v);
                    break;
                }
            }
            if(stack.getFirst() == currVertex)
                stack.removeFirst();
        }
    }

    public void dfs(int startVert) {
        visited[startVert] = true;
        for (int v : g.adj(startVert)) {
            if(!visited[v]) {
                comeFrom[v] = startVert;
                dfs(v);
            }
        }
    }

    public boolean isVisited(int currVert) {
        return visited[currVert];
    }
}
