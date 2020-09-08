/*
 * entity.java
 *
 * Created on 3 novembre 2002, 11.32
 */

package org.af1_0.entity;

/**
 *
 * @author  Luca
 */
public interface entity {
    public boolean isClass();
    public boolean isAttribute();
    public boolean isMethod();
    public boolean isRelation();
    public boolean isAgent();
    public boolean isTask();
}
