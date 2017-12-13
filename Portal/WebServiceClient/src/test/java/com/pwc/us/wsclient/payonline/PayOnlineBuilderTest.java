package com.pwc.us.wsclient.payonline;

import com.pwc.us.common.model.payonline.ItemPO;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.ws.client.payonline.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Roger
 */
public class PayOnlineBuilderTest {
    
    @Test
    public void testMarshall() throws Exception {
        System.out.println("marshall");
        TransactionPO trans = createTransaction();
        PayOnlineRequestBuilder instance = new PayOnlineRequestBuilder();
        Transaction transactionObject = instance.buildTransaction(trans);        
        String result = instance.marshall(transactionObject);
        assertNotNull(result);
        System.out.println(result);
    }
    
    private TransactionPO createTransaction() {
        TransactionPO trans = new TransactionPO();
        trans.setLoginId("compsource");
        trans.setPassword("compsourcetest");
        trans.setAutocomplete(0);
        trans.setNameOnAccount("Bob Jones & Sons");
        trans.setLocalReference("STS1111111111");
        trans.setAddress1("111 Main St.");
        trans.setAddress2("Suite 10");
        trans.setCityProvince("Oklahoma City");
        trans.setState("OK");
        trans.setCountry("US");
        trans.setZip("43111");
        trans.setEmail("bob@jones.com");
        trans.setPhone("5555555555");
        trans.setAmountPaid(100.0);
        trans.setExtra01("10/31/2012");
        trans.setExtra02("STS1111111111");
        trans.setExtra03("Extra data 3");
        trans.setExtra04("Extra data 4");
        trans.setExtra05("Extra data 5");
        trans.setMaxFutureDate("11/20/2012");
        trans.setSuccessUrl("http://www.yahoo.com");
        trans.setFailureUrl("http://www.yahoo.com");
        trans.setCancelUrl("http://www.yahoo.com");
        trans.setAllowedPayTypes("ALL");
        trans.setItems(createItems());
        return trans;
    }
    
    private List<ItemPO> createItems() {
        List<ItemPO> items = new ArrayList<ItemPO>();
        ItemPO item1 = new ItemPO();
        item1.setTitle("Tax Due");
        item1.setDescription("This is a description.");
        item1.setQuantity(1);
        item1.setCost(50.0);
        items.add(item1);
        
        ItemPO item2 = new ItemPO();
        item2.setTitle("Fine for being awesome");
        item2.setDescription("So awesome it beggars description.");
        item2.setQuantity(1);
        item2.setCost(50.0);
        items.add(item2);
        
        return items;
    }
    
}
