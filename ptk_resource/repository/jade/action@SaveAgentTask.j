try{
	PersistenceHelper helper = (PersistenceHelper)myAgent.getHelper(PersistenceHelper.NAME);
				helper.save(null);
}
			catch(jade.core.ServiceException e){
				e.printStackTrace();
}