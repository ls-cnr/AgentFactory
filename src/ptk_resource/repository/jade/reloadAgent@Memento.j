if(storyActivated){//Reload last saved istance in story mode
	List savedSnapshots = getSavedSnapshots();
    if(savedSnapshots.size() > 0){
    	String name = (String)savedSnapshots.get(0);
    	String date = ((String)savedSnapshots.get(0)).split("_")[1];
    	String time = ((String)savedSnapshots.get(0)).split("_")[2];
    	java.util.GregorianCalendar lastSnapshot = 
    		new java.util.GregorianCalendar(
    			MyDate.getYear(date),
    			MyDate.getMounth(date)-1,
    			MyDate.getDay(date),
    			MyDate.getHours(time),
    			MyDate.getMins(time),
    			MyDate.getSec(time)
    		);
    												
    	for(int i = 1; i < savedSnapshots.size(); i++){
    		date = ((String)savedSnapshots.get(i)).split("_")[1];
    		time = ((String)savedSnapshots.get(i)).split("_")[2];
    		java.util.GregorianCalendar snapshot = 
	    		new java.util.GregorianCalendar(
	    			MyDate.getYear(date),
	    			MyDate.getMounth(date)-1,
	    			MyDate.getDay(date),
	    			MyDate.getHours(time),
	    			MyDate.getMins(time),
	    			MyDate.getSec(time)
	    		);
    		if(lastSnapshot.before(snapshot)){
    			lastSnapshot = snapshot;
    			name = (String)savedSnapshots.get(i);
    		}
    	}
    	reloadSnapshot(name);
    }
    else
    	System.out.println("--- Agent <" + getName() 
		       		+ "> is not saved in <"
					+ jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY
					+ "> repository - can't reload" + " ---"
		);
}
else{//Reload in normalMode
	boolean find = false;
	String[] savedAgents = getSavedAgents();

	if((savedAgents != null) && (savedAgents.length > 0)){
		String mySelf = getName();
		int i = 0;
		while((i < savedAgents.length) && (!find)){
			if(mySelf.equals(savedAgents[i]))
				find = true;
			i++;
		}
	}
  	
	if(find)
   		addBehaviour(new ReloadBehaviour(this));
   	else
   		System.out.println("--- Agent <" + getName() 
		       		+ "> is not saved in <"
					+ jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY
					+ "> repository - can't reload" + " ---"
		);
}