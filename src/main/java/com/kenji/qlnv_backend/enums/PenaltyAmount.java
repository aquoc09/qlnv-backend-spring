package com.kenji.qlnv_backend.enums;

public enum PenaltyAmount {
    CHECK_IN_LATE(100000),
    CHECK_OUT_EARLY(200000),
    LEAVE_NO_NOTICE(300000),
    USE_PHONE_IN_WORKING_TIME(300000),
    LATE_DEADLINE(500000);

    private final double gross;

    PenaltyAmount(double gross) {this.gross = gross;}

    public double getGross(){
        return this.gross;
    }
}
