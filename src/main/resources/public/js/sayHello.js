function setResults(id, message)  {
    $('.hello-world-id').text('Times Called: ' + id);
    $('.hello-world-message').text('Message: ' + message);
}

function sayHello(verb) {
    var settings = {
        "async": true,
        "url": "http://localhost:8080/greeting",
        "type": verb,
        "headers": {
            "Authorization": "token",
            "Access-Control-Allow-Origin": "*",
            "Content-Type" : "application/json"
        }
    }
    $.ajax(settings).then(function (result, status) {
        setResults(result.id, result.message);
    }, function () {
        setResults('error', 'Request Failed');
    });
}
