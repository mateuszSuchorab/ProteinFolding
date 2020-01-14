package com.suchorab.entity;

public class Protein implements Cloneable {

    private int[][] table; // H = 2 P = 1
    private String chain;
    private int result;


    public Protein(String chain) {
        this.chain = chain.toUpperCase();
        if (!this.chain.matches("[PH]*")) {
            throw new IllegalArgumentException("must all be P/p or H/h");
        }
        int size = this.chain.length();
        if (size % 2 == 0) {
            size++;
        }
        table = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = 0;
            }
        }
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getChain() {
        return chain;
    }

    public Object clone() {
        try {
            Protein ret = (Protein) super.clone();
            ret.table = this.table;
            ret.chain = this.chain;
            ret.result = this.result;
            return ret;
        } catch (CloneNotSupportedException e) {
            return new RuntimeException(e);
        }
    }
}
