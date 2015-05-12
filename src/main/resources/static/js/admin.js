$(document).ready(function() {
	loadTopics();
});

function loadTopics() {
	$.ajax('/admin/topics').success(function(topics) {
		for (var i = 0; i < topics.length; i++) {
			$('#topics').append('<option value="' + topics[i].id + '">' + topics[i].name + '</option>');
		}
	});
}