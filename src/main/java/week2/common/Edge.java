package week2.common;

import java.util.Objects;

/**
 * Created by scher on 06.11.2020.
 */
public class Edge implements Comparable<Edge> {
    private int v;
    private int w;
    private double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int getFisrtVert() {
        return v;
    }

    public int getSecondVert(int vert) {
        if(w == vert)
            return v;

        return w;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        if(this.weight > o.weight) return 1;
        else if(this.weight < o.weight) return -1;
        else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null && v== ((Edge)obj).v && w == ((Edge)obj).w && Double.compare(weight, ((Edge)obj).weight) == 0)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v,w,weight);
    }
}
