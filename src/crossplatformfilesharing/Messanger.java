/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crossplatformfilesharing;

/**
 *
 * @author paagol
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Messanger extends Thread{
    CrossPlatformFileSharingView xx;
    String ip;
    int port;
    Socket sock;
    ObjectOutputStream oout;
    ObjectInputStream oin;
    ajairaData msg;

    Messanger(String i, int p, CrossPlatformFileSharingView cc){
        ip = i;
        port = p;
        xx = cc;
    }

    public void run(){
        try {
            sock = new Socket(ip, port);
            oout = new ObjectOutputStream(sock.getOutputStream());
            oout.flush();
            oin = new ObjectInputStream(sock.getInputStream());
            msg = (ajairaData)oin.readObject();
            while(!msg.msg.equals("915786423")){
                updatao();
                msg = (ajairaData)oin.readObject();
            }
        }catch(SocketException sux){
            return;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void updatao(){
        String tmp= "";
        for(int i=0;i<msg.list.size();i++)
            tmp+=(msg.list.get(i)+'\n');
        xx.hauaList(tmp);
        xx.gejao(msg.msg);
    }

    public void send(String s){
        try {xx.gejao(s);
            oout.writeObject(s);
            oout.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}



