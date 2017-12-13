package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.ws.client.quoterequest.TypekeyName;
import com.pwc.us.ws.client.quoterequest.model.account.Account;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class QuoteRequestBuilder {

    AccountBuilder accountBuilder = new AccountBuilder();
    PolicyPeriodBuilder policyPeriodBuilder = new PolicyPeriodBuilder();
    private static final Logger logger = LoggerFactory.getLogger(AccountBuilder.class);
    private final QuoteRequest client = new QuoteRequest();

    public List<KeyName> getAccountOrgKeys() throws GwIntegrationException {
        return getTypekeyNames(client.getAccountOrgKeys());
    }

    public List<KeyName> getAddlIntRelationships() throws GwIntegrationException {
        return getTypekeyNames(client.getAddlIntRelationships());
    }

    public List<KeyName> getOwnerOfficerRelationships() throws GwIntegrationException {
        return getTypekeyNames(client.getOwnerOfficerRelationships());
    }

    public String processRequest(AccountQR account, PolicyPeriodQR policyPeriod)
            throws GwIntegrationException {
        // Build Account object
        Account act = accountBuilder.buildAccountObject(account);

        // Build PolicyPeriod object
        PolicyPeriod per = policyPeriodBuilder.createPolicyPeriodObject(policyPeriod);

        // Turn both objects into XML
        String actXml = null, perXml = null;
        try {
            actXml = accountBuilder.marshall(act);
            perXml = policyPeriodBuilder.marshall(per);
        } catch (JAXBException e) {
            String message = "caught JAXBException trying to marshall quote request";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XMLStreamException e) {
            String message = "caught XMLStreamException trying to marshall quote request";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        
        // Submit the request
        String ret = client.createNewSubmission(actXml, perXml);
        return ret;
    }

    private List<KeyName> getTypekeyNames(List<TypekeyName> typekeyNames) {
        List<KeyName> listTypekeyNames = new ArrayList<KeyName>();

        for (TypekeyName t : typekeyNames) {
            KeyName typekeyNamePOJO = new KeyName();
            typekeyNamePOJO.setCode(t.getCode());
            typekeyNamePOJO.setName(t.getName());
            listTypekeyNames.add(typekeyNamePOJO);
        }
        return listTypekeyNames;
    }
}
