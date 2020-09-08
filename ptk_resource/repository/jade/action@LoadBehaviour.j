try{
	LoadAgent loadAct = new LoadAgent();
	loadAct.setAgent(new AID(targetAgent,AID.ISLOCALNAME));
	loadAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
	ContainerID where = new ContainerID(myAgent.getContainerController().getContainerName() , null);
	loadAct.setWhere(where);
	Action a = new Action();
	a.setActor(getAMS());
	a.setAction(loadAct);
	ACLMessage requestMsg = getRequest();
	requestMsg.setOntology(PersistenceVocabulary.NAME);
	getContentManager().fillContent(requestMsg, a);
	addBehaviour(new MyAMSClientBehaviour(myAgent,"LoadAgent", requestMsg,killAfterLoad));
}catch (Exception fe) {
	fe.printStackTrace();
}