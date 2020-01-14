package com.suchorab;

import com.suchorab.service.ProteinFolding;

public class Main {

    public static void main(String[] args) {
//        String test = "HPHPPHHPHPPHPHHPPHPH".toUpperCase();
//        if (test.length() == 0) {
//            System.out.println("usage: java Protein CHAIN1 [CHAIN2 ...]");
//            return;
//        }
//        long totalStart = System.nanoTime();
//        int totalScore = 0;
//       // for (int arg = 0; arg < test.length(); arg++) {
//            totalScore += Protein.testChain(test);
//        //}
//        double totalElapse = (System.nanoTime() - totalStart) / 1e6;
//        System.out.printf("%7.1f ms%3d TOTAL\n", totalElapse, totalScore);
        ProteinFolding proteinFolding = new ProteinFolding("HPHPPHHPHPPHPHHPPHPH"); // "HPHPPHHPHPPHPHHPPHPH"
        System.out.println(proteinFolding.startSearch());
    }
}
