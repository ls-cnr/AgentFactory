/*
 * IncorrectEnityType.java
 *
 * Created on 23 novembre 2002, 9.22
 */

package org.af1_0.gui.command;

/**
 *
 * @author  Luca
 */
public class IncorrectEnityType extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>IncorrectEnityType</code> without detail message.
     */
    public IncorrectEnityType() {
    }
    
    
    /**
     * Constructs an instance of <code>IncorrectEnityType</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IncorrectEnityType(String msg) {
        super(msg);
    }
}
