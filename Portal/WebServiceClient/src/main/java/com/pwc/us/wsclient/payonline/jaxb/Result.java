package com.pwc.us.wsclient.payonline.jaxb;

import com.pwc.us.common.model.payonline.OkGovResultPO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Roger
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
    
    @XmlElement
    private String token;
    
    @XmlElement
    private String error;
    
    public String getToken(){
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getError(){
        return this.error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public OkGovResultPO createResultPO() {
        OkGovResultPO resultPO = new OkGovResultPO();
        resultPO.setToken(token);
        resultPO.setError(error);
        return resultPO;
    }
    
}
