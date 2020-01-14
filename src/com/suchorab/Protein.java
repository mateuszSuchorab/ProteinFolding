package com.suchorab;

import java.io.PrintStream;

public class Protein implements Cloneable {
    private String chain; // string of H or P's representing amino acids
    private int[] row;    // row[i] represents y-coordinate of residue i
    private int[] col;    // col[i] represents x-coordinate of residue i

    /** Constructs a protein that is simply lined. */
    public Protein(String chain) {
        for (int i = 0; i < chain.length(); i++) {
            char c = chain.charAt(i);
            if (c != 'H' && c != 'P') {
                throw new IllegalArgumentException("must all be P or H");
            }
        }
        this.chain = chain;
        this.row = new int[chain.length()];
        this.col = new int[chain.length()];
        for (int i = 0; i < col.length; i++) {
            row[i] = 0;
            col[i] = i;
        }
    }

    /** Returns the number of amino acids in this protein chain. */
    public int getLength() {
        return chain.length();
    }

    /** Returns true if residue at index is hydrophobic. */
    public boolean isHydrophobic(int index) {
        return chain.charAt(index) == 'H';
    }

    /** Returns y-coordinate at which residue at index has been placed. */
    public int getRow(int index) {
        return row[index];
    }

    /** Returns x-coordinate at which residue at index has been placed. */
    public int getColumn(int index) {
        return col[index];
    }

    /** Relocates the residue at index to specified y- and x-coordinate. */
    public void setLocation(int index, int rowLoc, int colLoc) {
        row[index] = rowLoc;
        col[index] = colLoc;
    }

    /** Creates a duplicate of this protein chain. */
    public Object clone() {
        try {
            Protein ret = (Protein) super.clone();
            ret.row = new int[this.row.length];
            System.arraycopy(this.row, 0, ret.row, 0, this.row.length);
            ret.col = new int[this.col.length];
            System.arraycopy(this.col, 0, ret.col, 0, this.col.length);
            return ret;
        } catch (CloneNotSupportedException e) {
            return new RuntimeException(e);
        }
    }

    /** Returns the score for this protein chain, which is
     * negative if the chain overlaps itself and otherwise is
     * the number of adjacent pairs of hydrophobic residues. */
    public int getScore() {
        int ret = 0;
        int length = chain.length();
        for (int i = 1; i < length; i++) {
            int dx = Math.abs(col[i] - col[i - 1]);
            int dy = Math.abs(row[i] - row[i - 1]);
            if (dx + dy != 1) {
                // chain is illegal since a chained pair is not adjacent on grid
                return Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < length; i++) {
            int r0 = row[i], c0 = col[i];
            for (int j = i + 1; j < length; j++) {
                int r1 = row[j], c1 = col[j];
                if (r0 == r1 && c0 == c1) {
                    // chain is illegal since it wraps back onto itself
                    return Integer.MIN_VALUE + 1;
                }
                if (isHydrophobic(i) && isHydrophobic(j)) {
                    if (r0 == r1) {
                        if (c0 == c1 + 1 || c1 == c0 + 1) ret++;
                    } else if (c0 == c1) {
                        if (r0 == r1 + 1 || r1 == r0 + 1) ret++;
                    }
                }
            }
        }
        return ret;
    }

    /** Displays the amino acid to the specified output stream. */
    public void print(PrintStream out) {
        // Compute the range of rows and columns represented.
        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        for (int i = 0; i < row.length; i++) {
            if (row[i] < minRow) minRow = row[i];
            if (row[i] > maxRow) maxRow = row[i];
            if (col[i] < minCol) minCol = col[i];
            if (col[i] > maxCol) maxCol = col[i];
        }

        // Create a grid representing what to display at each location.
        // (Each entry has 1 bit set if horizontal line connects to right
        // and 2 bit set if vertical line connects to below. If a residue
        // is at that location, its index is added once, multiplied by 4,
        // and placed into grid.)
        int[][] grid = new int[maxRow - minRow + 1][maxCol - minCol + 1];
        int r0 = row[0] - minRow;
        int c0 = col[0] - minCol;
        grid[r0][c0] = 4; // mark that amino 0 is found at (r0,c0)
        for (int i = 1; i < row.length; i++) {
            int r1 = row[i] - minRow;
            int c1 = col[i] - minCol;
            grid[r1][c1] += 4 * (i + 1); // mark than amino i is at (r1,c1)
            if (r0 == r1) {
                if (c1 == c0 + 1) grid[r0][c0] += 1;
                else if (c0 == c1 + 1) grid[r1][c1] += 1;
            } else {
                if (r1 == r0 + 1) grid[r0][c0] += 2;
                else if (r0 == r1 + 1) grid[r1][c1] += 2;
            }
            r0 = r1;
            c0 = c1;
        }

        // Display the grid as computed.
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int k = grid[i][j];
                int pos = k / 4 - 1;

                if (pos < 0) out.print(" ");
                else if (pos == 0) out.print(Character.toLowerCase(chain.charAt(pos)));
                else if (pos < chain.length()) out.print(chain.charAt(pos));
                else out.print("?");

                if (j < grid[0].length - 1) {
                    if (k % 2 == 1) out.print("--");
                    else out.print("  ");
                }
            }
            out.println();
            if (i < grid.length - 1) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] % 4 >= 2) out.print("|");
                    else out.print(" ");
                    if (j < grid[0].length - 1) out.print("  ");
                }
                out.println();
            }
        }
    }

    public static int testChain(String chain) {
        if (chain.length() <= 1) {
            System.out.printf("%7.1f ms%3d %s\n", 0.0, 0, chain);
            return 0;
        } else {
            long start = System.nanoTime();
            Searcher searcher = new Searcher(chain);
            Protein best = searcher.search();
            double elapse = (System.nanoTime() - start) / 1e6;
            int score = best.getScore();
            System.out.printf("%7.1f ms%3d %s\n", elapse, score, chain);
            return score;
        }
    }
}