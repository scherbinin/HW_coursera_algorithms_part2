package week1.task;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by scher on 01.11.2020.
 */
public class SAP {
    private final Digraph g;
    private int minPath;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        this.g = G;
    }

    /**
     * minPath of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        if (v < 0 || v >= g.V() || w < 0 || w >= g.V())
            throw new IllegalArgumentException();

        int ancestor = ancestor(v, w);
        return ancestor != -1 ? minPath : -1;
    }

    /**
     * a week1.common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        if (v < 0 || v >= g.V() || w < 0 || w >= g.V())
            throw new IllegalArgumentException();

        minPath = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bfsToV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsToW = new BreadthFirstDirectedPaths(g, w);
        for (int k = 0; k < g.V(); k++) {
            if (bfsToV.hasPathTo(k) && bfsToW.hasPathTo(k)) {
                if (bfsToV.distTo(k) + bfsToW.distTo(k) < minPath) {
                    minPath = bfsToV.distTo(k) + bfsToW.distTo(k);
                    ancestor = k;
                }
            }
        }
        return ancestor;
    }

    /**
     * minPath of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        for (Integer item : v) {
            if (item == null || item < 0 || item >= g.V())
                throw new IllegalArgumentException();
        }

        for (Integer item : w) {
            if (item == null || item < 0 || item >= g.V())
                throw new IllegalArgumentException();
        }

        int ancestor = ancestor(v, w);
        if (ancestor != -1) {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
            return bfsV.distTo(ancestor) + bfsW.distTo(ancestor);
        }
        return -1;
    }

    /**
     * a week1.common ancestor that participates in shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();


        for (Integer item : v) {
            if (item == null || item < 0 || item >= g.V())
                throw new IllegalArgumentException();
        }

        for (Integer item : w) {
            if (item == null || item < 0 || item >= g.V())
                throw new IllegalArgumentException();
        }

        int min = Integer.MAX_VALUE;
        int minAncestor = -1;

        for (int vert1 : v) {
            for (int vert2 : w) {
                int ancestor = ancestor(vert1, vert2);
                int length = length(vert1, vert2);
                if (min > length && length != -1) {
                    min = length;
                    minAncestor = ancestor;
                }
            }
        }

        return minAncestor;
    }

    /**
     * do unit testing of this class
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 3;
        int w = 4;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("minPath = %d, ancestor = %d\n", length, ancestor);
    }
}
