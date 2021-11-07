package com.initial.protection;

import com.initial.login.*;
import java.util.*;
import java.security.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.net.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.initial.utils.render.*;
import java.io.*;

public class HWID extends GuiScreen
{
    private PasswordField password;
    private GuiTextField username;
    public static GuiMainMenu guiMainMenu;
    public static String Status;
    public static Scanner scanner;
    private static final char[] hexArray;
    
    public HWID() {
        HWID.guiMainMenu = new GuiMainMenu();
    }
    
    public static byte[] MaKeMeMes() {
        try {
            final MessageDigest hash = MessageDigest.getInstance("MD5");
            final StringBuilder s = new StringBuilder();
            s.append(System.getProperty("os.name"));
            s.append(System.getProperty("os.arch"));
            s.append(System.getProperty("os.version"));
            s.append(Runtime.getRuntime().availableProcessors());
            s.append(System.getenv("PROCESSOR_IDENTIFIER"));
            s.append(System.getenv("PROCESSOR_ARCHITECTURE"));
            s.append(System.getenv("PROCESSOR_ARCHITEW6432"));
            s.append(System.getenv("NUMBER_OF_PROCESSORS"));
            return hash.digest(s.toString().getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error("Algorithm wasn't found.", e);
        }
    }
    
    public static String MagicMemes(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            final int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HWID.hexArray[v >>> 4];
            hexChars[j * 2 + 1] = HWID.hexArray[v & 0xF];
        }
        return new String(hexChars);
    }
    
    @Override
    public void initGui() {
        HWID.Status = ChatFormatting.GRAY + "Login...";
        final int var3 = HWID.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, HWID.width / 2 - 100, HWID.height / 3 + 110, "Login"));
        this.buttonList.add(new GuiButton(1, HWID.width / 2 - 100, HWID.height / 3 + 140, "Exit Game"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, HWID.width / 2 - 100, 170, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, HWID.width / 2 - 100, 210, 200, 20);
        this.username.setText("");
        this.password.setText("");
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.shutdown();
                break;
            }
            case 0: {
                if (this.CheckCreds(this.username.getText(), this.password.getText())) {
                    System.out.println("Login Confirmed");
                    this.mc.displayGuiScreen(HWID.guiMainMenu);
                    this.onGuiClosed();
                    break;
                }
                break;
            }
        }
    }
    
    private boolean CheckCreds(final String username, final String password) {
        boolean found = false;
        final String F = MagicMemes(MaKeMeMes());
        try {
            final URL url = new URL("https://pastebin.com/raw/K4e7fr8U");
            try {
                HWID.scanner = new Scanner(url.openStream());
                while (HWID.scanner.hasNextLine() && !found) {
                    final String creds = HWID.scanner.nextLine();
                    if (creds.contains(":")) {
                        final String[] args = creds.split(":");
                        if (username.equals(args[0])) {
                            System.out.println("Confirmed username, " + username);
                            HWID.Status = "Confirmed username, " + username;
                            if (password.equals(args[1])) {
                                System.out.println("Confirmed password, " + password);
                                HWID.Status = "Confirmed password, " + password;
                                if (F.equals(args[2])) {
                                    System.out.println("Setting user, " + username);
                                    found = true;
                                    System.out.println("Confirmed HWID, Username, and Password | User: Niceto");
                                    HWID.Status = ChatFormatting.GREEN + "Confirmed HWID, Username, and Password | User: Niceto";
                                    this.username.setText("");
                                    this.password.setText("");
                                }
                                else {
                                    HWID.Status = ChatFormatting.DARK_RED + "Your hardware ID is Inauthorized.";
                                    found = true;
                                }
                            }
                            else {
                                HWID.Status = ChatFormatting.DARK_RED + "Incorrect Password";
                                found = true;
                            }
                        }
                        else {
                            HWID.Status = ChatFormatting.DARK_RED + "Incorrect Username";
                            found = true;
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
        return found;
    }
    
    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        super.drawScreen(x2, y2, z2);
        GL11.glPushMatrix();
        GL11.glScaled(2.0, 2.0, 1.0);
        GL11.glPopMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Render2DUtils.rectangleBordered(HWID.width / 2 - 150, 100.0, HWID.width / 2 - 100 + 250, HWID.height / 4 + 24 + 72 + 12 + 72 + 25, 5.0, -1879048192, -1728053248);
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.mc.fontRendererObj.drawCenteredStringWithShadow("Artemis Beta Login - Log in with your Username & Password", (float)(HWID.width / 2), 135.0f, -1);
        this.mc.fontRendererObj.drawCenteredString(HWID.Status, (float)(HWID.width / 2), 115.0f, -1);
        this.mc.fontRendererObj.drawCenteredStringWithShadow("Made by G8LOL, Bomt, and Raxu", (float)(HWID.width / 2), (float)(HWID.height - 10), -1);
        if (this.username.getText().isEmpty()) {
            this.mc.fontRendererObj.drawString("Username", HWID.width / 2 - 96, 176.0, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.mc.fontRendererObj.drawString("Password", HWID.width / 2 - 96, 216.0, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
    
    public static String getHWID() {
        try {
            final String command = "wmic csproduct get UUID";
            final Process sNumProcess = Runtime.getRuntime().exec(command);
            final BufferedReader sNumReader = new BufferedReader(new InputStreamReader(sNumProcess.getInputStream()));
            String hwid;
            while ((hwid = sNumReader.readLine()) != null && !hwid.matches(".*[0123456789].*")) {}
            return hwid;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    static {
        HWID.Status = ChatFormatting.GRAY + "Idle...";
        hexArray = "0123456789ABCDEF".toCharArray();
    }
}
