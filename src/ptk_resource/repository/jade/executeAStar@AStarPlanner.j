ListNode current;
			List successor_list;
			boolean terminated = false;

			opened.clear();
			closed.clear();
			
			ListNode node = new ListNode(starting_state);
			node.setRealCost(0);
			node.setEstimateCost( heuristic(starting_state,ending_state) );
			opened.add(node);
			
			do {
				current = getBestCostNode(opened);
				
				if (current == null)
					terminated = true;		// NON CI SONO PIU' NODI - FALLIMENTO
				else {
					opened.remove(current);
					closed.add(current);
				
					if (current.getNode() == ending_state) {
						terminated = true;	// RAGGIUNTO LO STATO FINALE - SUCCESSO
					}
				}
					
				if (!terminated) {
				    successor_list = neighboringNodes(current.getNode());
					Iterator it = successor_list.iterator();
				    while (it.hasNext()) {
				    	AStarNode successor = (AStarNode) it.next();
				    	double arc_cost = costAmong(current.getNode(),successor);
				    	double stimate = heuristic(successor,ending_state);
				    	double cost = current.getRealCost() + arc_cost + stimate;
				    	
				    	ListNode succInOpened = isInList(opened,successor);
				    	ListNode succInClosed = isInList(closed,successor);
				    	
				    	// IL SUCCESSORE SI TROVA NELLA LISTA APERTA
				    	if (succInOpened != null) {
				    		if (cost > succInOpened.getCost()) {
				    			opened.remove(succInOpened);
								closed.add(succInOpened);
								
								succInOpened.setRealCost(current.getRealCost() + arc_cost);
								succInOpened.setEstimateCost(stimate);
				    		}					    		

					    // IL SUCCESSORE SI TROVA NELLA LISTA CHIUSA
				    	} else if (succInClosed != null){
				    		if (cost <= succInClosed.getCost() ) {
				    			closed.remove(succInClosed);
				    			opened.add(succInClosed);
				    			
				    			succInClosed.setRealCost(current.getRealCost() + arc_cost);
				    			succInClosed.setEstimateCost(stimate);
				    		}					    		
				    	
					    // IL SUCCESSORE NON SI TROVA NELLE DUE LISTE
				    	} else {
				    		ListNode succ_node = new ListNode(successor);
				    		opened.add(succ_node);
				    		
				    		succ_node.setRealCost(current.getRealCost() + arc_cost);
				    		succ_node.setEstimateCost(heuristic(successor,ending_state));
				    	}
					}
				
				}
			} while (terminated != true);