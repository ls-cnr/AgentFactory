/*
 * Angle.java
 *
 * Created on 18 settembre 2002, 9.30
 */

package org.af1_0.gui.uml;

/**
 *
 * @author  Luca
 */
public class Angle {
    private static final double due_pi = Math.PI * 2.0;
    protected double angle;     // angolo espresso in radianti sempre compreso tra 0 e 2*PI
    
    /** Creates new Angle */
    public Angle() {
        angle = 0.0;
    }
    public Angle(double value, boolean isDegree) {
        if (isDegree)
            setDegrees(value);
        else
            setRadians(value);
    }
    
    /** set methods */
    public void setDegrees(double value) {
        angle = Math.toRadians(value);
        adatta();
    }
    public void setRadians(double value) {
        angle = value;
        adatta();
    }
    
    /** get methods */
    public double doubleDegrees() {
        return Math.toDegrees(angle);
    }
    public long longDegrees() {
        return Math.round( Math.toDegrees(angle) );
    }
    public double doubleRadians() {
        return angle;
    }
    public long longRadians() {
        return Math.round( angle );
    }
    
    /** math operations methods */
    public void addDegrees(double value) {
        angle += Math.toRadians(value);
        adatta();
    }
    public void addRadians(double value) {
        angle += value;
        adatta();
    }
    public void addAngle(Angle value) {
        angle += value.doubleRadians();
        adatta();
    }
    public void subDegrees(double value) {
        angle -= Math.toRadians(value);
        adatta();
    }
    public void subRadians(double value) {
        angle -= value;
        adatta();
    }
    public void subAngle(Angle value) {
        angle -= value.doubleRadians();
        adatta();
    }
    
    /** other methods */
    public java.lang.String toString() {
        //return "<"+angle+">";
        return "<"+longDegrees()+">";
    }
    
    public void atan(double deltaX,double deltaY) {
        if ( Math.abs(deltaX) > Math.abs(deltaY) ) {
            double rapp = deltaY / deltaX;
            
            if (deltaX >= 0) {
                // da -45 a 45 gradi
                angle = Math.atan(rapp);
                
                adatta();
                
            } else {
                // da 135 a 225 gradi
                angle = Math.PI + Math.atan(rapp);
                
            }
            
        } else {
            double rapp = deltaX / deltaY;
            
            if (deltaY >= 0) {
                // da 45 a 135 gradi
                angle = (Math.PI / 2.0) - Math.atan(rapp);
                
            } else {
                // da 225 a 315 gradi
                angle = (3.0 * Math.PI / 2.0) - Math.atan(rapp);
                
            }
            
        }
    }
    
    /** math operations with two operators methods */
    public Angle min_differenceRadians(double value) {
        double minor = Math.min(angle,value);
        double major = Math.max(angle,value);
        double left_dist = major - minor;
        double rigth_dist = due_pi - major+minor;
        return new Angle(Math.min(left_dist,rigth_dist),false);
    }
    public Angle min_differenceDegrees(double value) {
        double minor = Math.min( angle,Math.toRadians(value) );
        double major = Math.max( angle,Math.toRadians(value) );
        double left_dist = major - minor;
        double rigth_dist = due_pi - major+minor;
        return new Angle(Math.min(left_dist,rigth_dist),false);
    }
    public Angle min_differenceAngle(Angle value) {
        double minor = Math.min( angle,value.doubleRadians() );
        double major = Math.max( angle,value.doubleRadians() );
        double left_dist = major - minor;
        double rigth_dist = due_pi - major+minor;
        return new Angle(Math.min(left_dist,rigth_dist),false);
    }
    
    
    public Angle anti_differenceRadians(double value) {
        if (angle < value)
            return new Angle(due_pi - value + angle,false);
        else
            return new Angle(value - angle,false);
    }
    public Angle anti_differenceDegrees(double value) {
        if (angle < Math.toRadians(value))
            return new Angle(due_pi - Math.toRadians(value) + angle,false);
        else
            return new Angle(Math.toRadians(value) - angle,false);
    }
    public Angle anti_differenceAngle(Angle value) {
        if (angle < value.doubleRadians()) {
            return new Angle(due_pi + value.doubleRadians() - angle,false);
        } else {
            return new Angle(angle-value.doubleRadians(),false);
        }
    }
    
    public Angle differenceRadians(double value) {
        if (angle < value)
            return new Angle(value-angle,false);
        else
            return new Angle(due_pi - value + angle,false);
    }
    public Angle differenceDegrees(double value) {
        if (angle < Math.toRadians(value))
            return new Angle(Math.toRadians(value)-angle,false);
        else
            return new Angle(due_pi - Math.toRadians(value) + angle,false);
    }
    public Angle differenceAngle(Angle value) {
        if (angle < value.doubleRadians())
            return new Angle(angle - value.doubleRadians(),false);
        else
            return new Angle(due_pi - value.doubleRadians() + angle,false);
    }
    
    private void adatta() {
        while (angle < 0)
            angle += due_pi;
        
        while (angle >= due_pi)
            angle -= due_pi;
    }
    
    public static long rotate(long A, long B) {
        long oraria;
        long antioraria;
        
        // calcolo la differenza oraria
        if ( A > B ) {
            oraria = A - B;
        } else {
            oraria = A + (360 - B);
        }
        
        antioraria = (360-oraria);
        
        if (oraria < antioraria)
            return ( oraria  );
        else
            return ( - antioraria ) ;
    }
    
    public static void main(String argv[]) {
        int A = 350;
        int B = 15;
        
        System.out.println("rotazione = " + Angle.rotate( A,B ) );
        
    }
    
}