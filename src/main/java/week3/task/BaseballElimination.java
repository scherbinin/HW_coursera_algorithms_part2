package week3.task;


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.HashMap;
import java.util.Iterator;

public class BaseballElimination {
    private int numTeams;
    private HashMap<String, Integer[]> teams;
    private HashMap<Integer, String> index2TeamName;
    private int[][] against;
    private Bag<String> subset;
    private int[] wins;
    private boolean checked;

    /**
     * create a baseball division from given filename in format specified below
     */
    public BaseballElimination(String filename) {
        teams = new HashMap<>();
        index2TeamName = new HashMap<>();
        In in = new In(filename);
        numTeams = in.readInt();
        against = new int[numTeams][numTeams];
        wins = new int[numTeams];

        int i = 0;
        while (!in.isEmpty()) {
            // read team name, number, wins, losses, and remaining to hashmap
            String team = in.readString();
            Integer[] games = new Integer[4];
            games[0] = i; // team index
            games[1] = in.readInt(); // wins
            wins[i] = games[1];
            games[2] = in.readInt(); // loss
            games[3] = in.readInt(); // remaining games
            teams.put(team, games);
            index2TeamName.put(games[0], team);

            // add current team's future matches to game matrix
            for (int j = 0; j < numTeams; j++) {
                against[i][j] = in.readInt();
            }
            i++;
        }
    }

    /**
     * number of teams
     */
    public int numberOfTeams() {
        return numTeams;
    }

    /**
     * all teams
     */
    public Iterable<String> teams() {
        return teams.keySet();
    }

    /**
     * number of wins for given team
     */
    public int wins(String team) {
        teamNameValidate(team);

        return teams.get(team)[1];
    }

    /**
     * number of losses for given team
     */
    public int losses(String team) {
        teamNameValidate(team);

        return teams.get(team)[2];
    }

    /**
     * number of remaining games for given team
     */
    public int remaining(String team) {
        teamNameValidate(team);

        return teams.get(team)[3];
    }

    /**
     * number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        teamNameValidate(team1);
        teamNameValidate(team2);

        int tema1Index = teams.get(team1)[1];
        int tema2Index = teams.get(team2)[1];

        return against[tema1Index][tema2Index];
    }

    /**
     * is given team eliminated?
     */
    public boolean isEliminated(String team) {
        teamNameValidate(team);

        boolean flag = false;
        subset = new Bag<String>();
        // trivial elimination
        for (Iterator<String> teams = teams().iterator(); teams.hasNext();) {
            String current = teams.next();
            if (wins(team) + remaining(team) < wins(current)) {
                subset.add(current);
                checked = true;
                return true;
            }
        }
        flag = flowCheck(team);
        checked = true;
        return flag;
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        teamNameValidate(team);

        boolean flag = isEliminated(team);
        if (flag) return subset;
        else return null;
    }

    private boolean flowCheck(String team) {
        teamNameValidate(team);

        // initialize flow network with source [size-2] and sink [size-1]
        int gameCombos = numGameCombos(numTeams);
        int size = 2+numTeams+gameCombos-1;
        int teamNumber = teams.get(team)[0];
        FlowNetwork network = new FlowNetwork(size);

        // add edges for source to game combination vertices to teams
        int x = 0; int y = 0; int z = 0; int sum = 0; String[] networkTeams = new String[numTeams-1];
        for (int i = 0; i < numTeams; i++) {
            if (i != teamNumber) {
                for (int j = 0; j < numTeams; j++) {
                    if (j != teamNumber && j > i) {
                        network.addEdge(new FlowEdge(size-2, x, against[i][j]));
                        sum += against[i][j];
                        network.addEdge(new FlowEdge(x, size-1-numTeams+y, Double.POSITIVE_INFINITY));
                        network.addEdge(new FlowEdge(x, size-numTeams+z, Double.POSITIVE_INFINITY));
                        x++; z++;
                    }
                }

                int weight = wins(team) + remaining(team) - wins[i];
                network.addEdge(new FlowEdge(size-1-numTeams+y, size-1, weight));
                networkTeams[y] = index2TeamName.get(i);

                y++; z = y;
            }
        }

        // calculate maxflow using FF
        FordFulkerson ff = new FordFulkerson(network, size-2, size-1);
        if (sum == ff.value()) return false;
        else {
            for (int v = gameCombos; v < size-2; v++) {
                if (ff.inCut(v)) {
                    subset.add(networkTeams[v-gameCombos]);
                }
            }
            return true;
        }
    }

    private int numGameCombos(int x) {
        int n = x - 1;
        return n*(n-1)/2;
    }

    private void teamNameValidate(String team) {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
    }
}
