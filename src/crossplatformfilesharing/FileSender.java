/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crossplatformfilesharing;

import java.io.*;
import java.net.*;

public class FileSender extends Thread{
    File f;
    Socket sock;
    ServerSocket server;
    ObjectInputStream oin;
    ObjectOutputStream oout;
    RandomAccessFile rf;
    CrossPlatformFileSharingView xx;
    FileSender(File x,CrossPlatformFileSharingView pp){
        f= x;
        xx = pp;
        try {
            rf = new RandomAccessFile(f, "rw");
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        try{
            server = new ServerSocket(10000);
            while(true){
                sock = server.accept();
                process();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void process() throws Exception{
        oout = new ObjectOutputStream(sock.getOutputStream());
        oout.flush();
        oin  = new ObjectInputStream(sock.getInputStream());
        byte ary[] = new byte[1024*100];
        int tmp;
        int i=0;
        do{
            tmp = getBytes(ary,i);
            if(tmp<=0)
                break;
            file x= new file(i,tmp,ary);
            i+=tmp;
            send(x);
        }while(true);

        oout.writeObject(new file(0,0,ary));
        oout.flush();
        oout.close();
        oin.close();
        rf.close();
        sock.close();
        xx.ConfirmPopup("Vai file ta gesega.. Amar kam shesh");
    }

    void send(file x){
        try {
            oout.writeObject(x);
            oout.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    int getBytes(byte[] ary,int start){
        int x=0;
        try {
            rf.seek(start);

            x =  rf.read(ary, 0, ary.length);
            System.out.println("start "+start+" length "+x+" first "+ary[0]);
        } catch (IOException ex) {
            return 0;
        }
        return x;
    }
}

class file implements Serializable{
    int start;
    int length;
    byte[] ary;
    file(int x, int y, byte[] a){
        super();
        start = x;
        length = y;
        ary = a.clone();
    }

}