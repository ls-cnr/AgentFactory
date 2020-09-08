/*
 * ClipRect.java
 *
 * Created on 18 settembre 2002, 9.37
 */

package org.af1_0.gui.uml;

/**
 *
 * @author  Luca
 */
public class ClipRect {
    int x_left;
    int y_top;
    int x_right;
    int y_bottom;
    
    int Ex,Ey;
    
    /** Creates a new instance of ClipRect */
    public ClipRect(int x,int y,int wx,int wy) {
        x_left = x;
        x_right = x+wx;
        y_top = y;
        y_bottom = y+wy;
    }
    
    public void setExt(int Ext_x,int Ext_y) {
        if (isUpper(Ext_y)) {            
            Ext_x = yClip(Ext_x,Ext_y,y_top);
            Ext_y = y_top;
            //System.out.println("upper X="+Ext_x+" Y="+Ext_y);
            
            if (isInside(Ext_x,Ext_y)) {
                Ex = Ext_x;
                Ey = Ext_y;
                return ;
            }
        }
        if (isLeft(Ext_x)) {
            Ext_y = xClip(Ext_x,Ext_y,x_left);
            Ext_x = x_left;
            //System.out.println("left X="+Ext_x+" Y="+Ext_y); 
            
            if (isInside(Ext_x,Ext_y)) {
                Ex = Ext_x;
                Ey = Ext_y;
                return ;
            }
        }
        if (isBottom(Ext_y)) {
            Ext_x = yClip(Ext_x,Ext_y,y_bottom);
            Ext_y = y_bottom;
            //System.out.println("bottom X="+Ext_x+" Y="+Ext_y);
            
            if (isInside(Ext_x,Ext_y)) {
                Ex = Ext_x;
                Ey = Ext_y;
                return ;
            }
        }
        if (isRight(Ext_x)) {
            Ext_y = xClip(Ext_x,Ext_y,x_right);
            Ext_x = x_right;
            //System.out.println("right X="+Ext_x+" Y="+Ext_y);
            
            if (isInside(Ext_x,Ext_y)) {
                Ex = Ext_x;
                Ey = Ext_y;
                return ;
            }
        }
    }
    
    // trova il punto di intersezione tra una retta e la retta y=y_clip
    private int yClip(int ex,int ey, int y_clip) {
        int cx = x_left + (x_right - x_left)/2;
        int cy = y_top + (y_bottom - y_top)/2;
        double dx = ex - cx;
        double dy = ey - cy;
        double t = dx / dy;
        double y_diff = y_clip - ey;
        double result = t * y_diff + ex;
        return (int) Math.round(result);
    }
    // trova il punto di intersezione tra una retta e la retta x=x_clip
    private int xClip(int ex,int ey, int x_clip) {
        int cx = x_left + (x_right - x_left)/2;
        int cy = y_top + (y_bottom - y_top)/2;
        double dx = ex - cx;
        double dy = ey - cy;
        double m = dy/dx;
        double x_diff = x_clip - ex;
        double result = m * x_diff + ey;
        return (int) Math.round(result);
    }
    
    private boolean isInside(int x,int y) {
        if (!isUpper(y) && !isBottom(y) && !isLeft(x) && !isRight(x))
            return true;
        return false;
    }

    private boolean isUpper(int y) {
        return y < y_top;
    }
    
    private boolean isBottom(int y) {
        return y > y_bottom;
    }

    private boolean isLeft(int x) {
        return x < x_left;
    }
    
    private boolean isRight(int x) {
        return x > x_right;
    }
    
    public int getX() {
        return Ex;
    }
    
    public int getY() {
        return Ey;
    }
    
    public static void main(String args[]) {
        ClipRect clip_rect = new ClipRect(10,10,20,20);
        clip_rect.setExt(0,50);
        System.out.println("X="+clip_rect.getX()+" Y="+clip_rect.getY());
    }
    
}
