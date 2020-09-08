QUERYListener myQUERYListener =
			new QUERYListener(this, new AID(DATABASE_AGENT, false));
		addBehaviour(myQUERYListener);
		INSERTListener myINSERTListener = new INSERTListener(this);
		addBehaviour(myINSERTListener);