package com.initial.utils.player;

public class MemerV2
{
    private long lastTime;
    
    public MemerV2() {
        this.reset();
    }
    
    public long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public long getLastTime() {
        return this.lastTime;
    }
    
    public long getDifference() {
        return this.getCurrentTime() - this.lastTime;
    }
    
    public void reset() {
        this.lastTime = this.getCurrentTime();
    }
    
    public boolean hasReached(final long milliseconds) {
        return this.getDifference() >= milliseconds;
    }
    
    public MemerV2 hasTimeElapsed(final long milliseconds) {
        if (this.getDifference() >= milliseconds) {
            return this;
        }
        return null;
    }
}
