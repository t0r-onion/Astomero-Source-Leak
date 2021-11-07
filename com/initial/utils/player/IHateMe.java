package com.initial.utils.player;

public class IHateMe
{
    private long lastMS;
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public void setTime(final long time) {
        this.lastMS = time;
    }
    
    public boolean check(final float milliseconds) {
        return this.getTime() >= milliseconds;
    }
}
