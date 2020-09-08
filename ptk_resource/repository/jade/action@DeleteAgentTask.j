try {
DeleteAgent deleteAct = new DeleteAgent();
deleteAct.setAgent(myAgent.getAID());
deleteAct.setRepository(jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY);
ContainerID where = new ContainerID(myAgent.getContainerController().getContainerName() , null);
deleteAct.setWhere(where);
Action a = new Action();
a.setActor(getAMS());
a.setAction(deleteAct);
ACLMessage requestMsg = getRequest();
requestMsg.setOntology(PersistenceVocabulary.NAME);
getContentManager().fillContent(requestMsg, a);
addBehaviour(new MyAMSClientBehaviour(myAgent,"DeleteAgent", requestMsg));
}catch (Exception fe) {
fe.printStackTrace();
}