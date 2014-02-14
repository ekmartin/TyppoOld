package no.tdt4100.spillprosjekt.game;

public class AllowedCharacter {

    private char allowedChar;
    private Block block;

    public AllowedCharacter(char allowedChar, Block block) {
        this.allowedChar = allowedChar;
        this.block = block;
    }

    public char getChar() {
        return allowedChar;
    }

    public Block getBlock() {
        return block;
    }
}
