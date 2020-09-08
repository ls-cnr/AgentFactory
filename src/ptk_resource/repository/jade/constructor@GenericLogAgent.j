Calendar today = Calendar.getInstance();
    	BasicConfigurator.configure();
    	
    	logger.setLevel(log_level);
    	logger.info(AGENT_NAME+" born at "+today.getTime().toString());