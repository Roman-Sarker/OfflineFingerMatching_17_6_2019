package com.era.AllAgentNo;

import com.era.FingerCheck.DbConnectivity;
import com.era.FingerCheck.TestArr;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public class ReturnUsersNo {

    // Return Agent users List
    public List<Integer> agentUsers(String pStandard, int custNo) {

        List<Integer> listIntAgent = new ArrayList<Integer>();
        Connection con = DbConnectivity.getConnection();
        CallableStatement cs = null;

        try {
            
            //CallableStatement cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO2(?,?,?); end;");
            cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO(?,?,?); end;");
            cs.setInt(1, custNo); // Cust No  //cs.setString(1, listStrCust.get(c));
            cs.setString(2, pStandard);         // Standard
            cs.registerOutParameter(3, java.sql.Types.ARRAY, "CUS_NO_LIST"); // Agent No of that cust no
            cs.execute();

            Array arrAgentNo = cs.getArray(3);
            Map map = con.getTypeMap(); // ** map.clear();
            map.put("CUSTOMER_NO_LIST", Class.forName("com.era.FingerCheck.TestArr"));
            Object[] values = (Object[]) arrAgentNo.getArray();//** Close it

            //List<String> values = (List<String>) arrAgentNo.getArray();
            for (int k = 0; k < values.length; k++) { // Convert to Integer type by TestArr class
                TestArr a = (TestArr) values[k];
                listIntAgent.add(a.attrOne);
            }
        } catch (Exception e) {
            System.out.println("Exception raised at call 'BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO(?,?,?)' procedure. Error is : " + e);
            e.printStackTrace();
        } finally {
            try {
                cs.clearParameters();
                cs.close();
                con.close();
                //System.out.println("cs Clossed Successfully.");
            } catch (Exception e) {
                System.out.println("Exception raised at clear and close CallableStatement (cs). Error is : " + e);
                e.printStackTrace();
            }
        }

        return listIntAgent;
    }

    // Return ARO list
    public List<Integer> aROUsers(String pStandard, int custNo) {

        List<Integer> listIntAgent = new ArrayList<Integer>();
        Connection con = DbConnectivity.getConnection();
        CallableStatement cs = null;

        try {
            //CallableStatement cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO2(?,?,?); end;");
            cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_ARO_CUST_NO(?,?); end;");
            cs.setString(1, pStandard);         // Standard
            cs.registerOutParameter(2, java.sql.Types.ARRAY, "CUS_NO_LIST"); // Agent No of that cust no
            cs.execute();

            Array arrAgentNo = cs.getArray(2);
            Map map = con.getTypeMap(); // ** map.clear();
            map.put("CUSTOMER_NO_LIST", Class.forName("com.era.FingerCheck.TestArr"));
            Object[] values = (Object[]) arrAgentNo.getArray();//** Close it

            //List<String> values = (List<String>) arrAgentNo.getArray();
            for (int k = 0; k < values.length; k++) { // Convert to Integer type by TestArr class
                TestArr a = (TestArr) values[k];
                listIntAgent.add(a.attrOne);
            }
        } catch (Exception e) {
            System.out.println("Exception raised at call 'BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO(?,?,?)' procedure. Error is : " + e);
            e.printStackTrace();
        } finally {
            try {
                cs.clearParameters();
                cs.close();
                con.close();
                System.out.println("cs Clossed Successfully.");
            } catch (Exception e) {
                System.out.println("Exception raised at clear and close CallableStatement (cs). Error is : " + e);
                e.printStackTrace();
            }
        }

        return listIntAgent;
    }

    // Return Agent+ARO users list
    public List<Integer> agentAroUsers(String pStandard, int custNo) {

        List<Integer> listIntAgent = new ArrayList<Integer>();
        Connection con = DbConnectivity.getConnection();
        CallableStatement cs = null;

        try {
            //CallableStatement cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO2(?,?,?); end;");
            cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_ARO_CUST_NO(?,?,?); end;");
            cs.setInt(1, custNo); // Cust No  //cs.setString(1, listStrCust.get(c));
            cs.setString(2, pStandard);         // Standard
            cs.registerOutParameter(3, java.sql.Types.ARRAY, "CUS_NO_LIST"); // Agent No of that cust no
            cs.execute();

            Array arrAgentNo = cs.getArray(3);
            Map map = con.getTypeMap(); // ** map.clear();
            map.put("CUSTOMER_NO_LIST", Class.forName("com.era.FingerCheck.TestArr"));
            Object[] values = (Object[]) arrAgentNo.getArray();//** Close it

            //List<String> values = (List<String>) arrAgentNo.getArray();
            for (int k = 0; k < values.length; k++) { // Convert to Integer type by TestArr class
                TestArr a = (TestArr) values[k];
                listIntAgent.add(a.attrOne);
            }
        } catch (Exception e) {
            System.out.println("Exception raised at call 'BIOTPL.GET_CUSTOMER_LIST2.GET_AGENT_CUST_NO(?,?,?)' procedure. Error is : " + e);
            e.printStackTrace();
        } finally {
            try {
                cs.clearParameters();
                cs.close();
                con.close();
                System.out.println("cs Clossed Successfully.");
            } catch (Exception e) {
                System.out.println("Exception raised at clear and close CallableStatement (cs). Error is : " + e);
                e.printStackTrace();
            }
        }

        return listIntAgent;
    }
}
