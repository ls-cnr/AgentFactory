super(owner, AchieveREResponder.createMessageTemplate(FIPAProtocolNames.FIPA_REQUEST));
			registerPrepareResultNotification( new PrepareResource(myAgent,this));