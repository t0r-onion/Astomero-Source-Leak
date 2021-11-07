package com.initial;

import com.initial.configs.*;
import com.initial.modules.impl.visual.*;
import com.initial.login.*;
import net.minecraft.client.multiplayer.*;
import com.initial.discord.*;
import com.initial.commands.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import com.initial.events.impl.*;
import com.initial.events.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import com.initial.modules.*;
import java.util.*;
import java.io.*;

public class Astomero
{
    public static String getLoginUser;
    public static String client_build;
    public String name;
    public String fullName;
    public static Astomero instance;
    public Configs configManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public ClickGUI clickGui;
    public AltManager altManager;
    public ServerData serverData;
    public File folder;
    public File dataDr;
    public static DiscordRP discordRP;
    public static CommandManager commandManager;
    
    public Astomero() {
        this.name = "Astomero";
        this.fullName = "stomero";
    }
    
    public void start() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "/astomeroCfgs");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.eventManager = new EventManager();
        this.moduleManager = new ModuleManager();
        this.configManager = new Configs();
        this.clickGui = new ClickGUI();
        this.altManager = new AltManager();
        Astomero.discordRP.start();
        Display.setTitle("Minecraft 1.8.8");
        EventManager.register(this);
        this.loadKeys();
    }
    
    public static void onEvent(final EventNigger e) {
        if (e instanceof EventChat) {
            Astomero.commandManager.handleChat((EventChat)e);
        }
    }
    
    public static DiscordRP getDiscordRP() {
        return Astomero.discordRP;
    }
    
    public void shutdown() {
        final EventManager eventManager = this.eventManager;
        EventManager.unregister(this);
        this.saveKeys();
    }
    
    @EventTarget
    public void onKey(final EventKey event) {
        this.moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
    
    public static void addDebugMessage(String message) {
        message = "§f[" + ChatFormatting.RED + "Debug§f] " + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    public static void addChatMessage(String message) {
        message = "§f[" + ChatFormatting.RED + "Astomero§f] " + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    public void saveKeys() {
        this.folder = new File(Minecraft.getMinecraft().mcDataDir + "/astomeroKeys");
        if (!this.folder.exists()) {
            this.folder.mkdir();
        }
        this.dataDr = new File(this.folder, "keys.iota");
        final ArrayList<String> toSave = new ArrayList<String>();
        for (final Module m : Astomero.instance.moduleManager.getModules()) {
            toSave.add(m.getName() + ">" + m.getKey());
        }
        try {
            final PrintWriter pw = new PrintWriter(this.dataDr);
            for (final String str : toSave) {
                pw.println(str);
            }
            pw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void loadKeys() {
        this.folder = new File(Minecraft.getMinecraft().mcDataDir + "/astomeroKeys");
        if (!this.folder.exists()) {
            this.folder.mkdir();
        }
        this.dataDr = new File(this.folder, "keys.iota");
        final ArrayList<String> lines = new ArrayList<String>();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.dataDr));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                lines.add(line);
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (final String s : lines) {
                final String[] args = s.split(">");
                for (final Module m : Astomero.instance.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[0])) {
                        m.setKey(Integer.parseInt(args[1]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        Astomero.getLoginUser = "?";
        Astomero.client_build = "210823";
        Astomero.instance = new Astomero();
        Astomero.discordRP = new DiscordRP();
        Astomero.commandManager = new CommandManager();
    }
}
