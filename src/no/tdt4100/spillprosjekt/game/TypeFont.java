package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Font;
import java.awt.Color;

public class TypeFont {
    private Font awtFont;
    private UnicodeFont uFont;
    private String glyphs = "abcdefghijklmnopqrstuvwxyzæøå0123456789()!?.:";

    public TypeFont(String font, int size, boolean bold, Color color) {
        try {
            if (bold) {
                awtFont = new Font(font, Font.BOLD, size);
            }
            else {
                awtFont = new Font(font, Font.PLAIN, size);
            }
            uFont = new UnicodeFont(awtFont, size, bold, false);
            uFont.getEffects().add(new ColorEffect(color));
            uFont.addGlyphs(glyphs + glyphs.toUpperCase());
            uFont.loadGlyphs();
        } catch (Exception e) {
            Logger.log(e);
        }
    }


    public UnicodeFont getFont() {
        return uFont;
    }
}
