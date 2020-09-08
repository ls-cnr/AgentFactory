// This method is called when the initiator's message is received that matches the message template passed in the constructor.
			// The request parameter is the received message
			ACLMessage response = request.createReply();
			
			if (request.getContent().equals("subscribe")) {
				addAgent(response.getSender());
				sendAgree(response);
			} else if (request.getContent().equals("unsubscribe")) {
				removeAgent(response.getSender());
				sendAgree(response);
			} else {
				sendNotUnderstood(response);
			}
			
			return response;