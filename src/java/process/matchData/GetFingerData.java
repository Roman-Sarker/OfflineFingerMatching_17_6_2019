package process.matchData;

import com.era.AllAgentNo.AllAgentNo;
import com.era.AllAgentNo.UserParamBean;
import com.era.FingerCheck.DbConnectivity;
import com.era.nonIso.MatchOldFpData;
import com.era.nonIso.OldFutronicData;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.futronictech.AnsiSDKLib;
//import static com.era.AllAgentNo.AllAgentNo.receiveAgentCusNo;

/**
 * @author roman
 */
public class GetFingerData extends AllAgentNo {

    public static void matchFingerData(String standard, UserParamBean modal) {
//        standard = "S";
        try {
            System.out.println("Inside matchFingerData Class..");
            if (standard.equals("S")) {
                if(modal.getAgentPoint() !=0  || modal.getCustNo() != 0){ // Here 0 means Agent Point Or Cust no is not selected
                    
                    System.out.println("Process for Agent Point* or Specific Customer*.");
                    AllAgentNo allAgentNo = new AllAgentNo();
                    allAgentNo.receiveAgentCusNo(modal,standard);
                }else if(modal.getAllCustomer().equals("all") || modal.getAllCustomer() == "all"){
                    // Go proccess for All customer of all agent point.
                    System.out.println(" Process for All Customer. ** ");
                   ProcessForAllCust processForAllCust = new ProcessForAllCust();
                   processForAllCust.MatchallCust(modal, standard);
                }
                
                
                //StandaredFPData.methodIsoFP(modal);
                System.out.println("---*** Process finished for Standard Customer. ***---");
            }               // * Start for Old finger data.
            else if(standard.equals("O"))
            {
                if(modal.getAgentPoint() !=0  || modal.getCustNo() != 0){ // Here 0 means Agent Point Or Cust no is not selected
                    
                    System.out.println("Process for Agent Point* or Specific Customer*.");
                    MatchOldFpData matchOldFpData = new MatchOldFpData();
                    matchOldFpData.matchingOldFpData(modal, standard);
                    
                }else if(modal.getAllCustomer().equals("all") || modal.getAllCustomer() == "all"){
                    // Go proccess for All customer of all agent point.
                    System.out.println(" Process for All Customer. ** ");
                   ProcessForAllCust processForAllCust = new ProcessForAllCust();
                   processForAllCust.MatchallCust(modal, standard);
                }
                System.out.println("---*** Process finished for Old Customer.***---");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
// Convert BLOB data to byte format

    static byte[] getByteDataFromBlob(Blob blob) {
        if (blob != null) {
            try {
                return blob.getBytes(1, (int) blob.length());
            } catch (SQLException ex) {
                Logger.getLogger(GetFingerData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    static byte[][] getNonNullData(byte[][] data) {
        byte[][] nonNullData;
        int index = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                ++index;
            }
        }

        nonNullData = new byte[index][];
        index = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                nonNullData[index++] = data[i];
            }
        }
        return nonNullData;
    }


// match finger data here   

    public static boolean fingerprintVerify(AnsiSDKLib ansi_lib, byte[] dataFromUser, byte[] dataFromDB) {
        float[] matchResult = new float[1];
        float mMatchScore = AnsiSDKLib.FTR_ANSISDK_MATCH_SCORE_MEDIUM;
        boolean matchingFlag = false;

        dataFromUser[10] = 0x00;
        dataFromUser[11] = 0x4D;
        dataFromUser[12] = 0x00;
        dataFromUser[13] = 0x03;
        dataFromUser[14] = 0x00;
        dataFromUser[15] = 0x00;

        if (dataFromUser == null || dataFromDB == null) {
            System.out.println("null data found");
            return false;
        } else if (dataFromUser.length > 500 || dataFromDB.length > 500) {
            System.out.println("abnormal data found");
            System.out.println("dataFromUser length is " + dataFromUser.length);
            System.out.println("dataFromDB length is " + dataFromDB.length);
            return false;
        }

        if (ansi_lib.MatchTemplates(dataFromDB, dataFromUser, matchResult) && matchResult[0] > mMatchScore) {
            String message = String.format("Matchning Score:%f", matchResult[0]);
            System.out.println(message);
            matchingFlag = true;
        } else {
            int lastError = ansi_lib.GetErrorCode();
            if (lastError == AnsiSDKLib.FTR_ERROR_EMPTY_FRAME
                    || lastError == AnsiSDKLib.FTR_ERROR_NO_FRAME
                    || lastError == AnsiSDKLib.FTR_ERROR_MOVABLE_FINGER) {
                String error = String.format("Verification failed. Error: %s.", ansi_lib.GetErrorMessage());
                System.out.println(error);
                matchingFlag = false;
            } else {
                String error = String.format("Verification failed. Error: %s.", ansi_lib.GetErrorMessage());
                System.out.println(error);
                matchingFlag = false;
            }
        }
        return matchingFlag;
    }
    
        // main method for testing purpose
    public static void main(String[] args) {
        try {
            GetFingerData.matchFingerData("S",null);
            //GetFingerData.matchFingerData("O");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}

// 258534 Cus
// 257351 Age
