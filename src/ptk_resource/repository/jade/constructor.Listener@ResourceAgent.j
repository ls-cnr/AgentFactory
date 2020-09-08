send_resource = new SendResource(owner);

			MessageTemplate template = AchieveREResponder.createMessageTemplate(FIPAProtocolNames.FIPA_REQUEST);			
			super(owner, template );
			registerPrepareResultNotification( send_resource);