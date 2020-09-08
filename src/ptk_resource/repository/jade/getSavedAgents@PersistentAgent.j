try{
	PersistenceHelper helper = (PersistenceHelper)getHelper(PersistenceHelper.NAME);
	return(helper.getSavedAgents(getContainerController().getContainerName(),jade.core.persistence.PersistenceManager.DEFAULT_REPOSITORY));
}
catch(jade.core.ServiceException e){
	e.printStackTrace();
}
catch(jade.core.IMTPException e){
	e.printStackTrace();
}
catch(jade.core.NotFoundException e){
	e.printStackTrace();
}
catch(Exception e){
	e.printStackTrace();
}
return(null);