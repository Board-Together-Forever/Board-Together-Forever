// $.ajax("https://api.boardgameatlas.com/api/search?name=Catan&&client_id=" + bgaKey).done(function (data) {
//     console.log(data)
// })

$('#searchbtn').click(function (e) {
    e.preventDefault();
    let searchFor = $('#addresssearch').val();
    var sessionId = $('#gameSessionId').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        name: searchFor
    }).done(function (data) {
        console.log(data)
        console.log(sessionId)
        $('#found').html("1: " + '<a href="/gamesessions/create/' + data.games[0].upc + '">' + data.games[0].name + '</span></a><br>' )
        $('#found').append("2: " + '<a href="/gamesessions/create/' + data.games[1].upc + '">' + data.games[1].name + '</span></a><br>' )
        $('#found').append("3: " + '<a href="/gamesessions/create/' + data.games[2].upc + '">' + data.games[2].name + '</span></a><br>' )
        $('#found').append("4: " + '<a href="/gamesessions/create/' + data.games[3].upc + '">' + data.games[3].name + '</span></a><br>' )
        $('#found').append("5: " + '<a href="/gamesessions/create/' + data.games[4].upc + '">' + data.games[4].name + '</span></a><br>' )
        $('#found2').html("1: " + '<a href="/gamesessions/'+ 1 + '/' + data.games[0].upc + '">' + data.games[0].name + '</span></a><br>' )
        $('#found2').append("2: " + '<a href="/gamesessions/' + data.games[1].upc + '">' + data.games[1].name + '</span></a><br>' )
        $('#found2').append("3: " + '<a href="/gamesessions/' + data.games[2].upc + '">' + data.games[2].name + '</span></a><br>' )
        $('#found2').append("4: " + '<a href="/gamesessions/' + data.games[3].upc + '">' + data.games[3].name + '</span></a><br>' )
        $('#found2').append("5: " + '<a href="/gamesessions/' + data.games[4].upc + '">' + data.games[4].name + '</span></a><br>' )
    });
})

$(document).ready(function() {
    var Upc = $('#upc').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        upc: Upc
    }).done(function (data) {
        if (Upc !== undefined){
            console.log(Upc)
        $('#gameSessionName').val(data.games[0].name);
        $('#gameDuration').val(data.games[0].playtime + " minutes");
        $('#playerLimit').val(data.games[0].max_players);
        $('#upcField').val(Upc);
        $("#gameImage").html("<img src='"+ data.games[0].image_url +"' alt='GameImage'>");

        console.log(data)}
        else    {
            Upc = $('#upcField').val()
            $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
                upc: Upc
            }).done(function (data) {
                $('#gameSessionName').val(data.games[0].name);
                $('#gameDuration').val(data.games[0].playtime + " minutes");
                $('#playerLimit').val(data.games[0].max_players);
            $("#gameImage").html("<img src='"+ data.games[0].image_url +"' alt='GameImage'>");
            });
        }
    });

})





// <a th:href="@{/gamesessions/{id} (id = ${gamesession.id})}"><span th:text="${gamesession.gameSessionTitle}"></span></a>
