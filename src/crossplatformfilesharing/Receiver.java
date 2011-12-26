/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crossplatformfilesharing;

import java.io.*;
import java.net.*;


class Receiver extends Thread{
    Socket sock;
    ObjectOutputStream oout;
    ObjectInputStream oin;
    RandomAccessFile rf;
    String ii;
    int pp;
    File ff;
    CrossPlatformFileSharingView xx;
    Receiver(String ip, int port,File f,CrossPlatformFileSharingView cc){
        ii = ip;
        pp =port;
        ff = f;
        xx = cc;
        try {
            rf = new RandomAccessFile(ff, "rw");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
//            Logger.getLogger(receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        try{
            System.out.println("socked");
           sock = new Socket(ii,pp);
            oout = new ObjectOutputStream(sock.getOutputStream());
            oout.flush();

            oin = new ObjectInputStream(sock.getInputStream());
            System.out.println("read ");
            rf.setLength(0);
            do{
                System.out.println("read ");
                file f = (file)oin.readObject();
                if(f.length <=0)
                    break;
                write(f);
            }while(true);
            oout.close();
            oin.close();
            rf.close();
            sock.close();
            xx.ConfirmPopup("Haua file namano shesh!!!");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void write(file x) throws IOException{
        rf.seek(x.start);

        rf.write(x.ary,0,x.length);

    }
}