super.takeDown();
		Calendar today = Calendar.getInstance();
		logger.info(AGENT_NAME+" died at "+today.getTime().toString());