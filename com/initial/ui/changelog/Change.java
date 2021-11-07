package com.initial.ui.changelog;

import java.awt.*;

public class Change
{
    public String content;
    public ChangeType type;
    public Color color;
    
    public Change(final String content, final ChangeType type, final Color color) {
        this.content = content;
        this.type = type;
        this.color = color;
    }
}
