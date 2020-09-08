if (preparing_data==false) {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent( getData() );
				getDataStore().put(parent.RESULT_NOTIFICATION_KEY, msg );
				sending_data = false;
			}