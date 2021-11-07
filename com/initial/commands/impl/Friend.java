package com.initial.commands.impl;

import com.initial.commands.*;
import com.initial.*;
import com.initial.targets.*;
import java.util.*;

public class Friend extends Command
{
    public Friend() {
        super("Friend", "Prevents your KillAura from attacking someone", "friend", new String[] { "f" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        try {
            if (args.length == 1) {
                final String type = args[0];
                if (type.equals("list")) {
                    Astomero.addChatMessage(" ");
                    Astomero.addChatMessage("Friend List:");
                    final ArrayList<String> friends = FriendManager.getFriends();
                    for (int i = 0; i < friends.size(); ++i) {
                        final String s = friends.get(i);
                        Astomero.addChatMessage(i + 1 + ". " + s);
                    }
                    Astomero.addChatMessage(" ");
                }
                else if (type.equals("clear")) {
                    FriendManager.getFriends().clear();
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
                        Astomero.addChatMessage("Added " + name + " as a friend!");
                        FriendManager.addFriend(name);
                    }
                    case "remove": {
                        Astomero.addChatMessage("Removed " + name + " from your friend list");
                        FriendManager.removeFriend(name);
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
