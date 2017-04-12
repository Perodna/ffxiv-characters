function setUrlXIVDB(lodestoneId) {
    $.getJSON('https://api.xivdb.com/item/' + lodestoneId, function(data) {
            $('#' + lodestoneId).attr('href', data.url_xivdb);
        });
}