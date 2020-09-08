ListNode min_cost_node = null;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ListNode node = (ListNode) it.next();
				if (min_cost_node==null || node.getCost() < min_cost_node.getCost())
					min_cost_node = node;					
			}
			return min_cost_node;