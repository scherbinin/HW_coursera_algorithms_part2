package week1;

import week1.common.Graph;

import java.util.LinkedList;

public class PathSearch {
    public LinkedList<Integer> SearchByDFS(Graph g, int node) {
        LinkedList<Integer> res = new LinkedList<>();
        DeepFirstSearch dfs = new DeepFirstSearch(g);

        for (int vertex = 0; vertex < g.V(); vertex++) {

        }

        return res;
    }

    public LinkedList<Integer> SearchByBFS(Graph g, int node) {
        LinkedList<Integer> res = new LinkedList<>();
        BreadthFirstSearch bfs = new BreadthFirstSearch(g);



        return res;
    }
}
