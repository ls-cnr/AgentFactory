/*
 * Arrow.java
 *
 * Created on 18 settembre 2002, 9.21
 */

package org.af1_0.gui.uml;

import java.awt.Graphics;

/**
 *
 * @author  Luca
 */
public class Arrow {
    public static final int SIMPLE = 0;
    public static final int EXTENDS = 1;
    protected int start_x;
    protected int start_y;
    protected int end_x;
    protected int end_y;
    protected int dx,dy;
    protected int sx,sy;
    
    protected int type;
    
    
    /** Creates a new instance of Arrow */
    public Arrow(int x1,int y1,int x2,int y2) {
        start_x = x1;
        start_y = y1;
        end_x = x2;
        end_y = y2;
        type = SIMPLE;
        computate();
    }
    public Arrow(int x1,int y1,int x2,int y2,int arrow_type) {
        start_x = x1;
        start_y = y1;
        end_x = x2;
        end_y = y2;
        type = arrow_type;
        computate();
    }
    
    protected void computate() {
        Angle angolo = new Angle();
        angolo.atan(start_x-end_x,start_y-end_y);

        double ang1 = angolo.doubleRadians()+0.5;
        double ang2 = angolo.doubleRadians()-0.5;

        dx = end_x + (int) Math.round( 10.0*Math.cos(ang1) );
        dy = end_y + (int) Math.round( 10.0*Math.sin(ang1) );
        sx = end_x + (int) Math.round( 10.0*Math.cos(ang2) );
        sy = end_y + (int) Math.round( 10.0*Math.sin(ang2) );       
    }
    
    public void paint(Graphics g) {
        g.drawLine(start_x,start_y,end_x,end_y);
        if (type == EXTENDS) {
            int xvert[] = new int[3];
            int yvert[] = new int[3];
            xvert[0] = dx;
            xvert[1] = sx;
            xvert[2] = end_x;
            
            yvert[0] = dy;
            yvert[1] = sy;
            yvert[2] = end_y;
            
            g.setColor(umlClass.background);
            g.fillPolygon(xvert,yvert,3);
            g.setColor(umlClass.border);
            g.drawPolygon(xvert,yvert,3);
            
        } else {
            g.drawLine(dx,dy,end_x,end_y);
            g.drawLine(sx,sy,end_x,end_y);
        }
    }    
}
