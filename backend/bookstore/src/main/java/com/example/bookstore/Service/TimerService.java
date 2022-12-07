package com.example.bookstore.Service;

public interface TimerService {
    boolean setTimer();
    boolean endTimer();
    Long getRemainTime();
}
