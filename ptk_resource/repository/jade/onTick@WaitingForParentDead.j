try{
	if((jade.domain.AMSService.search(myAgent,descr)).length == 0)
    	stop();
}
catch(Exception e){
	e.printStackTrace();
}