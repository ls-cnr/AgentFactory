	ACLMessage AMSRequest = new ACLMessage(ACLMessage.REQUEST);
	AMSRequest.setSender(myAgent.getAID());
AMSRequest.clearAllReceiver();
AMSRequest.addReceiver(myAgent.getAMS());
AMSRequest.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
AMSRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
return(AMSRequest);