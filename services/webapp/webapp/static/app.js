(function () {
  var API_ENDPOINT = document.API_ENDPOINT;

  document.getElementById('send-btn').addEventListener('click', function (event) {
    event.preventDefault();

    var ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function () {
      debugger;
    };
    ajax.open('GET', API_ENDPOINT + '/send-message');
    ajax.send();
  });
})();
