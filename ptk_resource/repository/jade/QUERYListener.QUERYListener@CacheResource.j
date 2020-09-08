super(
				owner,
				AchieveREResponder.createMessageTemplate(
					FIPANames.InteractionProtocol.FIPA_REQUEST));

			query_database = new QueryDatabase(myAgent, id_database, "", this);
			registerPrepareResultNotification(query_database);