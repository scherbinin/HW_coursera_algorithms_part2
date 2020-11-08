package week1.task;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by scher on 01.11.2020.
 */
public class WordNet {
    private final Map<String, List<Integer>> synonymsToIds = new HashMap<>();
    private final Map<Integer, String> idsToSynonyms = new HashMap<>();
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(synsets);
        String[] lines = in.readAllLines();
        Digraph graph = new Digraph(lines.length);
        String[] tuple;
        for (String line : lines) {
            tuple = line.split(",");
            Integer synsetId = Integer.parseInt(tuple[0]);
            for (String synonym : tuple[1].split(" ")) {
                synonymsToIds
                        .computeIfAbsent(synonym, k -> new LinkedList<>())
                        .add(synsetId);
            }
            idsToSynonyms.put(synsetId, tuple[1]);
        }
        in.close();
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            tuple = line.split(",");
            for (int i = 1; i < tuple.length; i++) {
                graph.addEdge(Integer.parseInt(tuple[0]), Integer.parseInt(tuple[i]));
            }
        }
        in.close();
        List<Integer> roots = findRoots(graph);
        if (roots.size() != 1 || hasCycle(graph)) {
            throw new IllegalArgumentException("Not a DAG!");
        }
        sap = new SAP(graph);
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synonymsToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null || word.isEmpty())
            throw new IllegalArgumentException();

        return synonymsToIds.containsKey(word);
    }

    /**
     * distance between nounA and nounB (defined below)
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(synonymsToIds.get(nounA), synonymsToIds.get(nounB));
    }

    /**
     * a synset (second field of synsets.txt) that is the week1.common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below)
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int ancestor = sap.ancestor(synonymsToIds.get(nounA), synonymsToIds.get(nounB));
        return idsToSynonyms.get(ancestor);
    }

    private List<Integer> findRoots(Digraph g) {
        List<Integer> roots = new LinkedList<>();
        for (int v = 0; v < g.V(); v++) {
            if (!g.adj(v).iterator().hasNext()) {
                roots.add(v);
            }
        }
        return roots;
    }

    /**
     * Checks whether given <code>Digraph</code> contains a cycle.
     * <p>
     * This done using strong components algorithm.
     *
     * @param g
     * @return
     */
    private boolean hasCycle(Digraph g) {
        DirectedCycle directedCycle = new DirectedCycle(g);
        return directedCycle.hasCycle();
    }
}
