try {
								ContainerID where = new ContainerID(myAgent.getContainerController().getContainerName() , null);
								PersistenceHelper helper = (PersistenceHelper) myAgent.getHelper(PersistenceHelper.NAME);
								helper.freeze(null,where);
							}
							catch (Exception e) {
								e.printStackTrace();
							}