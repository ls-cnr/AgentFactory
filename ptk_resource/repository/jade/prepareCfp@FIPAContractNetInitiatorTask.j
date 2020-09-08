cfp.setSender(myAgent.getAID());
			cfp.setProtocol(
				jade.domain.FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			cfp.setPerformative(ACLMessage.CFP);

			Iterator it = participant_agents.iterator();
			while (it.hasNext()) {
				AID aid = (AID) it.next();
				cfp.addReceiver(aid);
			}

			Vector l = new Vector();
			l.addElement(cfp);
			return l;