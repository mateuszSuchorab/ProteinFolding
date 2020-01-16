package com.suchorab.service;

import com.suchorab.entity.Move;
import com.suchorab.entity.Protein;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProteinFolding {

    public void startSearch(String chain) {
        Map<Integer, Protein> proteinMap = new HashMap<>();
        Runnable task = () -> {
            for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(30); stop > System.nanoTime(); ) { // Loop for 30 seconds
                Protein protein = generateResults(chain);
                proteinMap.put(protein.getResult(), protein);  //Thread.currentThread().getId(); -> id of currency thread
            }
        };

        ExecutorService es = Executors.newCachedThreadPool();
        int availableProcessors = Runtime.getRuntime().availableProcessors(); // get a number of available processors
        long start = System.nanoTime();
        for (int i = 0; i <= availableProcessors; i++) {
            es.execute(task);
        }
        es.shutdown();

        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES); // check if the threads finished after one minute
            if (finished) {
                System.out.println("Generated in: " + (System.nanoTime() - start) / 1000000000+ " s");
                Protein bestProtein = proteinMap.entrySet().stream().max((entry1, entry2) -> entry1.getKey() > entry2.getKey() ? 1 : -1).get().getValue();
                printTable(bestProtein.getTable());
                System.out.println("Result: -" + bestProtein.getResult());
                System.out.println("Generated and filtered in : " + (System.nanoTime() - start) / 1000000000 + " s");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Protein generateResults(String chain) {
        Protein protein = new Protein(chain);
        generateTable(protein);
        generateResult(protein);
        return protein;
    }

    private void generateTable(Protein protein) {
        int[][] table = protein.getTable();
        String chain = protein.getChain();
        int chanLength = chain.length() - 1;
        int w = chanLength / 2;
        int h = chanLength / 2;
        table[h][w] = setTableValue(chain.charAt(0));
        Random random = new Random();
        int rand = 5; // randNext never will be 5
        for (int i = 1; i < chanLength; i++) {
            int randNext = random.nextInt(4);
            Move move = new Move(rand);
            while (rand == randNext || !canIGo(randNext, h, w, table, move)) {
                randNext = random.nextInt(4);
                if (move.haveSpaceToMove()) {
                    protein.setTable(null);
                    return;
                }
            }
            rand = randNext;

            if (rand == 0) { // Right
                table[h][w + 1] = setTableValue(chain.charAt(i));
                w++;
            } else if (rand == 1) { // Left
                table[h][w - 1] = setTableValue(chain.charAt(i));
                w--;
            } else if (rand == 2) { // Up
                table[h - 1][w] = setTableValue(chain.charAt(i));
                h--;
            } else if (rand == 3) { // Down
                table[h + 1][w] = setTableValue(chain.charAt(i));
                h++;
            }
        }
        protein.setTable(table);
    }

    private boolean canIGo(int value, int h, int w, int table[][], Move move) {
        int tableResult;
        if (value == 0) { // Right
            tableResult = table[h][w + 1];
            move.setRight(true);
        } else if (value == 1) { // Left
            tableResult = table[h][w - 1];
            move.setLeft(true);
        } else if (value == 2) { // Up
            tableResult = table[h - 1][w];
            move.setUp(true);
        } else { // Down
            tableResult = table[h + 1][w];
            move.setDown(true);
        }
        return tableResult == 0;
    }

    public int setTableValue(char letter) {
        if (letter == 'H') {
            return 2;
        }
        return 1;
    }

    public void generateResult(Protein protein) {
        int result = 0;
        int[][] table = protein.getTable();
        if (table != null) { // table is null when is damaged
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    if (i < table.length - 1 && table[i][j] != 0 &&
                            (table[i][j] + table[i + 1][j]) % 4 == 0) {
                        result++;
                    }
                    if (j < table[i].length - 1 && table[i][j] != 0 &&
                            (table[i][j] + table[i][j + 1]) % 4 == 0) {
                        result++;
                    }
                }
            }
        }
        protein.setResult(result);
    }

    private void printTable(int[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                String pp = " _ ";
                if (table[i][j] == 1) {
                    pp = " P ";
                } else if (table[i][j] == 2) {
                    pp = " H ";
                }
                System.out.print(pp);
            }
            System.out.println();
        }
    }

}
