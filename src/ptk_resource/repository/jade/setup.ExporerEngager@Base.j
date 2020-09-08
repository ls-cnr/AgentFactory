try {   
				Object[] args = new Object[2];
				args[0]=new String(destination_container);
				args[1]=myAgent.getAID();
				
				AgentContainer myContainer =myAgent.getContainerController();
				
				AgentController agente01 = myContainer.createNewAgent("SendAgent", "AgenteLanciato", args);
				agente01.start();
			} catch (Exception n) {
				n.printStackTrace();
			}