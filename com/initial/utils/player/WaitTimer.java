package com.initial.utils.player;

public final class WaitTimer
{
    public long time;
    
    public WaitTimer() {
        this.time = System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }
    
    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
}
