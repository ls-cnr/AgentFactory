/*
 * pattern_description.java
 *
 * Created on 25 ottobre 2002, 17.19
 */

package org.af1_0.pattern;

import java.io.Serializable;
import java.net.URL;

/**
 *
 * @author  Luca
 */
public class pattern_description implements Serializable {
    protected String name;
//    protected String description; = MOTIVATION
//    protected String scenario;	= PARTICIPANT
    protected String xml;
    
	protected String classification;
    protected String intent;
    protected String motivation;	
    protected String pre_condition;
    protected String post_condition;
    protected String participants;
    protected String availability;
    protected String related_patterns;
    
	protected URL structure;
	protected URL collaboration;
	
    /** Creates a new instance of pattern_description */
    public pattern_description(String name) {
        this.name = name;
		structure = null;
		collaboration = null;
    }

	public pattern_description(String name,String description,String scenario,String xml) {
        this.name = name;
        this.motivation = description;
        this.participants = scenario;
        this.xml = xml;
		structure = null;
		collaboration = null;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return motivation;
    }

    public String getScenario() {
        return participants;
    }

	public String getXml() {
        return xml;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.motivation = description;
    }

    public void setScenario(String scenario) {
        this.participants = scenario;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

	
    public String getClassification() {
        return classification;
    }
    public void setClassification(String string) {
        this.classification = string;
    }

    public String getIntent() {
        return intent;
    }
    public void setIntent(String string) {
        this.intent = string;
    }
	
    public String getMotivation() {
        return motivation;
    }
    public void setMotivation(String string) {
        this.motivation = string;
    }

    public String getPreCondition() {
        return pre_condition;
    }
    public void setPreCondition(String string) {
        this.pre_condition = string;
    }

    public String getPostCondition() {
        return post_condition;
    }
    public void setPostCondition(String string) {
        this.post_condition = string;
    }

    public String getParticipants() {
        return participants;
    }
    public void setParticipants(String string) {
        this.participants = string;
    }

    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String string) {
        this.availability = string;
    }
	
    public String getRelatedPatterns() {
        return related_patterns;
    }
    public void setRelatedPatterns(String string) {
        this.related_patterns = string;
    }

    public URL getStructure() {
        return structure;
    }
    public void setStructure(URL icon) {
        this.structure = icon;
    }

    public URL getCollaboration() {
        return collaboration;
    }
    public void setCollaboration(URL icon) {
        this.collaboration = icon;
    }

}
