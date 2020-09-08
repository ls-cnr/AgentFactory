//Story save mode
if(storyActivated){
    SequentialBehaviour seq = new SequentialBehaviour();
    seq.addSubBehaviour(new PersistenceCloneMySelf(this));
    seq.addSubBehaviour(new WakerBehaviour(this,300){
    	public void onWake(){
    		cloneForPersistence = false;
    	}
    });
    		
    addBehaviour(seq);
}
else//Normal save mode
	addBehaviour(new SaveBehaviour(this,false));