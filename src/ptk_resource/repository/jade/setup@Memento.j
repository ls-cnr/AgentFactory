init();
killer = new OneShotBehaviour(this) {
    public void action() {
    	doDelete();
    };
};
persistentSetup();