	try{
	PersistenceHelper helper = (PersistenceHelper)myAgent.getHelper(PersistenceHelper.NAME);
	helper.reload(null);
			}
			catch(jade.core.ServiceException e){
				e.printStackTrace();
			}