/*
 * umlClass.java
 *
 * Created on 17 settembre 2002, 10.28
 */

package org.af1_0.gui.uml;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.util.Iterator;

import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class umlClass extends Component {
    public static final Color background = Color.lightGray;
    public static final Color border = Color.blue;
    public static final Color font = Color.black;
    private static final int Xdist = 5;
    private static final int Ydist = 5;
    
    private Multiline name = null;
    private Multiline attribute = null;
    private Multiline operation = null;
    
    protected int x,y;
    protected int width,height;
    
    protected int Ydivider1,Ydivider2;

    /** Creates a new instance of umlClass */
    public umlClass(java_class classe) {
        Iterator it;
        int w,h;
        int Yoffset;

        name = new Multiline();
        attribute = new Multiline();
        operation = new Multiline();
        
        name.add( classe.getName() );
        it = classe.getAttributeList();
        while (it.hasNext()) {
            java_attribute attr = (java_attribute) it.next();
            attribute.add( visibility(attr.getVisibility()) + " " + attr.getName());
        }
        it = classe.getMethodList();
        while (it.hasNext()) {
            java_method meth = (java_method) it.next();
            operation.add( visibility(meth.getVisibility()) + " " + meth.getName() + "()");
        }
        
        Yoffset = Ydist;
        name.setX(x+Xdist);
        name.setY(y+Yoffset);
        
        width = name.getWidth();
        h = name.getHeight();
        Ydivider1 = 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        attribute.setX(x+Xdist);
        attribute.setY(y+Yoffset);
        
        width = Math.max(width,attribute.getWidth());
        h = attribute.getHeight();
        Ydivider2 = Ydivider1+ 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        operation.setX(x+Xdist);
        operation.setY(y+Yoffset);

        width = Math.max(width,operation.getWidth());
        h = operation.getHeight();
        height = Yoffset + h + Ydist;
        
        width += Xdist*2;
    }
    
    public void setX(int x_pos) {
        int w,h;
        int Yoffset;
        x = x_pos;
        width = 0;

        Yoffset = Ydist;
        name.setX(x+Xdist);
        name.setY(y+Yoffset);
        
        width = name.getWidth();
        h = name.getHeight();
        Ydivider1 = 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        attribute.setX(x+Xdist);
        attribute.setY(y+Yoffset);
        
        width = Math.max(width,attribute.getWidth());
        h = attribute.getHeight();
        Ydivider2 = Ydivider1+ 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        operation.setX(x+Xdist);
        operation.setY(y+Yoffset);

        width = Math.max(width,operation.getWidth());
        h = operation.getHeight();
        height = Yoffset + h + Ydist;
        
        width += Xdist*2;
    }
    public void setY(int y_pos) {
        int w,h;
        int Yoffset;
        y = y_pos;

        width = 0;
        Yoffset = Ydist;
        name.setX(x+Xdist);
        name.setY(y+Yoffset);
        
        width = name.getWidth();
        h = name.getHeight();
        Ydivider1 = 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        attribute.setX(x+Xdist);
        attribute.setY(y+Yoffset);
        
        width = Math.max(width,attribute.getWidth());
        h = attribute.getHeight();
        Ydivider2 = Ydivider1+ 2*Ydist + h;
        Yoffset += 2*Ydist + h;
        operation.setX(x+Xdist);
        operation.setY(y+Yoffset);

        width = Math.max(width,operation.getWidth());
        h = operation.getHeight();
        height = Yoffset + h + Ydist;
        
        width += Xdist*2;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }  
    
    public void paint(Graphics g) {
        g.setColor(background);
        g.fillRect(x,y,width,height);

        //System.out.println("x: "+x+" y: "+y+" w: "+width+" h: "+height);
		
        g.setColor(font);
        name.paint(g);
        attribute.paint(g);
        operation.paint(g);
        g.drawLine(x,y+Ydivider1,x+width,y+Ydivider1);
        g.drawLine(x,y+Ydivider2,x+width,y+Ydivider2);
        
        g.setColor(border);
        g.drawRect(x,y,width,height);
    }

    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    private String visibility(String visibility) {
        if (visibility.equals("private"))
            return "-";
        else if (visibility.equals("public"))
            return "+";
        
        return "#";
    }
}
