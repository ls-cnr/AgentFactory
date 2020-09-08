/*
 * activityDiagram.java
 *
 * Created on 14 settembre 2002, 17.24
 */

package org.af1_0.gui;

import java.util.Observer; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;

import org.af1_0.entity.*;
import org.af1_0.gui.uml.*;

/**
 *
 * @author  Luca
 */
public class activityDiagram extends javax.swing.JPanel {
    protected final static int Xdist = 0;
    protected final static int Ydist = 40;
    protected java_class agent = null;
    protected LinkedList swimlane_list = null;
    protected LinkedList activity_list = null;
    protected LinkedList arrow_list = null;
    protected List relation_list = null;
    
    /** Creates new form activityDiagram */
    public activityDiagram() {
        initComponents();
    }
    
    public void clear() {
        swimlane_list = null;
        activity_list = null;
        arrow_list = null;
        relation_list = null;
        agent = null;
        
        this.revalidate();
        this.repaint();
    }        
    
    public void setRelations(List list) {
        relation_list = list;
    }
    
    public void setAgent(java_class agent_to_draw) {
        Iterator it;
        umlSwimlane swimlane;
        umlActivity activity;
        Hashtable tabella_activity = new Hashtable();
        int Xoffset = 0;
        int Yoffset = 0;
        int maxH = 0;
        
        agent = agent_to_draw;
        if (agent == null)
            return;
        
        swimlane_list = new LinkedList();
        activity_list = new LinkedList();
        arrow_list = new LinkedList();
        
        swimlane = new umlSwimlane(agent.getName(),"<<agent>>");
        swimlane_list.add(swimlane);
        swimlane.setX(Xoffset);
        swimlane.setY(Yoffset);
        
        Yoffset = swimlane.getHeight();
        it = agent.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            activity = new umlActivity(method.getIDString());
            activity_list.add(activity);
            activity.setName(method.getName());
            
            swimlane.addActivity(activity);
            activity.setY(Yoffset);
            Yoffset += Ydist + activity.getHeight();
            
            tabella_activity.put(method.getIDString(),activity);
        }
        
        maxH = Yoffset;
        swimlane.adjustXPositions();
        Xoffset += Xdist + swimlane.getWidth();
        
        it = agent.getInnerClassList();
        
        while (it.hasNext()) {
            java_class task = (java_class) it.next();
            
            Yoffset = 0;
            swimlane = new umlSwimlane(task.getName(),"<<task>>");
            swimlane_list.add(swimlane);
            swimlane.setX(Xoffset);
            swimlane.setY(Yoffset);
            
            Yoffset = swimlane.getHeight();
            Iterator i = task.getMethodList();
            while (i.hasNext()) {
                java_method method = (java_method) i.next();
                activity = new umlActivity(method.getIDString());
                activity_list.add(activity);
                activity.setName(method.getName());

                swimlane.addActivity(activity);
                activity.setY(Yoffset);
                Yoffset += Ydist + activity.getHeight();
                
                tabella_activity.put(method.getIDString(),activity);
            }
            swimlane.adjustXPositions();
            Xoffset += Xdist + swimlane.getWidth();
            maxH = Math.max (maxH,Yoffset);
        }
        
        // imposta l'array di freccie
        ClipRect start,end;
               
        Iterator it2 = relation_list.iterator();
        while (it2.hasNext()) {
            relation rel = (relation) it2.next();
            umlActivity key1 = (umlActivity) tabella_activity.get(rel.getStartMethodID());
            umlActivity key2 = (umlActivity) tabella_activity.get(rel.getEndMethodID());            
            
            if (key1 != null && key2 != null) {
                start = new ClipRect(key1.getX(),key1.getY(),key1.getWidth(),key1.getHeight());
                start.setExt(key2.getX()+key2.getWidth()/2, key2.getY()+key2.getHeight()/2);
                end = new ClipRect(key2.getX(),key2.getY(),key2.getWidth(),key2.getHeight());
                end.setExt(key1.getX()+key1.getWidth()/2, key1.getY()+key1.getHeight()/2);
                arrow_list.add( new Arrow( start.getX(),start.getY(),end.getX(),end.getY(),Arrow.SIMPLE) );
            }
        }
            
        this.setPreferredSize(new Dimension( Xoffset, maxH ) );
        this.revalidate();
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Iterator it;
        int Xoffset = 0;
        
        if (agent!=null) {
            it = swimlane_list.listIterator();
            while (it.hasNext()) {
                umlSwimlane swimlane = (umlSwimlane) it.next();
                swimlane.paint(g);
                g.setColor(umlActivity.border);
                g.drawLine(Xoffset,0,Xoffset,600);
                Xoffset += swimlane.getWidth();
            }
            g.setColor(umlActivity.border);    
            g.drawLine(Xoffset,0,Xoffset,600);
            
            it = activity_list.listIterator();
            while (it.hasNext()) {
                umlActivity activity = (umlActivity) it.next();
                activity.paint(g);
            }
        }
        if (arrow_list != null) {
            it = arrow_list.listIterator();
            while (it.hasNext()) {
                Arrow freccia = (Arrow) it.next();
                freccia.paint(g);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        
        setLayout(new java.awt.BorderLayout());
        
    }//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
