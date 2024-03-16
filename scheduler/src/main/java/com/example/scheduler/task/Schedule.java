package com.example.scheduler.task;

public class Schedule {
  
    //1000 = 1s  
    private static final long SECOND = 1000L;  
  
    public static final long FIXED_DELAY_1S = SECOND;  
  
    public static final long FIXED_DELAY_1M = 60 * FIXED_DELAY_1S;  
  
    //초 (0-59) 분 (0-59) 시 (0-23) 일 (1-31) 월 (1-12 또는 JAN-DEC) 요일 (0-7 또는 SUN-SAT)    public static final String EVERY_12AM = "0 0 0 * * *";  
  
}
