/*
 * Multiline.java
 *
 * Created on 17 settembre 2002, 17.28
 */

package org.af1_0.gui.uml;

import java.util.Vector;
import java.util.Iterator;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Component;
import java.awt.FontMetrics;

/**
 *
 * @author  Luca
 */
public class Multiline extends Component {
    protected static Font font = new Font("TimesRoman",Font.PLAIN,14);
    protected Vector lines = null;
    protected int x,y;
    
    /** Creates a new instance of Multiline */
    public Multiline() {
        lines = new Vector();
        x=0;
        y=0;
    }
    
    public void add(String line) {
        lines.add(line);
    }
    
    public void setX(int x_pos) {
        x = x_pos;
    }
    public void setY(int y_pos) {
        y = y_pos;
    }
    
    public int getHeight() {
        FontMetrics fm = getFontMetrics(font);
        int height = fm.getHeight() * lines.size();
        return height;
    }
    
    public int getWidth() {
        FontMetrics fm = getFontMetrics(font);
        int width = 0;
        Iterator it = lines.iterator();
        
        while (it.hasNext()) {
            String line = (String) it.next();
            width = Math.max(width, fm.stringWidth(line));
        }
        
        return width;
    }
    
    public void paint(Graphics g) {
        FontMetrics metrics = getFontMetrics(font);
        
        int incremento = metrics.getHeight();
        int Yoffset = metrics.getAscent();
        
        Iterator it = lines.iterator();
        
        while (it.hasNext()) {
            String line = (String) it.next();
            g.drawString(line, x,y+Yoffset );
            Yoffset += incremento;
        }
    }
    
    public static void setDrawingFont(Font new_font) {
        font = new_font;
    }
    
}
