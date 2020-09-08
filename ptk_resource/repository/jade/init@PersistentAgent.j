// Register the supported ontologies
getContentManager().registerOntology(PersistenceOntology.getInstance());
        
// register the supported languages
		getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
		
		try{
			PersistenceHelper helper = (PersistenceHelper) getHelper(PersistenceHelper.NAME);
			helper.registerSavable(this);
		}
		catch(jade.core.ServiceException e){
			e.printStackTrace();
		}