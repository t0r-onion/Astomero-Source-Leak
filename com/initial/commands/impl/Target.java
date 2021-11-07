package com.initial.commands.impl;

import com.initial.commands.*;
import com.initial.*;
import com.initial.targets.*;
import java.util.*;

public class Target extends Command
{
    public Target() {
        super("Target", "Targets someone", "target", new String[] { "t" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        try {
            if (args.length == 1) {
                final String type = args[0];
                if (type.equals("list")) {
                    Astomero.addChatMessage(" ");
                    Astomero.addChatMessage("Target List:");
                    final ArrayList<String> targets = TargetManager.getTargets();
                    for (int i = 0; i < targets.size(); ++i) {
                        final String s = targets.get(i);
                        Astomero.addChatMessage(i + 1 + ". " + s);
                    }
                    Astomero.addChatMessage(" ");
                }
                else if (type.equals("clear")) {
                    Astomero.addChatMessage("Cleared your target List");
                    TargetManager.getTargets().clear();
                }
                else {
                    Astomero.addChatMessage("Could not find the command!");
                }
            }
            if (args.length == 2) {
                final String type = args[0];
                final String name = args[1];
                final String s2 = type;
                switch (s2) {
                    case "add": {
                        Astomero.addChatMessage("Added " + name + " as a target!");
                        TargetManager.addTarget(name);
                    }
                    case "remove": {
                        Astomero.addChatMessage("Removed " + name + " from your targets list");
                        TargetManager.addTarget(name);
                    }
                    default: {
                        Astomero.addChatMessage("Could not find the command.");
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
}
