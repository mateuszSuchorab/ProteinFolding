package com.suchorab;

import com.suchorab.service.ProteinFolding;

public class Main {

    public static void main(String[] args) {
        ProteinFolding proteinFolding = new ProteinFolding("HPHPPHHPHPPHPHHPPHPH"); // "HPHPPHHPHPPHPHHPPHPH"
        proteinFolding.startSearch();
    }
}
