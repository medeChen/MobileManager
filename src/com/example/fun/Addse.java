package com.example.fun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
public class Addse {  
  
   
//    public static void main(String[] args) throws IOException {  
////       lockImage("d:/1.jpg", "d:/2.jpg", 33);  
//        unLockImage("C:/Users/user/Desktop/6.jpg", "C:/Users/user/Desktop/7.jpg", 2);  
//    }  
  
 
    public static void lockImage(String file_path, String save_path,  
            int lock_num) throws IOException {  
        File soure = new File(file_path);  
        File save = new File(save_path);  
        lockImage(soure, save, lock_num);  
    }  
  
    
    public static void lockImage(File soure_file, File save_file, int lock_num)  
            throws IOException {  
        FileInputStream fis = new FileInputStream(soure_file);  
        FileOutputStream fos = new FileOutputStream(save_file);  
        int b = 0;  
        while ((b = fis.read()) != -1) {  
            fos.write(b - lock_num);  
            fos.flush();  
        }  
        fos.close();  
        fis.close();  
        System.out.println("success");  
    }  
  
     
    public static void unLockImage(String file_path, String save_path,  
            int unlock_num) throws IOException {  
        File soure = new File(file_path);  
        File save = new File(save_path);  
        unLockImage(soure, save, unlock_num);  
    }  
   
    public static void unLockImage(File soure_file, File save_file,  
            int unlock_num) throws IOException {  
        FileInputStream fis = new FileInputStream(soure_file);  
        FileOutputStream fos = new FileOutputStream(save_file);  
        int b = 0;  
        while ((b = fis.read()) != -1) {  
            fos.write(b + unlock_num);  
            fos.flush();  
        }  
        fos.close();  
        fis.close();  
        System.out.println("success");  
    }  
}  