import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //Constructor...
    public Server() {
        try {
            //Server Creation at specific Port
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            //After Getting request from client it accepts
            socket = server.accept();

            /*
            Here InputStream is created from socket which output Byte Stream
            Now this byte Stream is trasnformed to InputStream
            then this InputStream is readed by BfferReader
             */
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //here SocketOuput Stream is created for writing
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            //TODO:handle exception
            e.printStackTrace();
        }

    }

    public void startReading() {
        //thread -reading
        Runnable r1 = () -> {
            System.out.println("reader started..");

            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated...");
                        break;
                    }

                    System.out.println("Client : " + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        //thread - data from user & send to client
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                //Here we are taking input from System.in that is console
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);

                out.flush();


            } catch (Exception e) {
                //TODO:handle Exception
                e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is server..going to start server");
        new Server();
    }
}
