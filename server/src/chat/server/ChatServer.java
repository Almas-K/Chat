package chat.server;

import chat.network.TCPConnection;
import chat.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server is running!");
        try (ServerSocket serverSocket =new ServerSocket(8888)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection connection) {
        connections.add(connection);
        sendToAllConnections("User connected: " + connection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection connection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection connection) {
        connections.remove(connection);
        sendToAllConnections("User disconnected: " + connection);
    }

    @Override
    public synchronized void onException(TCPConnection connection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        final int cnt = connections.size();
        for (int i=0; i < cnt; i++) {
            connections.get(i).sendMsg(value);
        }
    }
}
