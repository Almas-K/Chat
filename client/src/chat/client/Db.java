package chat.client;

import javax.swing.*;
import java.sql.*;

public class Db extends JFrame{
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=Asia/Almaty";
    private static String user = "root";
    private static String sqlPass = "root";



   public static void createUser(String name, String pass){
       try {
           System.out.println("Create user name " + name);
           System.out.println("Create password");
           Connection con = (Connection) DriverManager.getConnection(url,user, sqlPass);
           PreparedStatement st = (PreparedStatement) con.prepareStatement("INSERT INTO users(name, password) VALUES (?, ?)");
           st.setString(1, name);
           st.setString(2, pass);
           st.executeUpdate();
       } catch (SQLException sqlException) {
           sqlException.printStackTrace();
       }
   }


    public void getUser(String userName, String password, JButton btnNewButton) {
        try {
            Connection connection = (Connection) DriverManager.getConnection(url,
                    user, sqlPass);

            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT name, password FROM users WHERE name=? and password=?");

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                dispose();
                ClientWindow ah = new ClientWindow(userName);
                ah.setTitle("Welcome");
                ah.setVisible(true);
                JOptionPane.showMessageDialog(null, "You have successfully logged in." + "\n" + "Welcome to the chat");

            } else {
                JOptionPane.showMessageDialog(btnNewButton, "Wrong Username or Password");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
