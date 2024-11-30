/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package huii.carogame;
import javax.swing.*;
import java.awt.*;
import java.net.*;

public class Server extends JFrame{
    private int SIZE = 15;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private Board board = new Board();
    private boolean myTurn = true;
    
    private int port_client = 5678;
    private int port_server = 1234;
    private DatagramSocket socket = new DatagramSocket(port_server);
    private InetAddress IP = InetAddress.getByName("127.0.0.1"); //ip doi thu
    
    public Server() throws Exception
    {
        setTitle("Server");
        setLayout(new GridLayout(SIZE, SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setBackground(Color.WHITE);
                int x = i, y = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(x, y));
                add(buttons[i][j]);
            }
        }
        new Thread(this::receiveData).start();
    }

    
    private void handleButtonClick(int x, int y)
    {
        if(!myTurn)
        {
            JOptionPane.showMessageDialog(this, "chua den luot !");
            return;
        }
        if (board.check(x, y,"X"))
        {
            buttons[x][y].setText("X");
            buttons[x][y].setEnabled(false);
            myTurn = false;
            
            sendData(x, y);

            if (board.checkWin(x, y,"X"))
            {
                JOptionPane.showMessageDialog(this, "Player Server Win !");
                System.exit(0);
            }
        }
    }

    private void sendData(int x, int y)
    {
        try
        {
            String str = x + "-" + y ;
            byte[] data = str.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, IP, port_client);
            socket.send(packet);
        }
        catch (Exception e)
        {
        }
    }

    private void receiveData()
    {
        try
        {
            while (true)
            {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                String[] str = message.split("-");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                SwingUtilities.invokeLater(() -> {
                    buttons[x][y].setText("O");
                    buttons[x][y].setEnabled(false);
                    board.check(x, y,"O");

                    if (board.checkWin(x, y,"O")) {
                        JOptionPane.showMessageDialog(this, "Player Client win!");
                        System.exit(0);
                    }

                    myTurn = true;
                });
            }
        }
        catch (Exception e)
        {
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try {
                new Server().setVisible(true);
            } catch (Exception e) {
            }
        });
    }
}
