package com.kenji.qlnv_backend.enums;

import java.time.LocalTime;

public enum WorkingTime {

    CHECK_IN(LocalTime.of(8, 0)),
    CHECK_OUT(LocalTime.of(17, 0));

    private final LocalTime time;

    WorkingTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
