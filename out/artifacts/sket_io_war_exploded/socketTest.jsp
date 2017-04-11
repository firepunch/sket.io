<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Testing websockets</title>
</head>
<body>
</body>
<script type="text/javascript">

    var webSocket = new WebSocket('ws://localhost:9080/test');

    webSocket.onerror = function(event) {
        console.log('onError('+event+')');
    };
    webSocket.onopen = function(event) {
        console.log('onOpen('+event+')');
    };
    webSocket.onmessage = function(event) {
        console.log('onMessage('+event+')');
    };

</script>
</html>