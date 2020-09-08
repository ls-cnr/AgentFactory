try{
	PersistenceHelper helper = (PersistenceHelper)myAgent.getHelper(PersistenceHelper.NAME);
	helper.save(null);
	if(killAfterSave)
		myAgent.addBehaviour(killer);
}
catch(jade.core.ServiceException e){
	e.printStackTrace();
}