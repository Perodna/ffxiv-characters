function updateCookie() {
	var elementValues = {};
	$(".card-checkbox").each(function() {
		elementValues[this.name] = this.checked;
	});

	$.cookie('elementValues', elementValues, {
		expires : 10000,
		path : '/'
	});
}

function repopulateFormElements() {
	var elementValues = $.cookie('elementValues');

	if (elementValues) {
		Object.keys(elementValues).forEach(function(element) {
			var checked = elementValues[element];
			$("[name=" + element + "]").bootstrapSwitch('state', checked);
		});
	}
}