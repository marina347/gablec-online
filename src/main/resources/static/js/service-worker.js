'use strict';

self.addEventListener('push', function(event) {
  console.log('Received a push message', event);
    let username = '';
    fetch('/app1/rest/notifications?username='+username)
        .then(
            function(response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }

                // Examine the text in the response
                response.json().then(function(data) {
                    console.log(data);
                    let title = data.title;
                    let body =  data.body;
                    let icon = data.icon;
                    let tag = 'simple-push-demo-notification-tag';

                    event.waitUntil(
                        self.registration.showNotification(title, {
                            body: body,
                            icon: icon,
                            tag: tag
                        })
                    );
                });
            }
        )
        .catch(function(err) {
            console.log('Fetch Error :-S', err);
        });


});

self.addEventListener('notificationclick', function(event) {
  console.log('On notification click: ', event.notification.tag);
  // Android doesn’t close the notification when you click on it
  // See: http://crbug.com/463146
  event.notification.close();

  // This looks to see if the current is already open and
  // focuses if it is
  event.waitUntil(clients.matchAll({
    type: 'window'
  }).then(function(clientList) {
    for (var i = 0; i < clientList.length; i++) {
      var client = clientList[i];
      if (client.url === '/' && 'focus' in client) {
        return client.focus();
      }
    }
    if (clients.openWindow) {
      return clients.openWindow('/');
    }
  }));
});