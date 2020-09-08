if (preparing_data==false) {
				myAgent.sending_data = true;
				
				getDataStore().put( jade.proto.AchieveREInitiator.ALL_RESULT_NOTIFICATIONS_KEY, myAgent.getData() );
				
				myAgent.sending_data = false;
				finish	= true;
			}