package week2.task;

import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class SeamCarverTest {
    private SeamCarver testObj;

    @Before
    public void setup() {
        testObj = new SeamCarver(getSmallPicture());
    }

    @Test
    public void testVerticalSeam() {
        int[] expected = { 3, 4, 3, 2, 1};

        int[] actual = testObj.findVerticalSeam();

        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void testHorizontalSeam() {
        int[] expected = { 1, 2, 1, 2, 1, 0};

        int[] actual = testObj.findHorizontalSeam();

        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void testRemoveVerticalSeam() {
        int[] seam = { 3, 4, 3, 2, 1};
        int expectedWidth = testObj.width() - 1;
        int expectedHeight = testObj.height();

        testObj.removeVerticalSeam(seam);

        Assert.assertEquals(testObj.height(), expectedHeight);
        Assert.assertEquals(testObj.width(), expectedWidth);
    }

    @Test
    public void testRemoveHorizontalSeam() {
        int[] seam = { 1, 2, 1, 2, 1, 0};
        int expectedWidth = testObj.width();
        int expectedHeight = testObj.height() - 1;

        testObj.removeHorizontalSeam(seam);

        Assert.assertEquals(testObj.height(), expectedHeight);
        Assert.assertEquals(testObj.width(), expectedWidth);
    }

    private Picture getSmallPicture() {
        return new Picture(new File("src\\test\\java\\week2\\task\\6x5.PNG"));
    }
}
