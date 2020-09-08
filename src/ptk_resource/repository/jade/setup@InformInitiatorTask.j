ACLMessage msg = new ACLMessage(ACLMessage.NOT_UNDERSTOOD) ;
			prepareInform(msg);
			myAgent.send( msg );