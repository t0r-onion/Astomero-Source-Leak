package com.initial.ui.changelog;

import java.util.*;
import java.awt.*;
import com.mojang.realmsclient.gui.*;

public class ChangelogManager
{
    public static ArrayList<Change> changes;
    
    public ChangelogManager() {
        add(ChangeType.NEW, "Added niggaman123");
    }
    
    public static void add(final ChangeType type, String content) {
        Color color = new Color(16777215);
        if (type != null && type != ChangeType.NULL) {
            content = ChatFormatting.WHITE + content;
            switch (type) {
                case NEW: {
                    color = new Color(1179477);
                    break;
                }
                case UPDATE: {
                    color = new Color(65535);
                    break;
                }
                case FIX: {
                    color = new Color(16777028);
                    break;
                }
                case DELETE: {
                    color = new Color(16711680);
                    break;
                }
            }
        }
        else if (type == ChangeType.NULL) {
            color = new Color(14408667);
        }
        ChangelogManager.changes.add(new Change(content, type, color));
    }
    
    static {
        ChangelogManager.changes = new ArrayList<Change>();
    }
}
