package com.era.nonIso;

import com.era.AllAgentNo.ReturnUsersNo;
import com.era.AllAgentNo.UserParamBean;
import java.util.List;
import com.era.AllCustNo.*;
import java.util.ArrayList;
import process.matchData.MatchFingerInsert;

/**
 *
 * @author roman
 */
public class MatchOldFpData {

    public void matchingOldFpData(UserParamBean modal, String pStandard) {
        AllCustNo allCustNo = new AllCustNo();

        List<Integer> listIntCust = allCustNo.custNo(modal, pStandard);  // Receive all Customer No as String
        int custNo = listIntCust.get(0); // One customer no of specific Agent Point
        System.out.println("Total Customer :" + listIntCust.size());
        //System.out.println("One customer No of above Agent Point : " + custNo);

        // Receive Agent Or ARO no
        ReturnUsersNo returnUsersNo = new ReturnUsersNo();
        List<Integer> listIntUser = new ArrayList<Integer>();
        
        //System.out.println("Agent Point : "+modal.getAgentPoint());
        
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
        
        //System.out.println("Agent User of Point :" + listIntUser); // Print all user no in above Agent Point

        // Here write code for matching above Customer and Agent Finger Data.
        if (listIntUser.size() > 0) {
            //listIntCust 
            // Now write code here for customer loop and agent loop
            FingerMatching fingerMatching = new FingerMatching();
            String status = "O";
            int custYcount = 0; // Finger1 and Finger2 column value(Y/N) count. If both column Y then 2 else 1
            int userYcount = 0;
            FileRead fileRead = new FileRead();
            FingerData custFingerData = null;
            FingerData userFingerData = null;

            try {
                for (int cNo = 0; cNo < listIntCust.size(); cNo++) {     // Loop change for new Customer (cNo = Customer No)
                    //System.out.println("Retriving Y status for customer(" + listIntCust.get(cNo) + ") from database.");
                    FingerCheckInDB fingerCheckInDB = new FingerCheckInDB(); //** need to close this Object.
                    custYcount = fingerCheckInDB.twoFingerAvailableStatus(custNo); // Check finger Number availibility in directory But match in Database
                    System.out.println("Customer Number : "+ (cNo+1));
                    System.out.println("Customer(" + listIntCust.get(cNo) + ") total finger(Y) : " + custYcount);
                    

                    if (custYcount > 0) {
                        for (int cf = 1; cf <= custYcount; cf++){ // TThis loop for change finger number of specific Customer
                        
                            //C1_Template = null;
                            custFingerData = null;
                            custFingerData = fileRead.getFingerDataFromFile(listIntCust.get(cNo), cf); //Read of Customer Finger Data //258534=[257351]
                            
                            if(custFingerData != null || !custFingerData.equals("")){   // Check customer finger read successfully or Not
                            for (int uNo = 0; uNo < listIntUser.size(); uNo++) {         // This loop for change next agent (uNo = User No)

                                System.out.println("User Number : "+ (uNo+1));
                                userYcount = fingerCheckInDB.twoFingerAvailableStatus(listIntUser.get(uNo));
                                System.out.println("User(" + listIntUser.get(uNo) + ") total finger(Y) : " + userYcount);
                                if (userYcount > 0) {
                                    for (int uf = 1; uf <= userYcount; uf++) {  // uf = agent finger // This loop for change finger number of specific agent

                                        userFingerData = null;
                                        userFingerData = fileRead.getFingerDataFromFile(listIntUser.get(uNo), uf); // Read Agent Finger Data
                                        
                                        if(userFingerData != null || !userFingerData.equals("")){ // Check Aent finger read successfully or Not
                                            boolean result = false;
                                            result = fingerMatching.matchFingerData(custFingerData, userFingerData);
                                            if (result) {
                                            String vCustFpNo = Integer.toString(cf);
                                            String vAgentFpNo = Integer.toString(uf);
                                            byte[] fingerDataForInsert = custFingerData.getM_Template(); // here store for insert this match finger data to database
                                            //System.out.println("Old finger data matched Successfully");
                                            //System.out.println("cust No :" +custNo+ "/ Agent NO :"+ agentNo+"/ CustFinger No :"+cf+"/ AgentFinger No :"+uf+"/ Finger Data :"+custFingerData);
                                            System.out.println("** Old Finger Matched. Customer(" + listIntCust.get(cNo) + " - " + cf + ") Vs Agent(" + listIntUser.get(uNo) + " - " + uf + ") **");
                                            //matchDataInsertToDB.insertMatchValue(custNo, agentNo, vCustFpNo, vAgentFpNo, fingerDataForInsert);
                                            //MatchFingerInsert.updateStatus();
                                            MatchFingerInsert.insertFinger(listIntCust.get(cNo), listIntUser.get(uNo), vAgentFpNo, vCustFpNo, status);
                                        } else {
                                            System.out.println("## Old Finger does not Matched for Customer(" + listIntCust.get(cNo) + " - " + cf + ") Vs Agent(" + listIntUser.get(uNo) + " - " + uf + ") ##");
                                        }
                                        }else{
                                            System.out.println("*** User("+listIntUser.get(uNo)+") Finger is not available in Directory.");
                                        }

                                        //------- Matching Process Start
                                        //System.out.println(custFingerData+"||"+userFingerData);
                                        

                                        //-------
                                    }
                                } else {
                                    System.out.println("Finger not available for User(" + listIntUser.get(uNo) + ").");
                                }

                            }
                            }else{
                                 System.out.println("Finger not available for Customer(" + listIntUser.get(cNo) + ").");
                            }
                            
                        }

                    } else {
                        System.out.println("Finger not available for Customer(" + listIntCust.get(cNo) + ").");
                    }

                }

            } catch (Exception e) {
                System.out.println("Error is :" + e);
            }

        } else {
            System.out.println("Old agent user not found " + modal.getAgentPoint() + " point.");
        }

    }
}
