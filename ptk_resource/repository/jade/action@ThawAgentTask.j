try {
ThawAgent thawAct = new ThawAgent();
thawAct.setAgent(myAgent.getAID());
thawAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
ContainerID where = new ContainerID(myAgent.getContainerController().getContainerName() , null);
thawAct.setNewContainer(where);
Action a = new Action();
a.setActor(getAMS());
a.setAction(thawAct);
ACLMessage requestMsg = getRequest();
requestMsg.setOntology(PersistenceVocabulary.NAME);
getContentManager().fillContent(requestMsg, a);
addBehaviour(new MyAMSClientBehaviour(myAgent,"ThawAgent", requestMsg));
}catch (Exception fe) {
fe.printStackTrace();
}