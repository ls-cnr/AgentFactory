register_to_df();
		MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPAProtocolNames.FIPA_REQUEST);
		AchieveREResponder listener = new Listener(this, mt);
		addBehaviour(listener);
		addBehaviour( new PrepareResource(this) );