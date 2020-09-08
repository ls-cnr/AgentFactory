super(agent_ref);

			MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate m2 = MessageTemplate.MatchProtocol("inform-protocol");
			
			setMessageTemplate( MessageTemplate.and(m1,m2) );