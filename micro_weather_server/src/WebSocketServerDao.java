import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketServerDao extends WebSocketServer {

    private static List<WebSocket> webSocketList = new ArrayList<>();
    private ExecutorService executorService; // 线程池

    public WebSocketServerDao(int port) {
        super(new InetSocketAddress(port));
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // 初始化线程池
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        webSocketList.add(conn);
        System.out.println("WebSocket连接已建立：" + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        webSocketList.remove(conn);
        System.out.println("WebSocket连接已关闭：" + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("WebSocket接收到消息：" + message);
        executorService.execute(() -> { // 使用线程池处理消息
            conn.send("WebSocket服务端回复：" + message); // 发送消息到WebSocket客户端
        });
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("WebSocket异常：" + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("启动");
    }

}

