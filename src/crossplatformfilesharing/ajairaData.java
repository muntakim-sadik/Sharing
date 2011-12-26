/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crossplatformfilesharing;

import java.util.*;
import java.io.*;
/**
 *
 * @author paagol
 */
public class ajairaData implements Serializable {
    ArrayList<String> list;
    String msg;
    ajairaData(ArrayList<String> s1,String s3){
        list = s1;
        msg = s3;
    }
}
