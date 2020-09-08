/*
 * umlSwimlane.java
 *
 * Created on 18 settembre 2002, 14.41
 */

package org.af1_0.gui.uml;

import java.util.Vector;
import java.util.Iterator;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author  Luca
 */
public class umlSwimlane extends Component {
    private static final int Xdist = 10;
    private static final int Ydist = 10;

    private Multiline name = null;
    private Vector activity_list = null;
    
    protected int x,y;
    protected int width,height;

    /** Creates a new instance of umlSwimlane */
    public umlSwimlane(String class_name,String stereotype) {
        name = new Multiline();
        activity_list = new Vector();
        name.add(class_name);
        name.add(stereotype);
        
        recalculate();
    }
    
    public void addActivity(umlActivity activity) {
        activity_list.add( activity );
        recalculate();
    }
    
    public void adjustXPositions() {
        int center = width / 2;
        
        name.setX(x+center-name.getWidth()/2);
        Iterator it = activity_list.iterator();
        while (it.hasNext()) {
            umlActivity activity = (umlActivity) it.next();
            activity.setX(x+center-activity.getWidth()/2);
        }
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
    
    public void paint(Graphics g) {
        g.setColor(umlActivity.font);
        name.paint(g);
    }
    
    // calcola la width e length
    private void recalculate() {
        // width
        width = name.getWidth();
        
        Iterator it = activity_list.iterator();
        while (it.hasNext()) {
            umlActivity activity = (umlActivity) it.next();
            width = Math.max( width, activity.getWidth() );
        }
        width += 2*Xdist;
        
        // height
        height = 2*Ydist + name.getHeight();
    }
    
}
