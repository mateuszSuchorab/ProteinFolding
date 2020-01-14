package com.suchorab.entity;

import java.util.ArrayList;
import java.util.List;

public class Protein implements Cloneable {

    private List<Integer> row, col;
    private String chain;
    private long hydrophobicSize;

    public Protein(String chain) {
        this.chain = chain.toUpperCase();
        if (!this.chain.matches("[PH]*")) {
            throw new IllegalArgumentException("must all be P/p or H/h");
        }
        int size = this.chain.length();
        this.hydrophobicSize = chain.chars().filter(ch -> ch == 'H').count();
        row = new ArrayList<>();
        col = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            row.add(0);
            col.add(i);
        }
    }

    public int getLength() {
        return chain.length();
    }

    public boolean isHydrophobic(int index) {
        return chain.charAt(index) == 'H';
    }

    public int getRowAtIndex(int index) {
        return row.get(index);
    }

    public int getColumnAtIndex(int index) {
        return col.get(index);
    }

    public Object clone() {
        try {
            Protein ret = (Protein) super.clone();
            ret.row = this.row;
            ret.col = this.col;
            return ret;
        } catch (CloneNotSupportedException e) {
            return new RuntimeException(e);
        }
    }

    public List<Integer> getRow() {
        return row;
    }

    public void setRow(List<Integer> row) {
        this.row = row;
    }

    public List<Integer> getCol() {
        return col;
    }

    public void setCol(List<Integer> col) {
        this.col = col;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public long getHydrophobicSize() {
        return hydrophobicSize;
    }

    public void setHydrophobicSize(long hydrophobicSize) {
        this.hydrophobicSize = hydrophobicSize;
    }
}
