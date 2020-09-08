if(cloneForPersistence){
	removeBehaviour(killer);
	SequentialBehaviour seq = new SequentialBehaviour();
	seq.addSubBehaviour(new WaitingForParentDead(this));
	seq.addSubBehaviour(new PersistenceCloneToParent(this));
	addBehaviour(seq);
}
init();
persistentAfterLoad();