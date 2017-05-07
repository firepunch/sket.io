<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Testing websockets</title>
</head>
<body>
</body>
<script type="text/javascript" src="/WEB-INF/lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript">

    var webSocket = new WebSocket('ws://localhost:9595/websocket');

    webSocket.onerror = function (event) {
        console.log('onError(' + event + ')');
    };
    webSocket.onOpen = function (event) {
        console.log("온오푼 클라");
        webSocket.send("onOpen. 클라에서 서버로~");
    };
    webSocket.onMessage = function (event) {
        console.log('onMessage(' + event + ')');
    };

</script>
</html>