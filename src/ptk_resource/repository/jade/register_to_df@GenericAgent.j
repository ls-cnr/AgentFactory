/* this block enable DF registration */
		try {
			// create the agent description of itself
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			
			// register the description with the DF
			DFService.register(this, dfd);
			
		} catch (FIPAException e) {
			e.printStackTrace();
		}