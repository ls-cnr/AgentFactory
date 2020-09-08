// This method must return the vector of ACLMessage objects to be sent.
			// It is called in the first state of this protocol.
			// This default implementation just returns the ACLMessage object passed in the constructor.
			msg.setPerformative(ACLMessage.REQUEST);
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			msg.setSender(myAgent.getAID());
			msg.addReceiver(agent_to_request);
			msg.setContent(request_content);
			
			Vector l = new Vector();
			l.addElement(msg);
			
			return l;