package com.initial.targets;

import java.util.*;

public class TargetManager
{
    private static final ArrayList<String> targets;
    
    public static void addTarget(final String name) {
        TargetManager.targets.add(name);
    }
    
    public static void removeTarget(final String name) {
        TargetManager.targets.remove(name);
    }
    
    public static ArrayList<String> getTargets() {
        return TargetManager.targets;
    }
    
    static {
        targets = new ArrayList<String>();
    }
}
