/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webservice.client;

import com.pwc.us.common.model.CertificateOfInsuranceRequest;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class COIClientTester {

    public static void main(String[] args) {
        
        boolean response;
        
        try {
            CertificateOfInsuranceRequest request = new CertificateOfInsuranceRequest();
            COIRequestBuilder client = new COIRequestBuilder();
            response = client.processRequest(request);
            System.out.println("The response is" + response);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

}
