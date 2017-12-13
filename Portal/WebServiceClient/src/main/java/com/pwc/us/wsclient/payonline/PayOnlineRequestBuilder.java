package com.pwc.us.wsclient.payonline;

import com.pwc.us.common.model.payonline.ItemPO;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.payonline.model.Item;
import com.pwc.us.ws.client.payonline.model.Items;
import com.pwc.us.ws.client.payonline.model.Transaction;
import com.pwc.us.ws.client.payonline.model.ObjectFactory;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Roger
 */
public class PayOnlineRequestBuilder {
    
    private ObjectFactory factory = new ObjectFactory();
    
    public Transaction buildTransaction(TransactionPO transPo) {
        Transaction trans = factory.createTransaction();
        // login id
        if (NullChecker.isNotNullOrEmpty(transPo.getLoginId())) {
            trans.setLoginId(transPo.getLoginId());
        }
        // password
        if (NullChecker.isNotNullOrEmpty(transPo.getPassword())) {
            trans.setPassword(transPo.getPassword());
        }
        // customer_id
        if (NullChecker.isNotNullOrEmpty(transPo.getCustomerId())) {
            trans.setCustomerId(transPo.getCustomerId());
        }
        // autocomplete
        if (NullChecker.isNotNullOrEmpty(transPo.getAutocomplete())) {
            trans.setAutocomplete(BigInteger.valueOf(transPo.getAutocomplete()));
        }
        // name on account
        if (NullChecker.isNotNullOrEmpty(transPo.getNameOnAccount())) {
            trans.setNameOnAccount(transPo.getNameOnAccount());
        }
        // local reference
        if (NullChecker.isNotNullOrEmpty(transPo.getLocalReference())) {
            trans.setLocalReference(transPo.getLocalReference());
        }
        // address 1
        if (NullChecker.isNotNullOrEmpty(transPo.getAddress1())) {
            trans.setAddress1(transPo.getAddress1());
        }
        // address 2
        if (NullChecker.isNotNullOrEmpty(transPo.getAddress2())) {
            trans.setAddress2(transPo.getAddress2());
        }
        // city province
        if (NullChecker.isNotNullOrEmpty(transPo.getCityProvince())) {
            trans.setCityProvince(transPo.getCityProvince());
        }
        // state
        if (NullChecker.isNotNullOrEmpty(transPo.getState())) {
            trans.setState(transPo.getState());
        }
        // country
        if (NullChecker.isNotNullOrEmpty(transPo.getCountry())) {
            trans.setCountry(transPo.getCountry());
        }
        // zip
        if (NullChecker.isNotNullOrEmpty(transPo.getZip())) {
            trans.setZip(transPo.getZip());
        }
        // email
        if (NullChecker.isNotNullOrEmpty(transPo.getEmail())) {
            trans.setEmail(transPo.getEmail());
        }
        // phone
        if (NullChecker.isNotNullOrEmpty(transPo.getPhone())) {
            trans.setPhone(transPo.getPhone());
        }
        // amount paid
        if (NullChecker.isNotNullOrEmpty(transPo.getAmountPaid())) {
            trans.setAmountPaid(BigDecimal.valueOf(transPo.getAmountPaid()));
        }
        // extra 1
        if (NullChecker.isNotNullOrEmpty(transPo.getExtra01())) {
            trans.setExtra01(transPo.getExtra01());
        }
        // extra 2
        if (NullChecker.isNotNullOrEmpty(transPo.getExtra02())) {
            trans.setExtra02(transPo.getExtra02());
        }
        // extra 3
        if (NullChecker.isNotNullOrEmpty(transPo.getExtra03())) {
            trans.setExtra03(transPo.getExtra03());
        }
        // extra 4
        if (NullChecker.isNotNullOrEmpty(transPo.getExtra04())) {
            trans.setExtra04(transPo.getExtra04());
        }
        // extra 5
        if (NullChecker.isNotNullOrEmpty(transPo.getExtra05())) {
            trans.setExtra05(transPo.getExtra05());
        }
        // max future date
        if (NullChecker.isNotNullOrEmpty(transPo.getMaxFutureDate())) {
            trans.setMaxFutureDate(transPo.getMaxFutureDate());
        }
        // success url
        if (NullChecker.isNotNullOrEmpty(transPo.getSuccessUrl())) {
            trans.setSuccessUrl(transPo.getSuccessUrl());
        }
        // failure ur.
        if (NullChecker.isNotNullOrEmpty(transPo.getFailureUrl())) {
            trans.setFailureUrl(transPo.getFailureUrl());
        }
        // cancel url
        if (NullChecker.isNotNullOrEmpty(transPo.getCancelUrl())) {
            trans.setCancelUrl(transPo.getCancelUrl());
        }
        // allowed pay types
        if (NullChecker.isNotNullOrEmpty(transPo.getAllowedPayTypes())) {
            trans.setAllowedPayTypes(transPo.getAllowedPayTypes());
        }
        //items
        if (NullChecker.isNotNullOrEmpty(transPo.getItems())) {
            trans.setItems(buildItems(transPo.getItems()));
        }
        return trans;
    }
    
    
    public TransactionPO buildTransactionPO(Transaction trans) {
        TransactionPO tpo = new TransactionPO();
        
        if (NullChecker.isNotNullOrEmpty(trans.getCustomerId())) {
            tpo.setCustomerId(trans.getCustomerId());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getNameOnAccount())) {
            tpo.setNameOnAccount(trans.getNameOnAccount());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getLocalReference())) {
            tpo.setLocalReference(trans.getLocalReference());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getAddress1())) {
            tpo.setAddress1(trans.getAddress1());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getAddress2())) {
            tpo.setAddress2(trans.getAddress2());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getLocalReference())) {
            tpo.setLocalReference(trans.getLocalReference());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getCityProvince())) {
            tpo.setCityProvince(trans.getCityProvince());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getState())) {
            tpo.setState(trans.getState());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getCountry())) {
            tpo.setCountry(trans.getCountry());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getZip())) {
            tpo.setZip(trans.getZip());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getEmail())) {
            tpo.setEmail(trans.getEmail());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getPhone())) {
            tpo.setPhone(trans.getPhone());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getAmountPaid())) {
            tpo.setAmountPaid(trans.getAmountPaid().doubleValue());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getExtra01())) {
            tpo.setExtra01(trans.getExtra01());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getExtra02())) {
            tpo.setExtra02(trans.getExtra02());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getExtra03())) {
            tpo.setExtra03(trans.getExtra03());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getExtra04())) {
            tpo.setExtra04(trans.getExtra04());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getExtra05())) {
            tpo.setExtra05(trans.getExtra05());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getInitiatePaymentDate())) {
            tpo.setInitiatePaymentDate(trans.getInitiatePaymentDate());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getTransDate())) {
            tpo.setTransDate(trans.getTransDate());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getProcessedTransId())) {
            tpo.setProcessedTransId(trans.getProcessedTransId());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getPaymentType())) {
            tpo.setPaymentType(trans.getPaymentType());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getOnlineFee())) {
            tpo.setOnlineFee(trans.getOnlineFee().doubleValue());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getLastFourAcct())) {
            tpo.setLastFourAcct(trans.getLastFourAcct());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getToken())) {
            tpo.setToken(trans.getToken());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getFailedDate())) {
            tpo.setFailedDate(trans.getFailedDate());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getFailedReason())) {
            tpo.setFailedReason(trans.getFailedReason());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getPendingTransId())) {
            tpo.setPendingTransId(trans.getPendingTransId());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getCancelled())) {
            tpo.setCancelled(trans.getCancelled());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getCancelledDate())) {
            tpo.setCancelledDate(trans.getCancelledDate());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getCancelledNotes())) {
            tpo.setCancelledNotes(trans.getCancelledNotes());
        }
        if (NullChecker.isNotNullOrEmpty(trans.getAuthcode())) {
            tpo.setAuthCode(trans.getAuthcode());
        }
        
        List<ItemPO> itemsPo = buildItemPOList(trans.getItems());
        if (NullChecker.isNotNullOrEmpty(itemsPo)) {
            tpo.setItems(itemsPo);
        }
      
        return tpo;
    }
    
    private List<ItemPO> buildItemPOList(Items items) {
        List<ItemPO> itemsPo = new ArrayList<ItemPO>();
        
        for (Item item : items.getItem()) {
            ItemPO ipo = new ItemPO();
            if (NullChecker.isNotNullOrEmpty(item.getCost())) {
                ipo.setCost(item.getCost().doubleValue());
            }
            if (NullChecker.isNotNullOrEmpty(item.getDescription())) {
                ipo.setDescription(item.getDescription());
            }
            if (NullChecker.isNotNullOrEmpty(item.getQuantity())) {
                ipo.setQuantity(item.getQuantity().intValue());
            }
            if (NullChecker.isNotNullOrEmpty(item.getTitle())) {
                ipo.setTitle(item.getTitle());
            }
        }
        
        return itemsPo;
    }
    
    private Items buildItems(List<ItemPO> itemsPo) {
        Items items = factory.createItems();
        
        for (ItemPO itmPo : itemsPo) {
            items.getItem().add(buildItem(itmPo));
        }
        return items;
    }
    
    private Item buildItem(ItemPO itmPo) {
        Item item = factory.createItem();
        item.setTitle(itmPo.getTitle());
        item.setDescription(itmPo.getDescription());
        item.setCost(BigDecimal.valueOf(itmPo.getCost()));
        item.setQuantity(BigInteger.valueOf(itmPo.getQuantity()));
        return item;
    }

    
    public String marshall(Transaction transObject) throws JAXBException, XMLStreamException {
        String xml = null;

        JAXBContext jaxbContext = JAXBContext.newInstance(Transaction.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        // Marshal Transaction
        jaxbMarshaller.marshal(getJAXBElementforTransaction(transObject), cdataStreamWriter);
        xml = stringWriter.toString();
        streamWriter.close();
        return xml;
    }
    
    private JAXBElement<Transaction> getJAXBElementforTransaction(Transaction trans) {
        QName qname = new QName("https://webservices.compsourceok.com", "transaction");
        return new JAXBElement<Transaction>(qname, Transaction.class, trans);
    }
    
}
