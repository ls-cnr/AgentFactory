Iterator iter = responses.iterator();
			while (iter.hasNext()) {
				ACLMessage element = (ACLMessage) iter.next();
				if (element.getPerformative() == ACLMessage.PROPOSE) {
					ACLMessage reply = element.createReply();
					// Insert HERE the code to accept or to reject the proposal
					// for the participant agent in the conversation
					// Use sendX to select the reply performative
					acceptances.add(reply);
				}
			}