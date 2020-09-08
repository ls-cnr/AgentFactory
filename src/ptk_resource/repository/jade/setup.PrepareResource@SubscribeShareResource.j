// HERE insert code for data producing
			
			block(1000);
			Iterator it = agents.iterator();
			while(it.hasNext()) {
				AID receiver = (AID) it.next();
				addBehaviour( new SendResource(myAgent,receiver,getData(),this) );
			}