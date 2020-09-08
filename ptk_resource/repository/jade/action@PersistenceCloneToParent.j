String parentName = myAgent.getLocalName().split("_")[0];
myAgent.doClone(myAgent.here(),parentName);
myAgent.addBehaviour(killer);