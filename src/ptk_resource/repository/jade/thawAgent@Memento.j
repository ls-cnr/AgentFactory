try {
	ThawAgent thawAct = new ThawAgent();
	thawAct.setAgent(getAID());
	thawAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
	ContainerID where = new ContainerID(getContainerController().getContainerName() , null);
	thawAct.setNewContainer(where);
	Action a = new Action();
	a.setActor(getAMS());
	a.setAction(thawAct);
	ACLMessage requestMsg = getRequest();
	requestMsg.setOntology(PersistenceVocabulary.NAME);
	getContentManager().fillContent(requestMsg, a);
	addBehaviour(new MyAMSClientBehaviour(this,"ThawAgent", requestMsg,false));
}catch (Exception fe) {
	fe.printStackTrace();
}