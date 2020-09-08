ACLMessage response = request.createReply();
			sendAgree(response);
			sending_data = true;
			return response;