package com.yang.util;

public class GetCurrentTimeByMin {

    public static int getCurrentMinuteTime() {
        long curSec = System.currentTimeMillis();
        curSec -= curSec % (1000 * 60);
        return (int) (curSec / 1000);
    }
}
