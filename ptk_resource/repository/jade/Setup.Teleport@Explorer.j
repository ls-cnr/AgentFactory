try {
				Location dest = new jade.core.ContainerID(destination, null);
				myAgent.doMove(dest);
			} catch (Exception n) {
				n.printStackTrace();
			}