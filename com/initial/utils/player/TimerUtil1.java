package com.initial.utils.player;

public final class TimerUtil1
{
    private long time;
    
    public TimerUtil1() {
        this.time = System.nanoTime() / 1000000L;
    }
    
    public boolean reach(final long time) {
        return this.time() >= time;
    }
    
    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
    
    public boolean sleep(final long time) {
        if (this.time() >= time) {
            this.reset();
            return true;
        }
        return false;
    }
    
    public short convertToMS(final float perSecond) {
        return (short)(1000.0f / perSecond);
    }
    
    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }
}
