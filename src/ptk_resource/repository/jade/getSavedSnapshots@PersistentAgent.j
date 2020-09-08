List istances = new LinkedList();
String[] savedAgents = getSavedAgents();
if((savedAgents != null) && (savedAgents.length > 0)){
	String mySelf = getLocalName();
	for(int i = 0; i < savedAgents.length; i++)
		if(savedAgents[i].startsWith(mySelf + "_"))
			istances.add((savedAgents[i].split("@"))[0]);
}
return(istances);