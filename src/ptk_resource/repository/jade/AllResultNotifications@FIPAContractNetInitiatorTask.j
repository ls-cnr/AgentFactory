Iterator iter = allResultNotifications.iterator();
			while (iter.hasNext()) {
				ACLMessage element = (ACLMessage) iter.next();
				if (element.getPerformative() == ACLMessage.INFORM) {
					// Insert HERE the code for the behaviour on INFORM
				} else {
					// Insert HERE the code for the behaviour on FAILURE
				}
			}