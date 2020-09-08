String destination=null;
		Object[] agentArg = getArguments();
		if(agentArg != null || agentArg.length != 0){
			destination=(String)agentArg[0];
			parent=(AID)agentArg[1];
		}
		
		Behaviour b;
		b = new TeleportBehaviour(this,destination);
		addBehaviour(b);