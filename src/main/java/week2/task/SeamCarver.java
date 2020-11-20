package week2.task;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private static final double ENERGY_BASE = 1000;

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    /**
     * current picture
     */
    public Picture picture() {
        return picture;
    }

    /**
     * width of current picture
     */
    public int width() {
        return picture.width();
    }

    /**
     * height of current picture
     */
    public int height() {
        return picture.height();
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        if (x < 0 || x > picture.width() - 1 || y < 0 || y > picture.height() - 1)
            throw new IllegalArgumentException();

        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1)
            return ENERGY_BASE;

        Color colorFirst = picture.get(x - 1, y);
        Color colorSecond = picture.get(x + 1, y);
        int deltaR = colorSecond.getRed() - colorFirst.getRed();
        int deltaG = colorSecond.getGreen() - colorFirst.getGreen();
        int deltaB = colorSecond.getBlue() - colorFirst.getBlue();

        int deltaX = deltaB * deltaB + deltaG * deltaG + deltaR * deltaR;

        colorFirst = picture.get(x, y - 1);
        colorSecond = picture.get(x, y + 1);

        deltaR = colorSecond.getRed() - colorFirst.getRed();
        deltaG = colorSecond.getGreen() - colorFirst.getGreen();
        deltaB = colorSecond.getBlue() - colorFirst.getBlue();

        int deltaY = deltaB * deltaB + deltaG * deltaG + deltaR * deltaR;

        return Math.sqrt(deltaX + deltaY);
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        int[] seam = new int[picture.width()];
        double[][] energyMatrix = new double[picture.width()][picture.height()];
        double[][] weights = new double[picture.width()][picture.height()];
        Point[][] comeFrom = new Point[picture.width()][picture.height()];
        double minBottomW = Double.POSITIVE_INFINITY;
        int indexMinRightW = 0;

        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                energyMatrix[x][y] = energy(x, y);
                weights[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        // Fill start row for path calculations
        for (int y = 0; y < picture.height(); y++) {
            comeFrom[0][y] = new Point(0, y);
            weights[0][y] = ENERGY_BASE;
        }

        // Because graph can be counted like topologicaly sorted we can define the topoligical order,
        // It going to be by layers
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                // Cycle for adjacent pixels (vertexes)
                for (Point pixel : adjHorizontalTopology(x, y)) {
                    // recalculate path for adjacent vertexes
                    if (weights[pixel.x][pixel.y] > weights[x][y] + energyMatrix[pixel.x][pixel.y]) {
                        comeFrom[pixel.x][pixel.y] = new Point(x, y);
                        weights[pixel.x][pixel.y] = weights[x][y] + energyMatrix[pixel.x][pixel.y];
                    }
                }

                // Finding the min pixel W in the bottom row
                if (x == picture.width() - 1 && minBottomW > weights[x][y]) {
                    minBottomW = weights[x][y];
                    indexMinRightW = y;
                }
            }
        }

        // Find path from bottom to top
        Point curr = new Point(picture.width() - 1, indexMinRightW);
        while (curr.x != 0) {
            seam[curr.x] = curr.y;
            curr = comeFrom[curr.x][curr.y];
        }
        seam[0] = curr.y;

        return seam;
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        int[] seam = new int[picture.height()];
        double[][] energyMatrix = new double[picture.width()][picture.height()];
        double[][] weights = new double[picture.width()][picture.height()];
        Point[][] comeFrom = new Point[picture.width()][picture.height()];
        double minBottomW = Double.POSITIVE_INFINITY;
        int indexMinBottomW = 0;

        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                energyMatrix[x][y] = energy(x, y);
                weights[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        // Fill start row for path calculations
        for (int x = 0; x < picture.width(); x++) {
            comeFrom[x][0] = new Point(x, 0);
            weights[x][0] = ENERGY_BASE;
        }

        // Because graph can be counted like topologicaly sorted we can define the topoligical order,
        // It going to be by layers
        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                // Cycle for adjacent pixels (vertexes)
                for (Point pixel : adjVerticalTopology(x, y)) {
                    // recalculate path for adjacent vertexes
                    if (weights[pixel.x][pixel.y] > weights[x][y] + energyMatrix[pixel.x][pixel.y]) {
                        comeFrom[pixel.x][pixel.y] = new Point(x, y);
                        weights[pixel.x][pixel.y] = weights[x][y] + energyMatrix[pixel.x][pixel.y];
                    }
                }

                // Finding the min pixel W in the bottom row
                if (y == picture.height() - 1 && minBottomW > weights[x][y]) {
                    minBottomW = weights[x][y];
                    indexMinBottomW = x;
                }
            }
        }

        // Find path from bottom to top
        Point curr = new Point(indexMinBottomW, picture.height() - 1);
        while (curr.y != 0) {
            seam[curr.y] = curr.x;
            curr = comeFrom[curr.x][curr.y];
        }
        seam[0] = curr.x;

        return seam;
    }

    /**
     * remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
        if(seam.length !=  picture.width())
            throw new IllegalArgumentException();

        Picture newPicture = new Picture(picture.width(), picture.height() - 1);

        for (int x = 0; x < picture.width(); x++) {
            int yNew = 0;

            if(seam[x] > picture.height() - 1 || seam[x] < 0)
                throw new IllegalArgumentException();

            for (int y = 0; y < picture.height(); y++) {
                if (y == seam[x])
                    continue;
                newPicture.set(x, yNew, picture.get(x, y));
                yNew++;
            }
        }

        this.picture = newPicture;
    }

    /**
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
        if(seam.length !=  picture.height())
            throw new IllegalArgumentException();

        Picture newPicture = new Picture(picture.width()-1, height());

        for (int y = 0; y < picture.height(); y++) {
            int xNew = 0;

            if(seam[y] > picture.width() - 1 || seam[y] < 0)
                throw new IllegalArgumentException();

             for (int x = 0; x < picture.width(); x++){
                if (x == seam[y])
                    continue;
                newPicture.set(xNew, y, picture.get(x, y));
                xNew++;
            }
        }

        this.picture = newPicture;
    }

    private Point[] adjVerticalTopology(int x, int y) {
        Point[] res;

        if (y == picture.height() - 1) {
            res = new Point[0];
        } else if (x == 0) {
            res = new Point[2];
            res[0] = new Point(x, y + 1);
            res[1] = new Point(x + 1, y + 1);
        } else if (x == picture.width() - 1) {
            res = new Point[2];
            res[0] = new Point(x - 1, y + 1);
            res[1] = new Point(x, y + 1);
        } else {
            res = new Point[3];
            res[0] = new Point(x - 1, y + 1);
            res[1] = new Point(x, y + 1);
            res[2] = new Point(x + 1, y + 1);
        }

        return res;
    }

    private Point[] adjHorizontalTopology(int x, int y) {
        Point[] res;

        if (x == picture.width() - 1) {
            res = new Point[0];
        } else if (y == 0) {
            res = new Point[2];
            res[0] = new Point(x + 1, y);
            res[1] = new Point(x + 1, y + 1);
        } else if (y == picture.height() - 1) {
            res = new Point[2];
            res[0] = new Point(x + 1, y);
            res[1] = new Point(x + 1, y - 1);
        } else {
            res = new Point[3];
            res[0] = new Point(x + 1, y - 1);
            res[1] = new Point(x + 1, y);
            res[2] = new Point(x + 1, y + 1);
        }

        return res;
    }

    private class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //  UncommentedEmptyMethodBody
    public static void main(String[] args) {

    }
}
