// This method is called after the response has been sent and only if the response was an agree message. 
			ACLMessage reply = request.createReply();
			sendInform(reply);
			reply.setContent( getData() );
			return reply;