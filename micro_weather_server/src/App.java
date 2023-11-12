import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        WebSocketServerDao websocket = new WebSocketServerDao(8080); // 前端服务所使用的端口号
        websocket.start();
        System.out.println("启动webSocket..." + websocket.getPort());

        ServerSocket serverSocket = new ServerSocket(8086);// 客户端服务所使用的端口号
        System.out.println("启动服务器....");


        while (true) {
            final Socket Client = serverSocket.accept();
            System.out.println("客户端:" + Client.getInetAddress().getLocalHost() + "已连接到服务器");
            new Handler(Client, websocket);
        }
    }
}
