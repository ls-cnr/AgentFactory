/*
 * umlActivity.java
 *
 * Created on 18 settembre 2002, 14.14
 */

package org.af1_0.gui.uml;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author  Luca
 */
public class umlActivity extends Component {
    public static final Color background = Color.lightGray;
    public static final Color border = Color.blue;
    public static final Color font = Color.black;
    private static final int Xdist = 10;
    private static final int Ydist = 10;

    private Multiline name = null;
    private String id = null;

    protected int x,y;
    protected int width,height;

    /** Creates a new instance of umlActivity */
    public umlActivity(String id_method) {
        id = id_method;   
        name = new Multiline();
        name.add(id_method);
        
        x=0;
        y=0;
        name.setX(Xdist);
        name.setY(Ydist);
        width = 2*Xdist + name.getWidth();
        height = 2*Ydist + name.getHeight();
    }
    
    public void setName(String activity_name) {
        name = new Multiline();
        name.add(activity_name);
        width = 2*Xdist + name.getWidth();
        height = 2*Ydist + name.getHeight();
    }
    
    public void setX(int x_pos) {
        x = x_pos;
        name.setX(x+Xdist);
    }
    public void setY(int y_pos) {
        y = y_pos;
        name.setY(y+Ydist);
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }  
    
    public void paint(Graphics g) {
        g.setColor(background);
        g.fillRoundRect(x,y,width,height,25,25);
        //g.fillOval(x,y,width,height);
        
        g.setColor(font);
        name.paint(g);

        g.setColor(border);
        g.drawRoundRect(x,y,width,height,25,25);
        //g.drawOval(x,y,width,height);
    }
}
