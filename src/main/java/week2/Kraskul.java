package week2;

import week2.common.Edge;
import week2.common.EdgeWeightedGraph;
import java.util.LinkedList;
import java.util.List;

public class Kraskul {
    public List<Edge> Kraskal(EdgeWeightedGraph graph) {
        UnionFind unionFind = new UnionFind(graph.V());
        List<Edge> edges = new LinkedList<>();
        List<Edge> res = new LinkedList<>();

        for(Edge edge : graph.edges())
            edges.add(edge);
        edges.sort(Edge::compareTo);

        for ( Edge edge : edges){
            int v = edge.getFisrtVert();

            if(!unionFind.isConnected(v,edge.getSecondVert(v))) {
                unionFind.union(v,edge.getSecondVert(v));
                res.add(edge);
            }

            if(res.size() == graph.V()-1)
                break;
        }

        return res;
    }

    // Very Simple Union-Find structure
    private class UnionFind  {
        int[] arr;
        public UnionFind(int size) {
            arr = new int[size];

            for(int i=0; i<size; i++) {
                arr[i] = i;
            }
        }

        public void union(int v, int w) {
            int rootW = arr[w]; // Which number of set has W now
            int newRoot = arr[v]; // Which new number of set will get W

            for(int i=0; i<arr.length; i++) {
                if(arr[i] == rootW)
                    arr[i] = newRoot;
            }
        }

        public boolean isConnected(int v, int w) {
            return arr[v] == arr[w];
        }
    }
}
