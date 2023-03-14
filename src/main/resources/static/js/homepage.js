$(document).ready(function() {
    let Upc = $('#upc').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        name: Upc
    }).done(function (data) {
        if (Upc !== undefined){
            $("#gameImage").html("<img src='"+ data.games[0].image_url +"' alt='GameImage'>");
            console.log(data)}
    });
})

$(document).ready(function() {
    let Upc = $('#upc2').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        name: Upc
    }).done(function (data) {
        if (Upc !== undefined){
            $("#gameImage2").html("<img src='"+ data.games[0].image_url +"' alt='GameImage'>");
            console.log(data)}
    });
})
$(document).ready(function() {
    let Upc = $('#upc3').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        name: Upc
    }).done(function (data) {
        if (Upc !== undefined){
            $("#gameImage3").html("<img src='"+ data.games[0].image_url +"' alt='GameImage'>");
            console.log(data)}
    });
})