double cost = -1;
			Iterator arcs = graph.getArcs().iterator();
			while (arcs.hasNext()) {
				AStarArc a = (AStarArc) arcs.next();
				if (a.getStart()==A && a.getEnd()==B || a.getStart()==B && a.getEnd()==A)
					cost = a.getWeight();
			}
			return cost;