package com.era.nonIso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Vector;

public class FileRead {

    public FingerData getFingerDataFromFile(int pCustNo, int pImageNo) {
        FingerData fingerData = new FingerData();
        String vCustNo = String.valueOf(pCustNo);
        int folderNo = pCustNo/20000;  
        File file1 = null;
        FileInputStream fs = null;
        try {
            String szFileName = "/u01/J2EE/Database/"+folderNo+"/"+vCustNo+pImageNo+".tml";
            file1 = new File(szFileName);
            fs = null;
            long nFileSize;

            if (file1.exists()) {
                nFileSize = file1.length();
                fs = new FileInputStream(file1);
                CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                byte[] DataSize = new byte[4];
                fs.read(DataSize);

                byte[] m_Key = new byte[16];
                fs.read(m_Key);

                String m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
                //System.out.println(m_UserName);

                byte[] m_Template = new byte[(int) nFileSize - 20];
                fs.read(m_Template);
                fs.close();

                fingerData.setM_Key(m_Key);
                fingerData.setM_UserName(m_UserName);
                fingerData.setM_Template(m_Template);
                
                //System.out.println("m_Key :"+ m_Key);
                //System.out.println("m_UserName :"+ m_UserName);
                //System.out.println("Length of finger Template : "+m_Template.length);
                return fingerData;

            } else {
                System.out.println("Finger not found for :"+ pCustNo);
                return null;
            }
        }catch(IOException ioe){
            System.out.println("Exception raised to Read *.tml data in IOException. Error is :" + ioe);
            return null;
        } 
        catch (Exception ex) {
            System.out.println("Exception raised to Read *.tml data. Error is :" + ex);
            return null;
        }finally{
            
        }

        
    }
    
// Main() method for testing purpose
//    public static void main(String[] args) {
//        try {
//            boolean restul = false;
//            FingerMatching fingerMatching = new FingerMatching();
////1141.tml
//            FileRead fileRead = new FileRead();
//            FingerData fingerData1 = fileRead.getFingerDataFromFile(114, 1);
//            FingerData fingerData2 = fileRead.getFingerDataFromFile(114, 1);
//
//            //System.out.println(fingerData1.getM_Template().equals(fingerData2.getM_Template())) ; 
//            if (fingerData1 == null) {
//                System.out.println("fingerData1 data not found.");
//            } else if (fingerData2 == null) {
//                System.out.println("fingerData2 data not found.");
//            } else {
//                restul = fingerMatching.matchFingerData(fingerData1, fingerData2);
//            }
//
//            if (restul) {
//                System.out.println("Data Match");
//            } else {
//                System.out.println("Matching Failed");
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }

}
