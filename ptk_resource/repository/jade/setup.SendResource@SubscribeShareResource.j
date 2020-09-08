ACLMessage msg = new ACLMessage(ACLMessage.NOT_UNDERSTOOD) ;
			prepareInform(msg);
			myAgent.send( msg );
			instances--;
			if (instances == 0)
				parent.restart();