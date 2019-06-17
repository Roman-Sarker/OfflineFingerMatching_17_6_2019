
package com.era.nonIso;
import com.era.FingerCheck.*;
import java.sql.*;

public class FingerCheckInDB {
    public int twoFingerAvailableStatus(int pCustNo){
            int yCount = 0;
            Connection con = null;
        try{
            String vFinger1 = null;
            String vFinger2 = null;
            
            String sql = "SELECT FINGER1, FINGER2 FROM FP_ENROLL WHERE CUST_NO = "+ pCustNo;
            
            con = DbConnectivity.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                vFinger1 = rs.getString("FINGER1");
                vFinger2 = rs.getString("FINGER2");
            }
            if(vFinger1.equals("Y")){
                //System.out.println("Finger1 is got. ("+vFinger1+")");
                yCount++; 
            }
            if(vFinger2.equals("Y")){
                yCount++; 
            }
            
        }
        catch(SQLException ex){
            System.out.println("Exception raised to retrive Y count from database in 'FingerCheckInDB.java' class.");
            System.out.println("Error is : "+ex);
            ex.printStackTrace();
        }catch(Exception e){
            System.out.println("Exception raised to retrive Y count from database in 'FingerCheckInDB.java' class.");
            System.out.println("Error is : "+e);
            e.printStackTrace();
        }
        finally{
            DbConnectivity.releaseConnection(con);
        }
        
    return yCount;
    }
}
