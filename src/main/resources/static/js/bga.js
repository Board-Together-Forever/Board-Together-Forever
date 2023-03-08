// $.ajax("https://api.boardgameatlas.com/api/search?name=Catan&&client_id=" + bgaKey).done(function (data) {
//     console.log(data)
// })

$('#searchbtn').click(function (e) {
    e.preventDefault();
    let searchFor = $('#addresssearch').val();
    $.get("https://api.boardgameatlas.com/api/search?client_id=" + bgaKey, {
        name: searchFor
    }).done(function (data) {
        console.log(data)
        $('#found').html("1: " + '<a href="/gamesessions/create/' + data.games[0].upc + '">' + data.games[0].name + '</span></a><br>' )
        $('#found').append("2: " + '<a href="/gamesessions/create/' + data.games[1].upc + '">' + data.games[1].name + '</span></a><br>' )
        $('#found').append("3: " + '<a href="/gamesessions/create/' + data.games[2].upc + '">' + data.games[2].name + '</span></a><br>' )
        $('#found').append("4: " + '<a href="/gamesessions/create/' + data.games[3].upc + '">' + data.games[3].name + '</span></a><br>' )
        $('#found').append("5: " + '<a href="/gamesessions/create/' + data.games[4].upc + '">' + data.games[4].name + '</span></a><br>' )
    });
})

// <a th:href="@{/gamesessions/{id} (id = ${gamesession.id})}"><span th:text="${gamesession.gameSessionTitle}"></span></a>
