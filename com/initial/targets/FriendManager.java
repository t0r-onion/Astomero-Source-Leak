package com.initial.targets;

import java.util.*;

public class FriendManager
{
    private static final ArrayList<String> friends;
    
    public static void addFriend(final String name) {
        FriendManager.friends.add(name);
    }
    
    public static void removeFriend(final String name) {
        FriendManager.friends.remove(name);
    }
    
    public static ArrayList<String> getFriends() {
        return FriendManager.friends;
    }
    
    static {
        friends = new ArrayList<String>();
    }
}
