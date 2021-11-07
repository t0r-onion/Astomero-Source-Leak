package com.initial.utils.niggers;

import java.util.*;

public class NameGenerator
{
    private static final int diffBetweenAtoZ = 25;
    private static final int charValueOfa = 97;
    private String lastGeneratedName;
    int length;
    char[] vowels;
    
    public NameGenerator(int lengthOfName) {
        this.lastGeneratedName = "";
        this.vowels = new char[] { 'a', 'e', 'i', 'o', 'u' };
        if (lengthOfName < 5 || lengthOfName > 10) {
            System.out.println("Setting default length to 7");
            lengthOfName = 7;
        }
        this.length = lengthOfName;
    }
    
    public String getName() {
        String currentGeneratedName;
        do {
            final Random randomNumberGenerator = new Random(Calendar.getInstance().getTimeInMillis());
            final char[] nameInCharArray = new char[this.length];
            for (int i = 0; i < this.length; ++i) {
                if (this.positionIsOdd(i)) {
                    nameInCharArray[i] = this.getVowel(randomNumberGenerator);
                }
                else {
                    nameInCharArray[i] = this.getConsonant(randomNumberGenerator);
                }
            }
            nameInCharArray[0] = Character.toUpperCase(nameInCharArray[0]);
            currentGeneratedName = new String(nameInCharArray);
        } while (currentGeneratedName.equals(this.lastGeneratedName));
        return this.lastGeneratedName = currentGeneratedName;
    }
    
    private boolean positionIsOdd(final int i) {
        return i % 2 == 0;
    }
    
    private char getConsonant(final Random randomNumberGenerator) {
        char currentCharacter;
        while (true) {
            currentCharacter = (char)(randomNumberGenerator.nextInt(25) + 97);
            if (currentCharacter != 'a' && currentCharacter != 'e' && currentCharacter != 'i' && currentCharacter != 'o') {
                if (currentCharacter == 'u') {
                    continue;
                }
                break;
            }
        }
        return currentCharacter;
    }
    
    private char getVowel(final Random randomNumberGenerator) {
        return this.vowels[randomNumberGenerator.nextInt(this.vowels.length)];
    }
}
