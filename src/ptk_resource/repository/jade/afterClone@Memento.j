removeBehaviour(killer);
if(cloneForPersistence)
	addBehaviour(new SaveBehaviour(this,true));
else{
	init();
	persistentAfterClone();
}