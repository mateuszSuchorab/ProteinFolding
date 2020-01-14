package com.suchorab;

import com.suchorab.service.ProteinFolding;

public class Main {

    public static void main(String[] args) {
        System.out.println("----- START -----");
        ProteinFolding proteinFolding = new ProteinFolding();
        proteinFolding.startSearch("HPHPPHHPHPPHPHHPPHPHPPHPHHPPHP"); // "HPHPPHHPHPPHPHHPPHPH"
        System.out.println("----- FINISH -----");
    }
}
