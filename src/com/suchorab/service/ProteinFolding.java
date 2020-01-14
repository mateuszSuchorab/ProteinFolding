package com.suchorab.service;

import com.suchorab.entity.Protein;

import java.util.concurrent.TimeUnit;

public class ProteinFolding {

    private Protein protein;

    public ProteinFolding(String chain) {
        protein = new Protein(chain);
    }

    public int startSearch() {
        long start = System.nanoTime();
        int proteinFoldingCount =0;
        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(30); stop > System.nanoTime(); ) { // Loop for 30 seconds
            for (int i = 0; i <getChainLength(); i ++){
                int foldingCout = 0;
                while (i < foldingCout){
                    //foldingCout = generateFoldingCout();
                }
                //TODO
                // write a implementation a for loop to get a count of protein folding
                proteinFoldingCount = i;
            }
        }
        System.out.println((System.nanoTime() - start) / 1000000000);
        return proteinFoldingCount;
    }

    public int getChainLength() {
        return this.protein.getChain().length();
    }

    public String getChain() {
        return this.protein.getChain();
    }
}
