ListNode found = null;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ListNode node = (ListNode) it.next();
				if (node.getNode()==A)
					found = node;
			}
			return null;