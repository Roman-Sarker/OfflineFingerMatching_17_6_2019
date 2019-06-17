
package com.era.AllAgentNo;

/**
 *
 * @author roman
 */
public class UserParamBean {
    private int agentPoint, custNo;
    private String userType, allCustomer;;

    public String getUserType() {
        return userType;
    }

    public int getAgentPoint() {
        return agentPoint;
    }

    public void setAgentPoint(int agentPoint) {
        this.agentPoint = agentPoint;
    }

    public int getCustNo() {
        return custNo;
    }

    public void setCustNo(int custNo) {
        this.custNo = custNo;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    
    public String getAllCustomer() {
        return allCustomer;
    }

    public void setAllCustomer(String allCustomer) {
        this.allCustomer = allCustomer;
    }


    
    
}
