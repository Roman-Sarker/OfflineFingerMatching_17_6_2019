
package com.era.AllCustNo;

/**
 *
 * @author roman
 */

import com.era.AllAgentNo.UserParamBean;
import java.sql.*;
import java.util.*;
import com.era.FingerCheck.*;

public class AllCustNo {
    
    public static List<Integer> custNo(UserParamBean modal, String pStandard ){
        
        Connection con = null;
        List<Integer> listStrCust = new ArrayList<Integer>();
            //System.out.println("modal.getCustNo() :"+ (modal.getCustNo())+" (Expected is 0)");
            //System.out.println("modal.getAgentPoint() :"+ (modal.getAgentPoint()));
        int custNo = 0;
        custNo = modal.getCustNo();
            
        int pointNo = 0;
        pointNo = modal.getAgentPoint();
        try{
            con = DbConnectivity.getConnection();
            CallableStatement cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_CUST_CUST_NO_DYN(?,?,?,?); end;");
            cs.setString(1, pStandard);
            cs.setInt(2, custNo);
            cs.setInt(3, pointNo);
            cs.registerOutParameter(4, java.sql.Types.ARRAY, "CUS_NO_LIST");
            cs.execute();
         
            Array arrCusNo = cs.getArray(4);
            
            Map map = con.getTypeMap();
            map.put("CUSTOMER_NO_LIST", Class.forName("com.era.FingerCheck.TestArr"));
           
            Object[] values = (Object[]) arrCusNo.getArray(); /// Customer No. here.
           
           for (int i = 0; i < values.length; i++) {
                TestArr a = (TestArr) values[i];
                listStrCust.add(a.attrOne); 
            }    
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Error is:"+ ex);
            System.out.println("Exception raised at Retrieve Customer No. Error is : "+ ex);
        }
        finally
        {
            DbConnectivity.releaseConnection(con);
        }
         //System.out.println("---Total ("+pStandard+") Customer's : "+listStrCust.size());
     return listStrCust;       
    }
    
// main function for testing this class.    
     public static void main(String[] args) {
        List<Integer> listStrCust = AllCustNo.custNo(null,"S");
        System.out.println(listStrCust);        //Old 231, 120, 131, 1559, 42083, 44232, 114114
    }
    
}
//258534