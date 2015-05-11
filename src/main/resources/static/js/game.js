var $loader;
var $content;

var gameStates;
var currentPlayingGameState;
var playerName;
var playerId;

var noState = 'no';
var choosePlayerState = 'choose-player';
var viewResultsState = 'view-results';
var chooseTopicState = 'choose-topic';
var questionState = 'question';
var statisticsState = 'statistics';

var winMsg = 'Вы победили игрока ';
var looseMsg = 'Вы проиграли игроку ';
var drawMsg = 'Вы сыграли вничью с игроком ';
var countMsg = ' со счетом ';
var countDrawMsg = '. Счет ';

$(document).ready(function() {
	$loader = $('#loader');
	$content= $('#content');
	showLoader();
	createGame();
});

function createGame() {
	$.ajax('/game/state').success(function(states) {
		gameStates = states;
		playerName = gameStates[0].playerName;
		playerId = gameStates[0].playerId;
		
		$('#player-name').text(playerName);
		var gameStatesLength = gameStates.length;
		
		showEndGameNotifications(gameStatesLength);
		
		var currentPlayingGameStateIndex = hasPlayingGame(gameStatesLength);
		if (currentPlayingGameStateIndex < 0) {
			generateCompetitors(gameStatesLength);
			showState(noState);
			return;
		}
		
		currentPlayingGameState = gameStates[currentPlayingGameStateIndex];
		var playingRound = getPlayingRound(currentPlayingGameState);
		chooseTopic(playingRound.topic.id);
	});
}

function showEndGameNotifications(gameStatesLength) {
	var msg = '';
	for (var i = 0; i < gameStatesLength; i++) {
		if (gameStates[i].gameResult !== 'NO_RESULT') {
			msg += '<p class="text->';
			if (gameStates[i].gameResult === 'WIN') {
				msg += 'success">';
				msg += winMsg;
			}
			if (gameStates[i].gameResult === 'LOOSE') {
				msg += 'danger">';
				msg += looseMsg;
			}
			if (gameStates[i].gameResult === 'DRAW') {
				msg += 'info">';
				msg += drawMsg;
			}
			msg += gameStates[i].competitorName;
			if (gameStates[i].gameResult === 'WIN' || gameStates[i].gameResult === 'LOOSE') {
				msg += countMsg;
			} else {
				msg += countDrawMsg;
			}
			msg += gameStates[i].points;
			msg += '-';
			msg += gameStates[i].competitorPoints;
			msg += '</p>';
			$.ajax('/game/complete/' + gameStates[i].id);
		}
	}
	if (msg !== '') {
		$('#notification').html(msg);
		$('#modal').modal('show');
	}
}

function hasPlayingGame(gameStatesLength) {
	for (var i = 0; i < gameStatesLength; i++) {
		if (gameStates[i].playingNow) {
			return i;
		}
	}
	return -1;
}

function generateCompetitors(gameStatesLength) {
	var $players = $('#players');
	$players.html('');
	for (var i = 0; i < gameStatesLength; i++) {
		if (gameStates[i].competitorId != null && gameStates[i].gameResult === 'NO_RESULT') {
			if (gameStates[i].yourTurn) {
				$players.append('<button type="button" class="btn btn-block btn-lg btn-primary" onclick="play(' + i + ')">' + gameStates[i].competitorName + ' ' + gameStates[i].points + ' - ' + gameStates[i].competitorPoints + '</button>');				
			} else {
				$players.append('<button type="button" class="btn btn-block btn-lg btn-primary disabled">' + gameStates[i].competitorName + ' ' + gameStates[i].points + ' - ' + gameStates[i].competitorPoints + ' (ждем соперника)</button>');
			}
		}
	}
}

