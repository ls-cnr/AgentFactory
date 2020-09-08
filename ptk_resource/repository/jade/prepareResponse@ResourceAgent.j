// This method is called when the initiator's message is received that matches the message template passed in the constructor.
			// The request parameter is the received message
			// Insert reply user code here (use sendX method to determine the performative act, complete the message with a content if needed)
			
			ACLMessage response = request.createReply();
			sendAgree(response);
			return response;