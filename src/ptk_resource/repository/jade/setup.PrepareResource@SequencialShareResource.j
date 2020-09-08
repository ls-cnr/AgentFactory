// HERE insert the code for prepare data
                
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                		msg.setContent( getData() );
                		getDataStore().put(parent.RESULT_NOTIFICATION_KEY, msg );