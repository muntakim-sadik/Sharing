/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crossplatformfilesharing;

/**
 *
 * @author paagol
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.util.*;

public class Main extends JFrame{
    boolean mark;
    Main(){
        super("Hauar Server");
        setSize(300,300);
        setLayout(new BorderLayout());
        mark = false;
        JButton b = new JButton("Chalu ho");
        b.addActionListener(
                new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!mark){
                    (new Server()).start();
                    mark = true;
                }
            }
        });
       add(b,BorderLayout.NORTH);

       setVisible(true);
    }


    public static void main(String args[]){
        Main m= new Main();
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class Server extends Thread{
    ServerSocket ss;
    Socket s;
    ArrayList<process> users;
    static ArrayList<String> list;

    public void run(){
        try {
            ss = new ServerSocket(11000,20);
            users = new ArrayList();
            list = new ArrayList();
            while(true){
                s = ss.accept();
                process p = new process(s,this);
                users.add(p);
                p.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    synchronized void send(String s, int x){
        for(int i=0;i<users.size();i++){
            if(i==x) continue;
            users.get(i).send(s);
        }
    }

}

class process extends Thread{
    Socket ss;
    ObjectOutputStream oout;
    ObjectInputStream oin;
    Server ser;
    int index;
    boolean mark = true;

    process(Socket s,Server x) {
        ss = s;
        ser = x;
    }

    public void run(){
        try {
            oout = new ObjectOutputStream(ss.getOutputStream());
            oout.flush();
            oin = new ObjectInputStream(ss.getInputStream());
            index = Server.list.size();
            Server.list.add(ss.getInetAddress().getHostName());
            while(true && mark){
                String s = (String)oin.readObject();
                System.out.println(ss.getInetAddress().getHostName()+">>"+s);
                ser.send(ss.getInetAddress().getHostName()+">>"+s, index);
                if(s.equals("915786423"))
                    mark = false;
            }
        }catch(SocketException sux){
            Server.list.remove(index);
            System.out.println("One Logged out");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void send(String s){
        try {
            ajairaData dd = new ajairaData(Server.list,s);
            oout.writeObject(dd);
            oout.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}