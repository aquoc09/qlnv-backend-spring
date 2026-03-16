package com.kenji.qlnv_backend.enums;

public enum ContractLevel {
    LVL_1(10000000),
    LVL_2(12500000),
    LVL_3(17000000),
    LVL_4(22000000);

    private final double gross;

    ContractLevel(double gross) {this.gross = gross;}

    public double getGross(){
        return this.gross;
    }
}

