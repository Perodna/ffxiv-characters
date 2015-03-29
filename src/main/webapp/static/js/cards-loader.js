function getCardsXml() {
	// NOTE:  This function must return the value from calling the $.ajax() method
	return $.ajax({
		type : "GET",
		url : "config/cards.xml",
		dataType : "xml",
	});
}

function getNpcXml() {
	// NOTE:  This function must return the value from calling the $.ajax() method
	return $.ajax({
		type : "GET",
		url : "config/npc.xml",
		dataType : "xml",
	});
}

function getTrialsXml() {
	// NOTE:  This function must return the value from calling the $.ajax() method
	return $.ajax({
		type : "GET",
		url : "config/trials.xml",
		dataType : "xml",
	});
}

function loadCards(cardsXml, cards) {
	$(cardsXml).find("card").each(function() {
		cards[$(this).attr("id")] = {
			id : $(this).attr("id"),
			name : $(this).attr("name"),
			top : $(this).attr("top"),
			left : $(this).attr("left"),
			right : $(this).attr("right"),
			bottom : $(this).attr("bottom"),
			stars : $(this).attr("stars"),
			no : $(this).attr("no")
		};
	});
}

function loadNpc(npcXml, npc, cards) {
	// iterate through NPCs
	$(npcXml).find("npc").each(function() {	
		// get cards for this npc
		var npcCards = [];
		$(npcXml).find("npc[id=" + $(this).attr("id") +"] > cards >card").each(function() {
			npcCards.push(cards[$(this).attr("id")]);
		});
		
		// create npc object with cards and other info
		npc.push({
			id : $(this).attr("id"),
			name : $(this).attr("name"),
			location : $(this).attr("location"),
			cards : npcCards
		});
	});
}

function loadTrials(trialsXml, trials, cards) {
	// iterate through trials
	$(trialsXml).find("trial").each(function() {	
		// get cards for this trial
		var trialCards = [];
		$(trialsXml).find("trial[id=" + $(this).attr("id") +"] > cards > card").each(function() {
			trialCards.push(cards[$(this).attr("id")]);
		});
		
		// create trial object with cards and other info
		trials.push({
			id : $(this).attr("id"),
			name : $(this).attr("name"),
			cards : trialCards
		});
	});
}

function toNumericArray(associativeArray) {
	res = [];
	for (item in associativeArray) {
		res.push(associativeArray[item]);
	}
	return res;
}