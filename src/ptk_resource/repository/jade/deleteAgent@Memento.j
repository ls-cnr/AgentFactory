try {
//Story mode
	if(storyActivated){
		List savedSnapshots = getSavedSnapshots();
		DeleteAgent deleteAct = new DeleteAgent();
		deleteAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
		ContainerID where = new ContainerID(getContainerController().getContainerName() , null);
		deleteAct.setWhere(where);
		Action a = new Action();
		a.setActor(getAMS());
		
		for(int i = 0; i < savedSnapshots.size(); i++){
			deleteAct.setAgent(new AID((String)savedSnapshots.get(i),AID.ISLOCALNAME));
			a.setAction(deleteAct);
			ACLMessage requestMsg = getRequest();
			requestMsg.setOntology(PersistenceVocabulary.NAME);
			getContentManager().fillContent(requestMsg, a);
			addBehaviour(new MyAMSClientBehaviour(this,"DeleteAgent", requestMsg,false));
		}
	}
	else{//Normal mode
		DeleteAgent deleteAct = new DeleteAgent();
		deleteAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
		ContainerID where = new ContainerID(getContainerController().getContainerName() , null);
		deleteAct.setWhere(where);
		Action a = new Action();
		a.setActor(getAMS());
		deleteAct.setAgent(getAID());
		a.setAction(deleteAct);
		ACLMessage requestMsg = getRequest();
		requestMsg.setOntology(PersistenceVocabulary.NAME);
		getContentManager().fillContent(requestMsg, a);
		addBehaviour(new MyAMSClientBehaviour(this,"DeleteAgent", requestMsg,false));
	}
}catch (Exception fe) {
	fe.printStackTrace();
}