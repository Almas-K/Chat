package chat.client;

import chat.network.TCPConnection;
import chat.network.TCPConnectionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {
    private static final String IP_ADDR = "localhost";
    private static final int PORT = 8888;
    private static final int WIDTH = 1014;
    private static final int HEIGHT = 597;


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new ClientWindow();
//            }
//        });
//    }

    private final  JTextArea log = new JTextArea();
    private final  JLabel fieldNickname = new JLabel("Anonymous");
    private final JTextField fieldInput = new JTextField();
    private final JPanel bottomPanel = new JPanel(new BorderLayout());

    private TCPConnection connection;

    public ClientWindow(String userSes) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);
        fieldInput.addActionListener(this);
        fieldNickname.setText(userSes + ": ");
        fieldNickname.setFont(new Font("Tahoma", Font.PLAIN, 26));
        fieldNickname.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(fieldNickname, BorderLayout.WEST);
        bottomPanel.add(fieldInput);

        setVisible(true);

        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            printMessage("Connection exception: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendMsg(fieldNickname.getText() + ": " + msg);
    }

    @Override
    public void onConnectionReady(TCPConnection connection) {
        printMessage("Connection ready.");
    }

    @Override
    public void onReceiveString(TCPConnection connection, String value) {
        printMessage(value);
    }

    @Override
    public void onDisconnect(TCPConnection connection) {
        printMessage("Connection close");
    }

    @Override
    public void onException(TCPConnection connection, Exception e) {
        printMessage("Connection exception: " + e);
    }

    private synchronized void printMessage(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
