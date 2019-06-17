package com.era.AllAgentNo;

import com.era.AllCustNo.*;
import com.era.FingerCheck.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import process.matchData.Bean;
import process.matchData.FingerMatchIsoToIsoFTR;
import process.matchData.FingerMatchIsoToIsoSG;
import process.matchData.GetFingerData;
import process.matchData.MatchFingerInsert;

public class AllAgentNo {

    public void receiveAgentCusNo(UserParamBean modal, String pStandard) throws SQLException {  //List<String> // HashMap<Integer, Integer>// HashMap<Integer, TreeSet<Integer>>
        AllCustNo allCustNo = new AllCustNo();

        // Declaration Part
        Connection con = null;
        //CallableStatement cs = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        int matchCount = 0;
        int UnMatchCount = 0;
        int custProcessCount = 0;

        List<Integer> listIntCust = allCustNo.custNo(modal, pStandard);  // Receive all Customer No as String
        int custNo = listIntCust.get(0);
        System.out.println("listIntCust.get(0) : " + custNo);
        // Receive Agent Or ARO no
        ReturnUsersNo returnUsersNo = new ReturnUsersNo();
        List<Integer> listIntUser = new ArrayList<Integer>();

        if (modal.getUserType().equals("agent") || modal.getUserType() == "agent") {
            listIntUser = returnUsersNo.agentUsers(pStandard, custNo); // Receive agent no list 
            System.out.println("Total Agent : " + listIntUser.size());
        } else if (modal.getUserType().equals("aro") || modal.getUserType() == "aro") {
            listIntUser = returnUsersNo.aROUsers(pStandard, custNo);  // Receive ARO list
            System.out.println("Total ARO : " + listIntUser.size());
        } else if (modal.getUserType().equals("all") || modal.getUserType() == "all") {
            listIntUser = returnUsersNo.agentAroUsers(pStandard, custNo); // Receive Agent + ARO list
            System.out.println("Total Agent + ARO : " + listIntUser.size());
        }
        // End Agent Or ARO no

        try {

            con = DbConnectivity.getConnection();
            System.out.println("---Total (" + pStandard + ") Customer : " + listIntCust.size());
            // System.out.println("Retrieve agent+ARO cust no of "+listIntCust.size()+" Customer.");

            int count = 0;
            if (listIntUser.size() >= 1) {
                for (int c = 0; c < listIntCust.size(); c++) { // Loop for change Customer

                    System.out.println("--Customer Number :" + (c + 1));

                    ///-----Start retrieve customer finger data
                    // mapAgentCustNo.put(listIntCust.get(c), listIntUser); // Arrays.asList(listIntUser)
                    // if (listIntUser.size() >= 1) {         // This condition check agent are available or not for specific customer
                    HashMap<String, byte[]> custAvaFPmap = new HashMap<String, byte[]>(); // -- Customer finger in Map
                    System.out.println("===Start Matching for Customer No : " + listIntCust.get(c) + ", And his total Agent is : " + listIntUser.size() + "===");

                    try {
                        String cQuery = "SELECT LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                                + "RMIDDLE,RRING,RLITTLE from  Biotpl.FP_ENROLL where  CUST_NO =" + listIntCust.get(c); // getKey() is for Key value of Map. (Simple value is : 258534)
                        pstmt = con.prepareStatement(cQuery);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            byte[] LINDEX = getByteDataFromBlob(rs.getBlob("LINDEX"));
                            byte[] LTHUMB = getByteDataFromBlob(rs.getBlob("LTHUMB"));
                            byte[] RINDEX = getByteDataFromBlob(rs.getBlob("RINDEX"));
                            byte[] RTHUMB = getByteDataFromBlob(rs.getBlob("RTHUMB"));

                            byte[] LMIDDLE = getByteDataFromBlob(rs.getBlob("LMIDDLE"));
                            byte[] LRING = getByteDataFromBlob(rs.getBlob("LRING"));
                            byte[] LLITTLE = getByteDataFromBlob(rs.getBlob("LLITTLE"));

                            byte[] RMIDDLE = getByteDataFromBlob(rs.getBlob("RMIDDLE"));
                            byte[] RRING = getByteDataFromBlob(rs.getBlob("RRING"));
                            byte[] RLITTLE = getByteDataFromBlob(rs.getBlob("RLITTLE"));

                            if (RLITTLE != null) {
                                custAvaFPmap.put("RLITTLE", RLITTLE);
                            }
                            if (LLITTLE != null) {
                                custAvaFPmap.put("LLITTLE", LLITTLE);
                            }
                            if (LRING != null) {
                                custAvaFPmap.put("LRING", LRING);
                            }
                            if (LMIDDLE != null) {
                                custAvaFPmap.put("LMIDDLE", LMIDDLE);
                            }
                            if (RTHUMB != null) {
                                custAvaFPmap.put("RTHUMB", RTHUMB);
                            }
                            if (RINDEX != null) {
                                custAvaFPmap.put("RINDEX", RINDEX);
                            }
                            if (LTHUMB != null) {
                                custAvaFPmap.put("LTHUMB", LTHUMB);
                            }
                            if (LINDEX != null) {
                                custAvaFPmap.put("LINDEX", LINDEX);
                            }
                            if (RRING != null) {
                                custAvaFPmap.put("RRING", RRING);
                            }
                            if (RMIDDLE != null) {
                                custAvaFPmap.put("RMIDDLE", RMIDDLE);
                            }

                            System.out.println("Total Finger of this customer(" + listIntCust.get(c) + ") is :" + custAvaFPmap.size());
//                        for(int r = 0; r<CustListData.size(); r++){
//                            System.out.println(CustListData.get(r)); // System.out.println("buffer : "+Base64.getEncoder().encodeToString(AllData.get(0)));
//                        }
//                        CustListData = new ArrayList<byte[]>() {};
                        }
                    } catch (Exception e) {
                        System.out.println("Exception raised at Retrieve all finger of Customer. Error is : " + e);
                        e.printStackTrace();
                    } finally {
                        try {
                            rs.close();
                            System.out.println("Cus rs clossed.");
                        } catch (Exception e) {
                            System.out.println("Exception raised to close all rs of Customer in 'AllAgentNo.java' class. Error is :" + e);
                        }
                        try {
                            pstmt.clearParameters();
                            System.out.println("Cus pstmt cleared.");
                        } catch (Exception e) {
                            System.out.println("Exception raised clear all parameter pstmt of Customer in 'AllAgentNo.java' class. Error is :" + e);
                        }
                        try {
                            pstmt.close();
                            System.out.println("Cus pstmt clossed.");
                        } catch (Exception e) {
                            System.out.println("Exception raised to close all pstmt of Customer in 'AllAgentNo.java' class. Error is :" + e);
                        }
                        //try {cs.close(); System.out.println("Cus cs clossed.");} catch (Exception e) {System.out.println("Exception raised to clear all cs of Customer in 'AllAgentNo.java' class. Error is :" + e);}
                    }

                    // Agent User finger data --------------------------------------------------------------
                    //System.out.println("------ Agent of " + entry.getKey() + " ------");
                    for (int a = 0; a < listIntUser.size(); a++) { // This loop change next Agent of specific Customer
                        System.out.println("Agent Number : " + (a + 1));
                        HashMap<String, byte[]> agentAvaFPmap = new HashMap<String, byte[]>(); // -- Agent finger in Map

                        try {

                            //String aQuery = "SELECT DISTINCT CUST_NO, RLITTLE, LLITTLE, LRING, LMIDDLE, RTHUMB, RINDEX, LTHUMB, LINDEX, RRING, RMIDDLE, STANDARD  from FP_ENROLL where CUST_NO =  " + entry.getValue().get(c); 
                            String aQuery = "SELECT CUST_NO, RLITTLE, LLITTLE, LRING, LMIDDLE, RTHUMB, RINDEX, LTHUMB, LINDEX, RRING, RMIDDLE, STANDARD  from FP_ENROLL where CUST_NO = " + listIntUser.get(a); //  337003
                            pstmt1 = con.prepareStatement(aQuery);
                            rs1 = pstmt1.executeQuery();

                            while (rs1.next()) {
                                byte[] aRthumb = getByteDataFromBlob(rs1.getBlob("RTHUMB"));
                                byte[] aRindex = getByteDataFromBlob(rs1.getBlob("RINDEX"));
                                byte[] aMiddle = getByteDataFromBlob(rs1.getBlob("RMIDDLE"));
                                byte[] aRring = getByteDataFromBlob(rs1.getBlob("RRING"));
                                byte[] aRlittle = getByteDataFromBlob(rs1.getBlob("RLITTLE"));
                                byte[] aLthumb = getByteDataFromBlob(rs1.getBlob("LTHUMB"));
                                byte[] aLindex = getByteDataFromBlob(rs1.getBlob("LINDEX"));
                                byte[] aLmiddle = getByteDataFromBlob(rs1.getBlob("LMIDDLE"));
                                byte[] aLring = getByteDataFromBlob(rs1.getBlob("LRING"));
                                byte[] aLlittle = getByteDataFromBlob(rs1.getBlob("LLITTLE"));

                                //System.out.println(entry.getValue().get(c)+" - "+ cRthumb);
                                if (aRlittle != null) {
                                    agentAvaFPmap.put("RLITTLE", aRlittle);
                                }
                                if (aLlittle != null) {
                                    agentAvaFPmap.put("LLITTLE", aLlittle);
                                }
                                if (aLring != null) {
                                    agentAvaFPmap.put("LRING", aLring);
                                }
                                if (aLmiddle != null) {
                                    agentAvaFPmap.put("LMIDDLE", aLmiddle);
                                }
                                if (aRthumb != null) {
                                    agentAvaFPmap.put("RTHUMB", aRthumb);
                                }
                                if (aRindex != null) {
                                    agentAvaFPmap.put("RINDEX", aRindex);
                                }
                                if (aLthumb != null) {
                                    agentAvaFPmap.put("LTHUMB", aLthumb);
                                }
                                if (aLindex != null) {
                                    agentAvaFPmap.put("LINDEX", aLindex);
                                }
                                if (aRring != null) {
                                    agentAvaFPmap.put("RRING", aRring);
                                }
                                if (aMiddle != null) {
                                    agentAvaFPmap.put("RMIDDLE", aMiddle);
                                }

                                System.out.println("Total Finger of this Agent(" + listIntUser.get(a) + ") is :" + agentAvaFPmap.size() + " X " + custAvaFPmap.size());
                            }

                        } catch (Exception e) {
                            System.out.println("Exception raised to retrieve customer finger data. Error is : " + e);
                            e.printStackTrace();
                        } finally {
                            try {
                                rs1.close();
                                System.out.println("Agent rs1 clossed.");
                            } catch (Exception e) {
                                System.out.println("Exception raised to close all rs1 of Customer in 'AllAgentNo.java' class. Error is :" + e);
                            }
                            try {
                                pstmt1.clearParameters();
                                System.out.println("Agent pstmt1 cleared.");
                            } catch (Exception e) {
                                System.out.println("Exception raised clear all parameter pstmt1 of Customer in 'AllAgentNo.java' class. Error is :" + e);
                            }
                            try {
                                pstmt1.close();
                                System.out.println("Agent pstmt1 clossed.");
                            } catch (Exception e) {
                                System.out.println("Exception raised to close all pstmt1 of Customer in 'AllAgentNo.java' class. Error is :" + e);
                            }

                        }

                        //------ Check Customer and Agent Finger data . Is all data comes correctly or not?
                        int customerId = listIntCust.get(c);
                        int AgentId = listIntUser.get(a);
//                                   System.out.println("Cust Id :"+customerId);
//                                   System.out.println("Agent Id :"+AgentId);

                        //--------------------------------------------------------------------------
                        //---------------------------- Start matching (Matching-Zone)--------------------------
//                                for (int c = 0; c < custAvaFP.size(); c++) {
//                                    for (int a = 0; a < agentAvaFP.size(); a++) {
                        // System.out.println("Customer total finger's : " + custAvaFPmap.size());
                        // System.out.println("Agent total finger's : " + agentAvaFPmap.size());
                        for (Map.Entry<String, byte[]> custEntry : custAvaFPmap.entrySet()) { //* clear it  //Loop for change customer finger
                            for (Map.Entry<String, byte[]> agentEntry : agentAvaFPmap.entrySet()) {//* clear it // Loop for change agent finger
//                                        System.out.println(agentAvaFP.get(a)); // System.out.println("buffer : "+Base64.getEncoder().encodeToString(AllData.get(0)));
//                                        System.out.println(custAvaFP.get(c));

                                byte[] bCustFP = null;
                                bCustFP = custEntry.getValue();
                                byte[] bAgentFP = null;
                                bAgentFP = agentEntry.getValue();
                                String CustFpName = custEntry.getKey();
                                String agentFpName = agentEntry.getKey();
//                                        System.out.println("CustFPNo = "+ c +"| AgentFpNo = "+ a);
                                // System.out.println("Cust_id: " + customerId + ". Cust Finger: " + CustFpName + " || Agent_Id: " + AgentId + ". Agent Finger: " + agentFpName);

                                //*** Call Matching function (This Secugen matching Engine)
                                //byte[][] data = new byte[1][];
                                //data[0] = bCustFP;
                                //FingerMatchIsoToIsoSG fingerMatchIsoToAnsi = new FingerMatchIsoToIsoSG();
                                //boolean matchingFlag = fingerMatchIsoToAnsi.fingerPrintIndetify(bAgentFP, data, Integer.toString(customerId));
                                //*** Call Matching function (This Futronic matching Engine)
                                FingerMatchIsoToIsoFTR fingerMatchIsoToIsoFTR = new FingerMatchIsoToIsoFTR();
                                boolean matchingFlag = false;
                                try {
                                    matchingFlag = fingerMatchIsoToIsoFTR.FingerprintVerify(bCustFP, bAgentFP);
                                    if (matchingFlag) {
//                                            
                                        System.out.println("***** Finger Matched. Customer(" + customerId + " - " + CustFpName + ") Vs Agent(" + AgentId + " - " + agentFpName + ") *****");
                                        MatchFingerInsert.insertFinger(customerId, AgentId, agentFpName, CustFpName, pStandard);
                                        //System.out.println(entry.getKey()+" - " + entry.getValue().get(c)+" ("+ custFpNum +" - "+agentFpNum+")");
                                        matchCount = matchCount + 1;

                                    } else {
                                        UnMatchCount = UnMatchCount + 1;
                                        //System.out.println("matchingFlag = " + matchingFlag);
                                        System.out.println("## Finger does not Matched for Customer(" + customerId + " - " + CustFpName + ") Vs Agent(" + AgentId + " - " + agentFpName + ") ##");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception raised to match finger data Or Insert matched finger data. Error is : " + e);
                                }

                            }
                            System.out.println("...");
                        }
                        //--------------------------------------------End of matchig zone

                    }// End loop change next Agent of specific Customer

                    /*
                } else {
                    System.out.println("No Standard(S) agent is found for Customer :" + listIntCust.get(c));
                }*/
                    ///-----End retrieve customer finger data
                    count++;

                } // End for loop for customer
            } else {
                System.out.println("No Standard(S) agent is found agent point no :" + modal.getAgentPoint());
            }
        } catch (Exception ex) {
            System.out.println("Exception raised at Retrieve Agent no in 'AllAgentNo' class. Error is : " + ex);
            ex.printStackTrace();
        } finally {
            con.close();
        }
        //System.out.println("---Total ("+pStandard+") Customer : "+mapAgentCustNo.size());
    }

    //--------
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

    // Main method for testing this class   
    public static void main(String[] args) throws SQLException {

        AllAgentNo allAgentNo = new AllAgentNo();
        allAgentNo.receiveAgentCusNo(null, "S");

    }
}
//258534
