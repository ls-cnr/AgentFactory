java.text.SimpleDateFormat sfd = new java.text.SimpleDateFormat("ddMMyyyy");
java.text.SimpleDateFormat hourFormatter = new java.text.SimpleDateFormat("HH:mm:ss");
cloneName = myAgent.getLocalName() + "_" + sfd.format(new java.util.Date()) 
			+ "_" + hourFormatter.format(new java.util.Date());
myAgent.doClone(myAgent.here(),cloneName);