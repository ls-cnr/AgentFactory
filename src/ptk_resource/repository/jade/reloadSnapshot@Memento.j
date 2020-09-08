if(getSavedSnapshots().contains(snapshotName))
	addBehaviour(new LoadBehaviour(this,snapshotName,true));
else
   	System.out.println("--- Agent <" + snapshotName 
			+ "> is not saved in <"
			+ jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY
			+ "> repository - can't reload" + " ---"
	);