package week1.task;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    /**
     * constructor takes a WordNet object
     */
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     */
    public String outcast(String[] nouns) {
        if (nouns == null || nouns.length == 0)
            throw new IllegalArgumentException();

        int maxDist = 0;
        int maxIndex = 0;

        for (int i = 0; i < nouns.length; i++) {
            int nounDist = 0;
            for (int j = 0; j < nouns.length; j++) {
                nounDist += wordNet.distance(nouns[i], nouns[j]);
            }

            if (maxDist < nounDist) {
                maxDist = nounDist;
                maxIndex = i;
            }
        }

        return nouns[maxIndex];
    }

    /**
     * see test client below
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
