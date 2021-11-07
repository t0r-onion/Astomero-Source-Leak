package com.initial.utils;

import com.initial.utils.movement.*;

public class Pene
{
    private static transient boolean onGround;
    private static transient boolean lastOnGround;
    private static transient boolean lastLastOnground;
    
    public Pene() {
        Pene.lastLastOnground = Pene.lastOnGround;
        Pene.lastOnGround = Pene.onGround;
        Pene.onGround = MovementUtils.isOnGround(1.0E-4);
    }
    
    public static boolean isOnGround() {
        return Pene.onGround = MovementUtils.isOnGround(1.0E-4);
    }
    
    public static boolean isLastOnGround() {
        return Pene.lastOnGround;
    }
    
    public static boolean isLastLastOnground() {
        return Pene.lastLastOnground;
    }
    
    static {
        Pene.onGround = false;
        Pene.lastOnGround = false;
        Pene.lastLastOnground = false;
    }
}
