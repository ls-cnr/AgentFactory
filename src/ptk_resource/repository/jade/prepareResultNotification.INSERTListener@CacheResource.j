ACLMessage reply = request.createReply();
			// Insert reply user code HERE (use sendX method to determine the performative act, complete the message with a content if needed)
			UpdateCache myUpdateCache =
				new UpdateCache(myAgent, request.getContent());
			reply.setPerformative(ACLMessage.INFORM);
			return reply;