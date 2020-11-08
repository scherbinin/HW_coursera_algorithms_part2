package week1;

import week1.common.Graph;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by scher on 31.10.2020.
 */
public class DirectedCycleSearcherTest {
    private DirectedCycleSearcher directedCycleSearcher;

    @Test
    public void graphWithOneCycle_successfullyFound() {
        directedCycleSearcher = new DirectedCycleSearcher(getGraphWithOneCycle());
        Object[] expectedCycle = {2, 4, 5, 1};
        int expectedCycleLength = 4;

        directedCycleSearcher.search(0);

        Assert.assertEquals(directedCycleSearcher.getMinCycleLength(), expectedCycleLength);
        Assert.assertArrayEquals(directedCycleSearcher.getMinCycle().toArray(), expectedCycle);
    }

    @Test
    public void graphWithTwoCycles_successfullyFoundSmallestCycle() {
        directedCycleSearcher = new DirectedCycleSearcher(getGraphWithTwoCycle());
        Object[] expectedCycle = {5, 3, 4};
        int expectedCycleLength = 3;

        directedCycleSearcher.search(0);

        Assert.assertEquals(directedCycleSearcher.getMinCycleLength(), expectedCycleLength);
        Assert.assertArrayEquals(directedCycleSearcher.getMinCycle().toArray(), expectedCycle);
    }

    @Test
    public void graphWithNoCycles_successfullyFoundSmallestCycle() {
        directedCycleSearcher = new DirectedCycleSearcher(getGraphWithNoCycles());
        Object[] expectedCycle = {};
        int expectedCycleLength = 0;

        directedCycleSearcher.search(0);

        Assert.assertEquals(directedCycleSearcher.getMinCycleLength(), expectedCycleLength);
        Assert.assertArrayEquals(directedCycleSearcher.getMinCycle().toArray(), expectedCycle);
    }

    private Graph getGraphWithNoCycles() {
        Graph g = new Graph(7, true);

        g.addEdge(0,1);
        g.addEdge(0,6);
        g.addEdge(1,2);
        g.addEdge(1,5);
        g.addEdge(2,4);
        g.addEdge(3,2);
        g.addEdge(4,5);
        g.addEdge(5,6);

        return g;
    }

    private Graph getGraphWithOneCycle() {
        Graph g = new Graph(7, true);

        g.addEdge(0,1);
        g.addEdge(0,6);
        g.addEdge(1,2);
        g.addEdge(2,4);
        g.addEdge(3,2);
        g.addEdge(4,5);
        g.addEdge(1,5);
        g.addEdge(5,6);
        g.addEdge(5,1);

        return g;
    }

    private Graph getGraphWithTwoCycle() {
        Graph g = getGraphWithOneCycle();

        g.addEdge(5,3);
        g.addEdge(3,4);

        return g;
    }
}
