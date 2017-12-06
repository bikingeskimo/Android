package android.oving6server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class CalculatorServer extends Thread {
    private final static int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket connection;
    private PrintWriter printer;
    private ObjectInputStream objectInputStream;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                connection = serverSocket.accept();

                printer = new PrintWriter(connection.getOutputStream(), true);
                objectInputStream = new ObjectInputStream(connection.getInputStream());

                Expression exp = (Expression) objectInputStream.readObject();

                if(exp.op == Operand.FIN) {
                    printer.println("Server shutdown");
                    break;
                }

                int result = exp.evaluate();
                printer.println(result);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //close the things
            try {
                printer.close();
                objectInputStream.close();
                connection.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
