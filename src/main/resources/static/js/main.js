'use strict';

var API_KEY = window.GoogleSamples.Config.gcmAPIKey;
var GCM_ENDPOINT = 'https://android.googleapis.com/fcm/send';

var curlCommandDiv ;
var isPushEnabled = false;


function registerToServer(googleId)
{



  googleId = googleId.endpoint.split("/").pop();
    $.ajax({
        url: "/app1/rest/notifications",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify({googleId:googleId}),

        success:
            function(result)
            {
                console.log("succ"+result);
                //alert("Request approved");
                //window.location.href

            },
        error:
            function (xhr, status, error)
            {
                //alert("Backend problems.Try later.");
                console.log("err"+xhr);
                console.log(xhr);
            }
    });

}


// This method handles the removal of subscriptionId
// in Chrome 44 by concatenating the subscription Id
// to the subscription endpoint
function endpointWorkaround(pushSubscription) {
  // Make sure we only mess with GCM
  if (pushSubscription.endpoint.indexOf('https://android.googleapis.com/fcm/send') !== 0) {
    return pushSubscription.endpoint;
  }

  var mergedEndpoint = pushSubscription.endpoint;
  // Chrome 42 + 43 will not have the subscriptionId attached
  // to the endpoint.
  if (pushSubscription.subscriptionId &&
    pushSubscription.endpoint.indexOf(pushSubscription.subscriptionId) === -1) {
    // Handle version 42 where you have separate subId and Endpoint
    mergedEndpoint = pushSubscription.endpoint + '/' +
      pushSubscription.subscriptionId;
  }
  return mergedEndpoint;
}

function sendSubscriptionToServer(subscription) {
  // TODO: Send the subscription.endpoint
  // to your server and save it to send a
  // push message at a later date
  //
  // For compatibly of Chrome 43, get the endpoint via
  // endpointWorkaround(subscription)
  console.log('TODO: Implement sendSubscriptionToServer()');

  var mergedEndpoint = endpointWorkaround(subscription);

  // This is just for demo purposes / an easy to test by
  // generating the appropriate cURL command
  showCurlCommand(mergedEndpoint);
}

// NOTE: This code is only suitable for GCM endpoints,
// When another browser has a working version, alter
// this to send a PUSH request directly to the endpoint
function showCurlCommand(mergedEndpoint) {
  // The curl command to trigger a push message straight from GCM
  if (mergedEndpoint.indexOf(GCM_ENDPOINT) !== 0) {
    //window.Demo.debug.log('This browser isn\'t currently ' +
    //  'supported for this demo');
    return;
  }

  var endpointSections = mergedEndpoint.split('/');
  var subscriptionId = endpointSections[endpointSections.length - 1];

  var curlCommand = 'curl --header "Authorization: key=' + API_KEY +
    '" --header Content-Type:"application/json" ' + GCM_ENDPOINT +
    ' -d "{\\"registration_ids\\":[\\"' + subscriptionId + '\\"]}"';

  //curlCommandDiv.textContent = curlCommand;
  console.log(curlCommand);

}

function subscribe() {
  // Disable the button so it can't be changed while
  // we process the permission request
  //var pushButton = document.querySelector('.js-push-button');
  //pushButton.disabled = true;

  navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {
    serviceWorkerRegistration.pushManager.subscribe({userVisibleOnly: true})
      .then(function(subscription) {
        // The subscription was successful
        isPushEnabled = true;
        //pushButton.textContent = 'Disable Push Messages';
        //pushButton.disabled = false;
          registerToServer(subscription);
        // TODO: Send the subscription subscription.endpoint
        // to your server and save it to send a push message
        // at a later date
        return sendSubscriptionToServer(subscription);
      })
      .catch(function(e) {
        if (Notification.permission === 'denied') {
          // The user denied the notification permission
          // means we failed to subscribe and the user will needwhich
          // to manually change the notification permission to
          // subscribe to push messages
          //window.Demo.debug.log('Permission for Notifications was denied');
          //pushButton.disabled = true;
        } else {
          // A problem occurred with the subscription, this can
          // often be down to an issue or lack of the gcm_sender_id
          // and / or gcm_user_visible_only
          //window.Demo.debug.log('Unable to subscribe to push.', e);
          //pushButton.disabled = false;
          //pushButton.textContent = 'Enable Push Messages';
        }
      });
  });
}

// Once the service worker is registered set the initial state
function initialiseState() {
  // Are Notifications supported in the service worker?
  if (!('showNotification' in ServiceWorkerRegistration.prototype)) {
    //window.Demo.debug.log('Notifications aren\'t supported.');
    return;
  }

  // Check the current Notification permission.
  // If its denied, it's a permanent block until the
  // user changes the permission
  if (Notification.permission === 'denied') {
    //window.Demo.debug.log('The user has blocked notifications.');
    return;
  }

  // Check if push messaging is supported
  if (!('PushManager' in window)) {
    //window.Demo.debug.log('Push messaging isn\'t supported.');
    return;
  }

  // We need the service worker registration to check for a subscription
  navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {
    // Do we already have a push message subscription?
    serviceWorkerRegistration.pushManager.getSubscription()
      .then(function(subscription) {
        // Enable any UI which subscribes / unsubscribes from
        // push messages.
        //var pushButton = document.querySelector('.js-push-button');
        //pushButton.disabled = false;

        if (!subscription) {
          // We aren’t subscribed to push, so set UI
          // to allow the user to enable push
          return;
        }

        // Keep your server in sync with the latest subscription
        sendSubscriptionToServer(subscription);
          registerToServer(subscription);

        isPushEnabled = true;
      })
      .catch(function(err) {
        //window.Demo.debug.log('Error during getSubscription()', err);
      });
  });
}

window.addEventListener('load', function() {
    subscribe();
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('/app1/service-worker.js')
            .then(initialiseState);
    } else {
        //window.Demo.debug.log('Service workers aren\'t supported in this browser.');
    }

    $('[data-toggle="tooltip"]').tooltip()


});