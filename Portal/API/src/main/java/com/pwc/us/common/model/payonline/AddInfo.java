/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model.payonline;

public class AddInfo {

    private String addressLine1="";
    private String employerCity="";
    private String empName="";
    private String employerState="";
    private String employerZip="";
    private String empPhone="";

    public AddInfo(String addressLine1, String employerCity, String empName, String employerState, String employerZip, String empPhone) {
        this.addressLine1 = addressLine1 == null ? "" : addressLine1;
        this.employerCity = employerCity == null ? "" : employerCity;
        this.empName = empName == null ? "" : empName;
        this.employerState = employerState == null ? "" : employerState;
        this.employerZip = employerZip == null ? "" : employerZip;
        this.empPhone = empPhone == null ? "" : empPhone;
    }
    
    public AddInfo()
    {
        
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getEmployerCity() {
        return employerCity;
    }

    public void setEmployerCity(String employerCity) {
        this.employerCity = employerCity;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmployerState() {
        return employerState;
    }

    public void setEmployerState(String employerState) {
        this.employerState = employerState;
    }

    public String getEmployerZip() {
        return employerZip;
    }

    public void setEmployerZip(String employerZip) {
        this.employerZip = employerZip;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

}
