package com.initial.utils.player;

public class Timerr
{
    private long prevMS;
    
    public Timerr() {
        this.prevMS = 0L;
        this.reset();
    }
    
    public boolean delay(final float milliSec) {
        return this.getTime() - this.prevMS >= milliSec;
    }
    
    public void reset() {
        this.prevMS = this.getTime();
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean sleep(final long time) {
        if (this.getTime() >= time) {
            this.reset();
            return true;
        }
        return false;
    }
}
