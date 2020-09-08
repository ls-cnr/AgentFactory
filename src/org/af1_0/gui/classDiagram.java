/*
 * classDiagram.java
 *
 * Created on 14 settembre 2002, 17.23
 */

package org.af1_0.gui;

import java.util.Observer; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import java.util.Iterator;

import org.af1_0.entity.*;
import org.af1_0.gui.uml.*;

/**
 *
 * @author  Luca
 */
public class classDiagram extends javax.swing.JPanel {
    protected final static int Xdist = 20;
    protected final static int Ydist = 40;
    
    protected umlClass AgentShell;
    protected umlClass TaskShell;
    
    protected java_class agent = null;
    protected umlClass agent_class = null;
    protected Vector tasks_class = null;
    protected Vector arrow_list = null;
    
    /** Creates new form classDiagram */
    public classDiagram() {
        initComponents();
        
        
        java_class agentShell = new java_class();
        agentShell.setName( "AgentShell" );
        java_class taskShell = new java_class();
        taskShell.setName( "TaskShell" );
        
        AgentShell = new umlClass( agentShell );
        TaskShell = new umlClass( taskShell );
    }
    
    public void clear() {
        Vector tasks_class = null;
        Vector arrow_list = null;
        agent = null;
        
        this.revalidate();
        this.repaint();
    }        
    
    public void setAgent(java_class agent_to_draw) {
        int Yoffset = 30;
        int Xoffset = 30;
        int width=Xdist;
        int height = 0;
        
        agent = agent_to_draw;
        tasks_class = new Vector();
        
        if (agent != null) {
            // POSIZIONA LE CLASSI
            // strategia: prima posiziona solo verticalmente le classi AgentShell, Agent e Task Shell
            // poi posiziona interamente le classi Tasks e calcola la dimensione orizontale
            // quindi posiziona orizontalmente al centro anche le restanti classi

            AgentShell.setY(Yoffset);
            Yoffset += Ydist + AgentShell.getHeight();

            agent_class = new umlClass(agent);
            agent_class.setY(Yoffset);
            Yoffset += Ydist + agent_class.getHeight();

            TaskShell.setY(Yoffset);
            Yoffset += Ydist + TaskShell.getHeight();

            Iterator it = agent.getInnerClassList();
            while (it.hasNext()) {
                java_class task = (java_class) it.next();
                umlClass umlclass = new umlClass(task);
                tasks_class.add( umlclass );
                umlclass.setX(width);
                umlclass.setY(Yoffset);
                width += Xdist + umlclass.getWidth();
                height = Math.max(height,umlclass.getHeight());
            }

            height += Yoffset + Ydist;
            int center = width/2;
            int mezz;

            mezz =  agent_class.getWidth()/2;
            agent_class.setX(Math.max(center - mezz,Xdist));

            mezz =  AgentShell.getWidth()/2;
            AgentShell.setX(Math.max(center - mezz,Xdist));

            mezz =  TaskShell.getWidth()/2;
            TaskShell.setX(Math.max(center - mezz,Xdist));

            setArrows();
        }
        
        this.setPreferredSize(new Dimension( width, height ) );
        this.revalidate();
        this.repaint();
    }
    
    private void setArrows() {
        arrow_list = new Vector();
        
        ClipRect start,end;
        int x,y,w,h;
        
        start = new ClipRect(agent_class.getX(),agent_class.getY(),agent_class.getWidth(),agent_class.getHeight());
        start.setExt(AgentShell.getX()+AgentShell.getWidth()/2, AgentShell.getY()+AgentShell.getHeight()/2);
        end = new ClipRect(AgentShell.getX(),AgentShell.getY(),AgentShell.getWidth(),AgentShell.getHeight());
        end.setExt(agent_class.getX()+agent_class.getWidth()/2, agent_class.getY()+agent_class.getHeight()/2);
        
        arrow_list.add( new Arrow( start.getX(),start.getY(),end.getX(),end.getY(),Arrow.EXTENDS) );
        
        Iterator it = tasks_class.iterator();
        while (it.hasNext()) {
            umlClass aclass = (umlClass) it.next();
            
            start = new ClipRect(aclass.getX(),aclass.getY(),aclass.getWidth(),aclass.getHeight());
            start.setExt(TaskShell.getX()+TaskShell.getWidth()/2, TaskShell.getY()+TaskShell.getHeight()/2);
            end = new ClipRect(TaskShell.getX(),TaskShell.getY(),TaskShell.getWidth(),TaskShell.getHeight());
            end.setExt(aclass.getX()+aclass.getWidth()/2, aclass.getY()+aclass.getHeight()/2);

            arrow_list.add( new Arrow( start.getX(),start.getY(),end.getX(),end.getY(),Arrow.EXTENDS) );
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Iterator it;
        
        if (agent!=null) {
            AgentShell.paint(g);
            agent_class.paint(g);
            TaskShell.paint(g);
            if (tasks_class != null) {
                it = tasks_class.iterator();
                while (it.hasNext()) {
                    umlClass unaclasse = (umlClass) it.next();
                    unaclasse.paint(g);
                }
            }
            if (arrow_list != null) {
                it = arrow_list.iterator();
                while (it.hasNext()) {
                    Arrow freccia = (Arrow) it.next();
                    freccia.paint(g);
                }
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
