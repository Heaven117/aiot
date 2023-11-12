import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestWebSocket {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebSocketServerDao s = new WebSocketServerDao(8081); // 服务器所使用的端口号,与html文件保持一致
        s.start();
        System.out.println("server is running..." + s.getPort());

        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String in = sysin.readLine();
            s.broadcast(in);
            if (in.equals("exit")) {
                s.stop(1000);
                break;
            }
        }
    }

}
