import java.io.DataInputStream;
import java.net.Socket;

public class Handler implements Runnable {
    protected Socket socket;
    protected WebSocketServerDao websocket;

    public Handler(Socket socket, WebSocketServerDao websocket) {
        this.socket = socket;
        this.websocket = websocket;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(this.socket.getInputStream());//新建一个输入流，用来读取客户端发来的数据
//            DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());//新建一个输出流，用来向客户端发送数据
//            System.out.print("请向客户端发送数据:");
//            String s = new BufferedReader(new InputStreamReader(System.in)).readLine();//程序停在这里,等待用户在控制台上输入要发给客户端的数据
//            out.writeUTF(s);  //把刚才从控制台输入的数据发送给客户端8266.注意服务器向8266发送数据，一定要采用UTF-8的格式，我也不知道为啥，可能其他格式也行，不过还得在单片机中进行啥处理，此处不作深究
//            out.close(); //关闭输出流


            //以下为接收客户端8266发给服务器的数据
            System.out.println("正在接受客户端的数据...");
            byte[] msg = new byte[1024];//声明一个数组用于接收客户端8266发来的数据
            input.read(msg);//注意8266发给服务器的数据在这里一定要使用read函数来接收，并且把接收到的数据存储到一个数组里面，不能再使用readUTF函数来读取了，可能单片机通过串口发送的数据不是UTF格式
            String message = new String(msg);
            System.out.println("客户端发过来的内容:" + message + "\n");

            this.websocket.broadcast(message);

            input.close();//关闭输入流
        } catch (Exception e) {
            System.out.println("服务器 run 异常: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("服务端 finally 异常:" + e.getMessage());
                }
            }
        }
    }
}
