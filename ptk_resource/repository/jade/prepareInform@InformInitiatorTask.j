// This method must return the vector of ACLMessage objects to be sent.
			// It is called in the first state of this protocol.
			// This default implementation just returns the ACLMessage object passed in the constructor.
			msg.setPerformative(ACLMessage.INFORM);
			msg.setProtocol("inform-protocol");
			msg.setSender(myAgent.getAID());
			msg.addReceiver(inform_receiver);
			msg.setContent(content);