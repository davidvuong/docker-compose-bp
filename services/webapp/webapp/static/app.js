(function () {
  document.getElementById('send-btn').addEventListener('click', function (event) {
    event.preventDefault();

    var messageInput = document.getElementById('user-input');
    var message = messageInput.value;

    if (!message) {
      return false;
    }

    var ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function () {
      if (this.readyState === 4) {
        console.log(this.response);
      }
    };
    ajax.open('POST', window.location.origin + '/send-message');
    ajax.setRequestHeader('Content-type', 'application/json');
    ajax.send(JSON.stringify({ message: message }));

    messageInput.value = '';
    return true;
  });
})();
