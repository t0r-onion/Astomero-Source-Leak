package com.initial.utils.player;

public class Timerrrr
{
    public long lastMS;
    
    public Timerrrr() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public void setTime(final long time) {
        this.lastMS = time;
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
}
