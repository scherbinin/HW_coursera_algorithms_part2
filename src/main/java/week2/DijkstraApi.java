package week2;

import week2.common.Edge;
import week2.common.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by scher on 14.11.2020.
 */
public class DijkstraApi {
    private final double[] distTo;
    private final Edge[] comeFrom;
    private final int sourceVertex;

    public DijkstraApi(EdgeWeightedDigraph graph, int sourceVertex) {
        this.sourceVertex = sourceVertex;
        this.distTo = new double[graph.V()];
        this.comeFrom = new Edge[graph.V()];

        PriorityQueue<VertexDescriptor> vertexWithWeightPQ = new PriorityQueue<>();

        for (int i = 0; i < graph.V(); i++)
            distTo[i] = Double.MAX_VALUE;

        distTo[sourceVertex] = 0;
        vertexWithWeightPQ.add(new VertexDescriptor(sourceVertex, 0));

        while(!vertexWithWeightPQ.isEmpty()) {
            VertexDescriptor currVertexDescriptor = vertexWithWeightPQ.poll();

            for (Edge edge : graph.adj(currVertexDescriptor.vertex)) {
                int secondVert = edge.getSecondVert(currVertexDescriptor.vertex);

                if (distTo[secondVert] >= distTo[currVertexDescriptor.vertex] + edge.getWeight()) {
                    distTo[secondVert] = distTo[currVertexDescriptor.vertex] + edge.getWeight();
                    // If we changed the weight and current vertex already exists, we have to re-weight such vertex in the Priority Queue.
                    // The simplest way is just delete and add vertex again, in our case - just replace (hash code will be the same)
                    vertexWithWeightPQ.add(new VertexDescriptor(secondVert, distTo[secondVert]));
                    comeFrom[secondVert] = edge;
                }
            }
        }
    }

    public double getPathLength(int vertex) {
        return distTo[vertex];
    }

    public List<Edge> getPath(int destVertex) {
        List<Edge> res = new LinkedList<>();
        Deque<Edge> path = new LinkedList<>();
        Edge currEdge = comeFrom[destVertex];

        for (;currEdge.getFisrtVert() != sourceVertex; currEdge = comeFrom[currEdge.getFisrtVert()]) {
            path.addFirst(currEdge);
        }
        path.addFirst(currEdge);// add last edge ( from source to the second vertex)

        // Revert path backwards, to start from the source vertex
        res.addAll(path);

        return res;
    }

    private class VertexDescriptor implements Comparable {
        public final int vertex;
        public final double weight;

        public VertexDescriptor(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Object o) {
            if (this.weight < ((VertexDescriptor) o).weight) return -1;
            else if (this.weight > ((VertexDescriptor) o).weight) return 1;
            else return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (VertexDescriptor.class.equals(o.getClass())) {
                VertexDescriptor externalObj = (VertexDescriptor) o;
                if (Objects.equals(this.vertex, externalObj.vertex) && Objects.equals(this.weight, externalObj.weight))
                    return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex, weight);
        }
    }
}
