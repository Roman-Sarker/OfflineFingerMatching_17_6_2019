package process.matchData;

import com.era.AllAgentNo.AllAgentNo;
import com.era.AllAgentNo.UserParamBean;
import com.era.FingerCheck.DbConnectivity;
import com.era.nonIso.MatchOldFpData;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author roman
 */
public class ProcessForAllCust {

    public void MatchallCust(UserParamBean modal, String pStandard) throws SQLException {

        String sql = "SELECT DISTINCT agent_point_id FROM emob.mb_customer_mst WHERE agent_point_id != 0 AND agent_point_id IS NOT NULL";
        //System.out.println("sql = " + sql);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String agentType = modal.getUserType();
        //System.out.println("agentType = " + agentType);
        try {
            
            
            con = DbConnectivity.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            //System.out.println("rs successfully Executed.");
            if(rs != null){
                if(pStandard.equals("S") || pStandard == "S"){
                    while (rs.next()) {
                    modal.setAgentPoint(rs.getInt("agent_point_id")); // Change next agent point
                    System.out.println(" **--Agent Point No : "+ modal.getAgentPoint());
                        AllAgentNo allAgentNo = new AllAgentNo();
                        allAgentNo.receiveAgentCusNo(modal,pStandard);
                    }
                }else if(pStandard.equals("O") || pStandard == "O"){
                    while (rs.next()) {
                    modal.setAgentPoint(rs.getInt("agent_point_id")); // Change next agent point
                    System.out.println(" **--Agent Point No : "+ modal.getAgentPoint());
                        MatchOldFpData matchOldFpData = new MatchOldFpData();
                        matchOldFpData.matchingOldFpData(modal, pStandard);
                    }
                }
                
            }else{
                System.out.println("ResultSet is null for Retrieve Agent Point Id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Exception raised at retriving all Agent Point ID. Error is : "+e);
            e.printStackTrace();
        } finally {
            rs.close();
            pstmt.clearParameters();
            pstmt.close();
            DbConnectivity.releaseConnection(con);
        }

    }

}