function play(gameStateIndex) {
	showLoader();
	var gameState = gameStates[gameStateIndex];
	currentPlayingGameState = gameState;
	
	$('#vs-names').text(gameState.playerName + ' vs ' + gameState.competitorName);
	$('#player-name-header').text(gameState.playerName);
	$('#scores').text(gameState.points + ' - ' + gameState.competitorPoints);
	$('#competitor-name-header').text(gameState.competitorName);
	
	$('tr[id^="round-"]').html('<td></td><td></td><td></td>');
	
	var playedRounds = gameState.playedRounds;
	if (playedRounds != null) {
		for (var i = 0; i < playedRounds.length; i++) {
			var playedRound = playedRounds[i];
			var tds = $('#round-' + (i + 1) + ' > td');
			
			if (playedRound.answers == null) {
				for (var j = 0; j < 3; j++) {
					$(tds[0]).append('<button type="button" class="btn btn-default btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
				}
			} else {
				for (var j = 0; j < 3; j++) {
					var answer = playedRound.answers[j];
					if (answer) {
						$(tds[0]).append('<button type="button" class="btn btn-success btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
					} else {
						$(tds[0]).append('<button type="button" class="btn btn-danger btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
					}
				}
			}
			
			$(tds[1]).text(playedRound.topic.name);
			
			if (playedRound.competitorAnswers == null) {
				for (var j = 0; j < 3; j++) {
					$(tds[2]).append('<button type="button" class="btn btn-default btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
				}
			} else {
				for (var j = 0; j < 3; j++) {
					var answer = playedRound.competitorAnswers[j];
					if (answer) {
						$(tds[2]).append('<button type="button" class="btn btn-success btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
					} else {
						$(tds[2]).append('<button type="button" class="btn btn-danger btn-xs disabled empty-button">&nbsp;</button>&nbsp;');
					}
				}
			}
		}
	}
	
	showState(viewResultsState);
}

function makeTurn() {
	showLoader();
	$.ajax('/game/turn/topic/' + currentPlayingGameState.id).success(function(topics) {
		if (topics.length == 1) {
			chooseTopic(topics[0].id);
		} else {
			var $topics = $('#topics');
			$topics.html('');
			for (var i = 0; i < topics.length; i++) {
				var topic = topics[i];
				$topics.append('<button type="button" class="btn btn-block btn-lg btn-primary" onclick="chooseTopic(\'' + topic.id + '\')">' + topic.name + '</button>');
			}
			showState(chooseTopicState);
		}
	});
}

function chooseTopic(topicId) {
	showLoader();
	$.ajax('/game/turn/topic/' + currentPlayingGameState.id + '/' + topicId + '/q').success(function(question) {
		$.ajax('/game/state/current').success(function(state) {
			currentPlayingGameState = state;
			
			var playingRound = getPlayingRound(currentPlayingGameState);
			
			$('#current-topic').text(playingRound.topic.name + '. Вопрос №' + (playingRound.questionsCounter + 1));
			$('#question').text(question.question);
			for (var i = 0; i < 4; i++) {
				$('#answer' + i).text(question.answers[i]);
			}
			$('#next').hide();
			
			$('button[id^="answer"]').removeClass('disabled');
			$('button[id^="answer"]').removeClass('btn-danger');
			$('button[id^="answer"]').removeClass('btn-success');
			$('button[id^="answer"]').addClass('btn-default');
			
			showState(questionState);
		});
	});
}

function getPlayingRound(state) {
	return state.playedRounds[state.playedRounds.length - 1];
}

function answer(answerIndex) {
	$('button[id^="answer"]').addClass('disabled');
	
	$.ajax('/game/turn/' + currentPlayingGameState.id + '/' + answerIndex).success(function(rightAnswer) {
		$('#answer' + rightAnswer).removeClass('btn-default');
		if (rightAnswer != answerIndex) {
			$('#answer' + answerIndex).removeClass('btn-default');
			$('#answer' + answerIndex).addClass('btn-danger');
		}
		$('#answer' + rightAnswer).addClass('btn-success');
	});
	
	$('#next').show();
}

function nextQuestion() {
	var playingRound = getPlayingRound(currentPlayingGameState);
	if (playingRound.questionsCounter + 1 == 3) {
		createGame();
	} else {
		chooseTopic(playingRound.topic.id);
	}
}

function showStatistics() {
	//TODO
}

function newGame() {
	showLoader();
	var $competitors = $('#competitors');
	$competitors.html('');
	$.ajax('/game/competitors').success(function(players) {
		for (var playerId in players) {
			$competitors.append('<button type="button" class="btn btn-block btn-lg btn-primary" onclick="startGame(\'' + playerId + '\', \'' + players[playerId] + '\')">' + players[playerId] + '</button>');
		}
		showState(choosePlayerState);
	});
}

function startGame(competitorId, competitorName) {
	showLoader();
	$.ajax('/game/start/' + competitorId + '/' + competitorName).success(function() {
		createGame();
	});
}

function showLoader() {
	$content.hide();
	$loader.show();
}

function hideLoader() {
	$loader.hide();
	$content.show();
}

function showState(stateId) {
	$('div[id$="-state"]').addClass('hide');
	$('#' + stateId + '-state').removeClass('hide');
	hideLoader();
}