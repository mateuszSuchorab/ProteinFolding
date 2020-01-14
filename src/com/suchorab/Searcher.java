package com.suchorab;

public class Searcher {
    private final Protein cur;
    private Protein best;
    private int bestScore;

    public Searcher(String chain) {
        cur = new Protein(chain);
        best = (Protein) cur.clone();
        bestScore = Integer.MIN_VALUE;
    }

    public Protein search() {
        search(0, cur.getLength() - 1, cur.getLength() - 1);
        return best;
    }

    private void search(int index, int row, int col) {
        cur.setLocation(index, row, col);
        if(index >= cur.getLength() - 1) { // we have placed final residue
            int score = cur.getScore();
            if(score > bestScore) {
                bestScore = score;
                best = (Protein) cur.clone();
            }
        } else {
            search(index + 1, row + 1, col); // try stepping south
            search(index + 1, row, col + 1); // or east
            search(index + 1, row - 1, col); // or north
            search(index + 1, row, col - 1); // or west
        }
    }
}