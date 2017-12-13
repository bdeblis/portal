package com.pwc.us.impl;

import com.pwc.us.common.model.OutOfStateAttachments;
import java.io.File;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class OutOfStateTest {
    
    File accord = new File("C:\\Temp\\Acord_130.pdf");
    File supp = new File("C:\\Temp\\UF-17__CSO_Supplemental_App_11-1-111.pdf");
    File attachment1 = new File("C:\\Temp\\Attachement_1.docx");
    File attachment2 = new File("C:\\Temp\\Attachement_2.docx");
    File attachment3 = new File("C:\\Temp\\Attachement_3.pdf");
    File attachment4 = new File("C:\\Temp\\Attachement_4.pdf");
    File attachment5 = new File("C:\\Temp\\Attachement_5.txt");
    public OutOfStateAttachments oosAttachments = new OutOfStateAttachments();
    public OutOfStateImpl outOfStateImpl = new OutOfStateImpl();


    
    /**
     * Test of getSmtpHost method, of class JndiUtils.
     */
    //@Test
    public void testOutOfState() {
        
        boolean ret = false;
        
        oosAttachments.setApplication(accord);
        oosAttachments.setSupplemental(supp);
        oosAttachments.setAddDocument1(attachment1);
        oosAttachments.setAddDocument2(attachment2);
        oosAttachments.setAddDocument3(attachment3);
        oosAttachments.setAddDocument4(attachment4);
        oosAttachments.setAddDocument5(attachment5);
        
        System.out.println("testOutOfState");
        boolean expResult = true;

        
        try {
            ret = outOfStateImpl.processRequest(oosAttachments);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        assertEquals(expResult, ret);
 
    }
}