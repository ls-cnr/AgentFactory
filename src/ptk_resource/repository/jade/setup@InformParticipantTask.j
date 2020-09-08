ACLMessage msg = myAgent.receive(template);
			if (msg != null)
				handleInform( msg );