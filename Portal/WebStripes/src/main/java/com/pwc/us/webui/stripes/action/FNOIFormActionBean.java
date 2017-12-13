package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.FNOIRequest;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwFNOIRequiredFieldException;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.UsState;
import com.pwc.us.common.model.fnoi.BodyPartDetailsFNOI;
import com.pwc.us.common.model.fnoi.BodyPartTypeFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactRoleFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.fnoi.EmploymentDataFNOI;
import com.pwc.us.common.model.fnoi.IncidentFNOI;
import com.pwc.us.common.model.fnoi.NoteFNOI;
import com.pwc.us.common.model.fnoi.PolicyFNOI;
import com.pwc.us.common.model.fnoi.PolicyLocationFNOI;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.YesNo;
import com.pwc.us.common.model.fnoi.WorkStatusFNOI;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.common.utils.DateTimeHelper;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class FNOIFormActionBean extends FNOIBaseActionBean {

    private static final String FNOI_ERROR_REQUEST_FAILED = "FNOIRequest.Error.RequestFailed";
    private static final Logger logger = LoggerFactory.getLogger(FNOIFormActionBean.class);
    private static final Logger claimsLog = LoggerFactory.getLogger("claimsLog");
    private static final String FORM = "/WEB-INF/jsp/fnoi_form.jsp";
    @Inject
    private FNOIRequest fnoiRequest;

    @DefaultHandler
    public Resolution form() {
        Resolution ret = null;
        logger.info("browser user agent " + getContext().getRequest().getHeader("user-agent"));
        if (NullChecker.isNullOrEmpty(
                (String) getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"))) {
            return new RedirectResolution(FNOIPolicyCheck.class);
        }
        if (NullChecker.isNullOrEmpty(
                (String) getContext().getRequest().getSession().getAttribute("fnoiDateOfAccident"))) {
            return new RedirectResolution(FNOIPolicyCheck.class);
        }

        try {

            buildCache();
            ret = new ForwardResolution(FORM);
        } catch (GwIntegrationException ex) {
            claimsLog.error("ErrorMessage=" + "\"fatal error trying to access FNOI web services" + "\" Task=ClaimsForm Status=Failed ErrorType=GwIntegrationException PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo(), ex);
            logger.error("fatal error trying to access FNOI web services for " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("FNOIRequest.Error.buildCache", getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber")));
            ret = new ForwardResolution(FORM);
        }

        return ret;
    }

    @DontValidate
    public Resolution cancel() {
        getContext().getMessages().add(
                new SimpleMessage("Action cancelled."));
        return new RedirectResolution(FNOIPolicyCheck.class);
    }

    private boolean bothValuesNotSupplied(Object thingOne, Object thingTwo) {
        if ((NullChecker.isNotNullOrEmpty(thingOne)
                && NullChecker.isNullOrEmpty(thingTwo))
                || (NullChecker.isNullOrEmpty(thingOne)
                && NullChecker.isNotNullOrEmpty(thingTwo))) {
            return true;
        }
        return false;
    }

    @HandlesEvent("submit")
    public Resolution submit() {
        Resolution ret = null;
        long submitStartTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis();
        ValidationErrors errors = new ValidationErrors();

        if (NullChecker.isNotNullOrEmpty(supervisorEP) && bothValuesNotSupplied(supervisorEP.getFirstName(), supervisorEP.getLastName())) {
            errors.add("missingName", new SimpleError("You must provide both a first and last name for the Supervisor (or blank them both out)"));
        }

        if (NullChecker.isNotNullOrEmpty(witnessList)) {
            for (ContactBO.EntityPerson witness : witnessList) {
                if (NullChecker.isNotNullOrEmpty(witness) && bothValuesNotSupplied(witness.getFirstName(), witness.getLastName())) {
                    errors.add("missingName", new SimpleError("You must provide both a first and last name for the Witness (or blank them both out)"));
                }
            }
        }

        if (NullChecker.isNotNullOrEmpty(physicianEP) && bothValuesNotSupplied(physicianEP.getFirstName(), physicianEP.getLastName())) {
            errors.add("missingName", new SimpleError("You must provide both a first and last name for the Treating Physician (or blank them both out)"));
        }
        boolean doctorNameSupplied = false;
        if (NullChecker.isNotNullOrEmpty(physicianEP) && NullChecker.isNotNullOrEmpty(physicianEP.getFirstName())) {
            doctorNameSupplied = true;
        }

        try {
            createClaimFNOI();
            if (NullChecker.isNotNullOrEmpty(claim)) {
                AddressBO address = claim.getLossLocation();
                if (NullChecker.isNotNullOrEmpty(claim.getLossLocation())) {
                    if (NullChecker.isNotNullOrEmpty(address)) {
                        if (NullChecker.isNullOrEmpty(address.getAddressLine1())) {
                            errors.add("missingAddress", new SimpleError("You must provide Place of Accident Address 1 (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getCity())) {
                            errors.add("missingAddress", new SimpleError("You must provide Place of Accident City (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getState())) {
                            errors.add("missingAddress", new SimpleError("You must provide Place of Accident State (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getPostalCode())) {
                            errors.add("missingAddress", new SimpleError("You must provide Place of Accident Zip (or totally blank the address out)"));
                        }

                    }
                }
            }

            if (NullChecker.isNotNullOrEmpty(physician) && doctorNameSupplied) {
                AddressBO address = physician.getPrimaryAddress();
                if (address == null) {
                    errors.add("missingAddress", new SimpleError("You must provide Treating Physician Address (or totally blank the Physician Name out)"));
                }
                if (NullChecker.isNotNullOrEmpty(physician.getPrimaryAddress())) {
                    if (NullChecker.isNotNullOrEmpty(address)) {
                        if (NullChecker.isNullOrEmpty(address.getAddressLine1())) {
                            errors.add("missingAddress", new SimpleError("You must provide Treating Physician Address 1 (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getCity())) {
                            errors.add("missingAddress", new SimpleError("You must provide Treating Physician City (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getState())) {
                            errors.add("missingAddress", new SimpleError("You must provide Treating Physician State (or totally blank the address out)"));
                        }
                        if (NullChecker.isNullOrEmpty(address.getPostalCode())) {
                            errors.add("missingAddress", new SimpleError("You must provide Treating Physician Zip (or totally blank the address out)"));
                        }

                    }
                }
            }
            if (!errors.isEmpty()) {
                getContext().setValidationErrors(errors);
                return getContext().getSourcePageResolution();
            }
            String response = fnoiRequest.processRequest(claim);
            elapsedTime = (System.currentTimeMillis() - submitStartTime);
            //Condition the message baseed on the returned value
            if (NullChecker.isNotNullOrEmpty(response)) {
                getContext().getMessages().add(
                        new SimpleMessage("First Notice of Injury Request has been successfully submitted to ClaimCenter. "
                                + "Your claim number is " + response + ".", response));

                claimsLog.info("claim successfully submitted # SubmissionNumber=" + response + " Status=Success Task=ClaimsSubmission processingTime=" + elapsedTime + " PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo());
                ret = new RedirectResolution(FNOIPolicyCheck.class);
            } else {
                logger.info("Unable to process FNOI Request  on policy " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"));
                claimsLog.error("ErrorMessage=" + "\"Unable to process FNOI Request:" + "\" Task=ClaimsSubmission Status=Failed ErrorType=RequestFailed PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + " processingTime=" + elapsedTime + getReporterInfo());
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError(FNOI_ERROR_REQUEST_FAILED, getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber")));
                ret = new ForwardResolution(this.getClass(), "form");
            }
        } catch (GwFNOIRequiredFieldException ex) {
            elapsedTime = (System.currentTimeMillis() - submitStartTime);
            logger.error("Unable to process FNOI Request - Required Field Exception on policy " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"), ex);
            claimsLog.error("ErrorMessage=" + "\"Unable to process FNOI Request - Required Field Exception on policy \"" + "Task=ClaimsSubmission Status=Failed ErrorType=GwFNOIRequiredFieldException processingTime=" + elapsedTime + " PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo(), ex);
            //getContext().getValidationErrors().addGlobalError(new LocalizableError("FNOIRequest.Error.RequirdFieldFail", ex.getCause().getMessage()));
            getContext().getValidationErrors().addGlobalError(new SimpleError("We're sorry, but your First Notice of Injury Request is not able to be processed at this time. Please correct the following required field:" + ex.getCause().getMessage(), ex.getCause().getMessage()));
            ret = new ForwardResolution(this.getClass(), "form");
        } catch (GwIntegrationException ex) {
            elapsedTime = (System.currentTimeMillis() - submitStartTime);
            claimsLog.error("ErrorMessage=" + "\"Unable to process FNOI Request on policy \"" + "Task=ClaimsSubmission Status=Failed ErrorType=GwIntegrationException processingTime=" + elapsedTime + " PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo(), ex);
            logger.error("Unable to process FNOI Request on policy " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(FNOI_ERROR_REQUEST_FAILED, getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber")));
            ret = new ForwardResolution(this.getClass(), "form");
        } catch (ParseException ex) {
            elapsedTime = (System.currentTimeMillis() - submitStartTime);
            claimsLog.error("ErrorMessage=" + "\"Unable to process FNOI Request on policy \"" + "Task=ClaimsSubmission Status=Failed ErrorType=ParseException processingTime=" + elapsedTime + " PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo(), ex);
            logger.error("Unable to process FNOI Request on policy " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(FNOI_ERROR_REQUEST_FAILED, getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber")));
            ret = new ForwardResolution(this.getClass(), "form");
        } catch (DatatypeConfigurationException ex) {
            elapsedTime = (System.currentTimeMillis() - submitStartTime);
            claimsLog.error("ErrorMessage=" + "\"Unable to process FNOI Request on policy \"" + "Task=ClaimsSubmission Status=Failed ErrorType=DatatypeConfigurationException processingTime=" + elapsedTime + " PolicyNumber=" + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber") + getReporterInfo(), ex);
            logger.error("Unable to process FNOI Request on policy " + getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber"), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(FNOI_ERROR_REQUEST_FAILED, getContext().getRequest().getSession().getAttribute("fnoiPolicyNumber")));
            ret = new ForwardResolution(this.getClass(), "form");
        }

        return ret;
    }

    @ValidationMethod(on = "submit")
    public void validateSecondaryFields(ValidationErrors errors) {

    }

    public void buildCache() throws GwIntegrationException {
        getPrimaryInjuryType();
        getMultipleInjuryType();
        getCumulativeDetailedInjuryType();
        getSpecificDetailedInjuryType();
        getOccupationalInjuryType();
        getAccountOrgTypeExt();
        getBodyPartType();
        //getDetailedBodyPartTypes();
        getHeadDetailedBodyParts();
        getNeckDetailedBodyParts();
        getUpperDetailedBodyParts();
        getTrunkDetailedBodyParts();
        getLowerDetailedBodyParts();
        getMultipleDetailedBodyParts();
    }

    public List<KeyName> getPrimaryInjuryType() throws GwIntegrationException {
        primaryInjuryType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("primaryInjuryInjuryType");
        primaryInjuryList = (List<String>) getContext().getRequest().getSession().getAttribute("primaryInjuryList");

        if (NullChecker.isNullOrEmpty(primaryInjuryType)) {
            primaryInjuryType = fnoiRequest.getCumulativeDetailedInjuryTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("primaryInjuryInjuryType", primaryInjuryType);
        }
        if (NullChecker.isNullOrEmpty(primaryInjuryList)) {
            if (primaryInjuryList == null) {
                primaryInjuryList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("primaryInjuryList", primaryInjuryList);
        }

        return primaryInjuryType;
    }

    public List<KeyName> getMultipleInjuryType() throws GwIntegrationException {
        multipleInjuryType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("multipleInjuryType");
        multipleInjuryList = (List<String>) getContext().getRequest().getSession().getAttribute("multipleInjuryList");

        if (NullChecker.isNullOrEmpty(multipleInjuryType)) {
            multipleInjuryType = fnoiRequest.getMultipleInjuryTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("multipleInjuryType", multipleInjuryType);
        }
        if (NullChecker.isNullOrEmpty(multipleInjuryList)) {
            if (multipleInjuryList == null) {
                multipleInjuryList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("multipleInjuryList", multipleInjuryList);
        }

        return multipleInjuryType;
    }

    public List<KeyName> getCumulativeDetailedInjuryType() throws GwIntegrationException {
        cumulativeDetailedInjuryType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("cumulativeDetailedInjuryType");
        cumulativeDetailedList = (List<String>) getContext().getRequest().getSession().getAttribute("cumulativeDetailedList");

        if (NullChecker.isNullOrEmpty(cumulativeDetailedInjuryType)) {
            cumulativeDetailedInjuryType = fnoiRequest.getCumulativeDetailedInjuryTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("cumulativeDetailedInjuryType", cumulativeDetailedInjuryType);
        }
        if (NullChecker.isNullOrEmpty(cumulativeDetailedList)) {
            if (cumulativeDetailedList == null) {
                cumulativeDetailedList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("cumulativeDetailedList", cumulativeDetailedList);
        }

        return cumulativeDetailedInjuryType;
    }

    public List<KeyName> getSpecificDetailedInjuryType() throws GwIntegrationException {
        specificDetailedInjuryType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("specificDetailedInjuryType");
        specificDetailedList = (List<String>) getContext().getRequest().getSession().getAttribute("specificDetailedList");

        if (NullChecker.isNullOrEmpty(specificDetailedInjuryType)) {
            specificDetailedInjuryType = fnoiRequest.getSpecificDetailedInjuryTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("specificDetailedInjuryType", specificDetailedInjuryType);
        }
        if (NullChecker.isNullOrEmpty(specificDetailedList)) {
            if (specificDetailedList == null) {
                specificDetailedList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("specificDetailedList", specificDetailedList);
        }

        return specificDetailedInjuryType;
    }

    public List<KeyName> getOccupationalInjuryType() throws GwIntegrationException {
        occupationalInjuryType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("occupationalInjuryType");
        occupationalList = (List<String>) getContext().getRequest().getSession().getAttribute("occupationalList");
        if (NullChecker.isNullOrEmpty(occupationalInjuryType)) {
            occupationalInjuryType = fnoiRequest.getOccupationalInjuryTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("occupationalInjuryType", occupationalInjuryType);
        }
        if (NullChecker.isNullOrEmpty(occupationalList)) {
            if (occupationalList == null) {
                occupationalList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("occupationalList", occupationalList);
        }
        return occupationalInjuryType;
    }

    /*Went different direction...keeping around for now.
     * 
     * public Map<KeyNameNameFNOI, List<KeyNameNameFNOI>> getDetailedBodyPartTypes() throws GwIntegrationException {
     detailedBodyPartMap = (HashMap<KeyNameNameFNOI, List<KeyNameNameFNOI>>) getContext().getRequest().getSession().getAttribute("detailedBodyPartMap");
     detailedBodyPartMapList = (HashMap<String, String>) getContext().getRequest().getSession().getAttribute("detailedBodyPartMapList");
        
     if (NullChecker.isNullOrEmpty(detailedBodyPartMap) || NullChecker.isNullOrEmpty(detailedBodyPartMapList)) {
     if (detailedBodyPartMap == null) {
     detailedBodyPartMap = new HashMap<KeyNameNameFNOI, List<KeyNameNameFNOI>>();
     }

     if (detailedBodyPartMapList == null) {
     detailedBodyPartMapList = new HashMap<String, String>();
     }
        

     for (KeyNameNameFNOI t : getBodyPartType()) {
     List<KeyNameNameFNOI> list = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue(t.getCode()));
     detailedBodyPartMap.put(t, list);
     for (KeyNameNameFNOI b : list) {
     detailedBodyPartMapList.put(t.getCode(), b.getCode());
     }
                
     }
            
     getContext().getRequest().getSession().setAttribute("detailedBodyPartMap", detailedBodyPartMap);
     getContext().getRequest().getSession().setAttribute("detailedBodyPartMapList", detailedBodyPartMapList);

     }
     return detailedBodyPartMap;
     }*/
    public List<KeyName> getBodyPartType() throws GwIntegrationException {
        bodyPartType = (List<KeyName>) getContext().getRequest().getSession().getAttribute("bodyPartType");
        bodyPartList = (List<String>) getContext().getRequest().getSession().getAttribute("bodyPartList");
        if (NullChecker.isNullOrEmpty(bodyPartType)) {
            bodyPartType = fnoiRequest.getBodyPartTypeTypeKeys();
            getContext().getRequest().getSession().setAttribute("bodyPartType", bodyPartType);
        }
        if (NullChecker.isNullOrEmpty(bodyPartList)) {
            if (bodyPartList == null) {
                bodyPartList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("bodyPartList", bodyPartList);
        }

        return bodyPartType;

    }

    /* These next 6 methods are each based off of the values found in the BodyPartTypeFNOI Enum.  Each of these will have a body part checkbox on the form
     * that then contols the visiablity of a subsequent set of checkboxes that define the detailed body parts effected.
     *      Here are the values as of implementation:
     *       HEAD("head")
     *       NECK("neck")
     *       UPPER("upper")
     *       TRUNK("trunk")
     *       LOWER("lower")
     *       MULTIPLE("multiple")

      
     * This is the logic from the fnoi_form.jsp that almost worked.
     * 
     <ul class="checkbox">
     <c:forEach var="bodyPartType" items="${detailedBodyPartMap}" varStatus="pStatus">
     <li><s:checkbox id="bodyPartID${pStatus.count}" name="bodyPartList" value="${bodyPartType.key.code}"/>${bodyPartType.key.name}</li>
     <!-- Indentify Detailed Body Parts -->
     <div class="sem-field-container " id="detailedBodyDiv${pStatus.count}">
     <fieldset class="sem-fieldset" id="sem-fieldset-11">
     <ul class="checkbox">
     <c:forEach var="detailedBodyPart" items="${bodyPartType.value}" varStatus="dStatus">
     <li><s:checkbox name="detailedBodyPartMapList.${bodyPartType.key.code}" value="${detailedBodyPart.code}"/>${detailedBodyPart.name}</li>
     </c:forEach>
     </ul>
     </fieldset>
     </div>
     </c:forEach>
     </ul> 
     */
    public List<KeyName> getHeadDetailedBodyParts() throws GwIntegrationException {
        headDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("headDetailedBodyParts");
        headDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("headDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(headDetailedBodyParts)) {
            headDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("head"));
            getContext().getRequest().getSession().setAttribute("headDetailedBodyParts", headDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(headDetailedBodyPartsList)) {
            if (headDetailedBodyPartsList == null) {
                headDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("headDetailedBodyPartsList", headDetailedBodyPartsList);
        }

        return headDetailedBodyParts;
    }

    public List<KeyName> getNeckDetailedBodyParts() throws GwIntegrationException {
        neckDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("v");
        neckDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("neckDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(neckDetailedBodyParts)) {
            neckDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("neck"));
            getContext().getRequest().getSession().setAttribute("neckDetailedBodyParts", neckDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(neckDetailedBodyPartsList)) {
            if (neckDetailedBodyPartsList == null) {
                neckDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("neckDetailedBodyPartsList", neckDetailedBodyPartsList);
        }

        return neckDetailedBodyParts;
    }

    public List<KeyName> getUpperDetailedBodyParts() throws GwIntegrationException {
        upperDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("upperDetailedBodyParts");
        upperDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("upperDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(upperDetailedBodyParts)) {
            upperDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("upper"));
            getContext().getRequest().getSession().setAttribute("upperDetailedBodyParts", upperDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(upperDetailedBodyPartsList)) {
            if (upperDetailedBodyPartsList == null) {
                upperDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("upperDetailedBodyPartsList", upperDetailedBodyPartsList);
        }

        return upperDetailedBodyParts;
    }

    public List<KeyName> getTrunkDetailedBodyParts() throws GwIntegrationException {
        trunkDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("v");
        trunkDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("trunkDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(trunkDetailedBodyParts)) {
            trunkDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("trunk"));
            getContext().getRequest().getSession().setAttribute("trunkDetailedBodyParts", trunkDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(trunkDetailedBodyPartsList)) {
            if (trunkDetailedBodyPartsList == null) {
                trunkDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("trunkDetailedBodyPartsList", trunkDetailedBodyPartsList);
        }

        return trunkDetailedBodyParts;
    }

    public List<KeyName> getLowerDetailedBodyParts() throws GwIntegrationException {
        lowerDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("lowerDetailedBodyParts");
        lowerDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("lowerDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(lowerDetailedBodyParts)) {
            lowerDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("lower"));
            getContext().getRequest().getSession().setAttribute("lowerDetailedBodyParts", lowerDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(lowerDetailedBodyPartsList)) {
            if (lowerDetailedBodyPartsList == null) {
                lowerDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("lowerDetailedBodyPartsList", lowerDetailedBodyPartsList);
        }

        return lowerDetailedBodyParts;
    }

    public List<KeyName> getMultipleDetailedBodyParts() throws GwIntegrationException {
        multipleDetailedBodyParts = (List<KeyName>) getContext().getRequest().getSession().getAttribute("v");
        multipleDetailedBodyPartsList = (List<String>) getContext().getRequest().getSession().getAttribute("multipleDetailedBodyPartsList");

        if (NullChecker.isNullOrEmpty(multipleDetailedBodyParts)) {
            multipleDetailedBodyParts = fnoiRequest.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.fromValue("multiple"));
            getContext().getRequest().getSession().setAttribute("multipleDetailedBodyParts", multipleDetailedBodyParts);
        }
        if (NullChecker.isNullOrEmpty(multipleDetailedBodyPartsList)) {
            if (multipleDetailedBodyPartsList == null) {
                multipleDetailedBodyPartsList = new ArrayList<String>();
            }
            getContext().getRequest().getSession().setAttribute("multipleDetailedBodyPartsList", multipleDetailedBodyPartsList);
        }

        return multipleDetailedBodyParts;
    }

    public List<KeyName> getAccountOrgTypeExt() throws GwIntegrationException {
        accountOrgTypeExt = (List<KeyName>) getContext().getRequest().getSession().getAttribute("accountOrgTypeExt");
        if (NullChecker.isNullOrEmpty(accountOrgTypeExt)) {
            accountOrgTypeExt = fnoiRequest.getAccountOrgTypeExtTypeKeys();
            getContext().getRequest().getSession().setAttribute("accountOrgTypeExt", accountOrgTypeExt);
        }
        return accountOrgTypeExt;
    }

    public UsState[] getUsStates() {
        return UsState.values();
    }

    public ClaimFNOI createClaimFNOI() throws ParseException,
            DatatypeConfigurationException {

        // FNOI Claim Objects
        ClaimFNOI.Contacts contacts = new ClaimFNOI.Contacts();
        List<ClaimContactFNOI> claimContacts = contacts.getEntry();
        ClaimFNOI.Incidents incidents = new ClaimFNOI.Incidents();
        List<IncidentFNOI> incidentList = incidents.getEntry();
        ClaimFNOI.Notes notes = new ClaimFNOI.Notes();
        List<NoteFNOI> noteList = notes.getEntry();

        ClaimContactRoleFNOI claimantRole = new ClaimContactRoleFNOI("claimant");
        ClaimContactRoleFNOI physicianRole = new ClaimContactRoleFNOI("FirstIntakeDoctor");
        ClaimContactRoleFNOI supervisorRole = new ClaimContactRoleFNOI("supervisor");
        ClaimContactRoleFNOI reporterRole = new ClaimContactRoleFNOI("reporter");
        ClaimContactRoleFNOI witnessRole = new ClaimContactRoleFNOI("witness");
        ClaimContactRoleFNOI policyHolderRole = new ClaimContactRoleFNOI("insured");

        // Build the contacts
        claimant = this.getClaimant();

        if (NullChecker.isNotNullOrEmpty(dateOfBirth)) {
            claimantEP.setDateOfBirth(DateTimeHelper.dateToXMLGregorianCalendar(dateOfBirth));
        }

        claimant.setEntityPerson(claimantEP);
        if (NullChecker.isNotNullOrEmpty(claimantAddress)) {
            claimant.setPrimaryAddress(claimantAddress);
        }
        buildContacts(claimContacts, claimant, claimantRole);

        if (NullChecker.isNotNullOrEmpty(physician) || NullChecker.isNotNullOrEmpty(physicianEP)) {
            physician = this.getPhysician();
            physician.setEntityPerson(physicianEP);
            if (NullChecker.isNotNullOrEmpty(physicianAddress)) {
                physician.setPrimaryAddress(physicianAddress);
            }
            buildContacts(claimContacts, physician, physicianRole);
        }

        if (NullChecker.isNotNullOrEmpty(supervisorEP)) {
            supervisor = this.getSupervisor();
            supervisor.setEntityPerson(supervisorEP);
            buildContacts(claimContacts, supervisor, supervisorRole);
        }

        if (NullChecker.isNotNullOrEmpty(reporter) || NullChecker.isNotNullOrEmpty(reporterEP)) {
            reporter = this.getReporter();
            reporter.setEntityPerson(reporterEP);
            buildContacts(claimContacts, reporter, reporterRole);
        }

        if (NullChecker.isNotNullOrEmpty(witnessList)) {
            for (ContactBO.EntityPerson witnessEP : witnessList) {
                ContactBO witness = new ContactBO();
                witness.setEntityPerson(witnessEP);
                buildContacts(claimContacts, witness, witnessRole);
            }
        }
        // Set the contacts on the claim
        claim.setContacts(contacts);
       

        // setDateRptdToEmployer
        if (NullChecker.isNotNullOrEmpty(dateReported)) {
            Format formatter = new SimpleDateFormat("MMM dd yyyy  HH:mm:ss");
            if (!(NullChecker.isNullOrEmpty(timeReported))) {
                if (timeReported.getMinutes() == 0) {
                    timeReported.setMinutes(1);
                } else {
                    timeReported.setMinutes(timeReported.getMinutes());
                    timeReported.setHours(timeReported.getHours());
                }
            }
            if (timeReported == null) {
                timeReported = dateReported;
            }

            if (!(NullChecker.isNullOrEmpty(timeReported))) {
                logger.debug("timeReported " + formatter.format(timeReported));
            }
            logger.debug("dateReported " + formatter.format(dateReported));
            claim.setDateRptdToEmployer(DateTimeHelper.dateTimeToXMLGregorianCalendar(dateReported, timeReported));
        }

        if (NullChecker.isNotNullOrEmpty(dateOfAccident)) {
            Format formatter = new SimpleDateFormat("MMM dd yyyy  HH:mm:ss");
            if (!(NullChecker.isNullOrEmpty(timeOfAccident))) {
                if (timeOfAccident.getMinutes() == 0) {
                    dateOfAccident.setMinutes(1);
                } else {
                    dateOfAccident.setMinutes(timeOfAccident.getMinutes());
                    dateOfAccident.setHours(timeOfAccident.getHours());
                }
            } else {
                Date tmpDate = new Date(dateOfAccident.getYear(), dateOfAccident.getMonth(),
                        dateOfAccident.getDate(), 0, 1);
                dateOfAccident.setTime(tmpDate.getTime());
            }
            if (timeOfAccident == null) {
                timeOfAccident = dateOfAccident;
            }

            if (!(NullChecker.isNullOrEmpty(timeOfAccident))) {
                logger.debug("timeOfAccident " + formatter.format(timeOfAccident));
            }
            logger.debug("dateOfAccident " + formatter.format(dateOfAccident));

            claim.setLossDate(DateTimeHelper.dateTimeToXMLGregorianCalendar(dateOfAccident, timeOfAccident));
        }

        claim.setEmpQusValidity(YesNo.valueOf(claim.getEmpQusValidity()).getValue());
        claim.setIncidentOnly(YesNo.valueOf(claim.getIncidentOnly()).getValue());
        // Set Employment Data
        EmploymentDataFNOI.WorkStatusChanges wsChanges = new EmploymentDataFNOI.WorkStatusChanges();
        List<WorkStatusFNOI> wsList = wsChanges.getEntry();

        workStatus = this.getWorkStatus();
        if (NullChecker.isNotNullOrEmpty(returnToWorkQuestion) && returnToWorkQuestion.equals(YesNo.Y.name())) {
            //workStatus.setStatus("fullduty/restricted_work");
            workStatus.setStatus("fullduty");
            if (NullChecker.isNotNullOrEmpty(returnToWorkDate)) {
                workStatus.setStatusDate(DateTimeHelper.dateToXMLGregorianCalendar(returnToWorkDate));
            }
        } else {
            workStatus.setStatus("stopped_work");
        }

        wsList.add(workStatus);

        employmentData = this.getEmploymentData();

        if (NullChecker.isNotNullOrEmpty(hireStateQuestion) && hireStateQuestion.equals(YesNo.Y.name())) {
            employmentData.setHireState("OK");
        }

        if (NullChecker.isNotNullOrEmpty(dateOfHire)) {
            employmentData.setHireDate(DateTimeHelper.dateToXMLGregorianCalendar(dateOfHire));
        }

        if (NullChecker.isNotNullOrEmpty(lastWorkedDate)) {
            employmentData.setLastWorkedDate(DateTimeHelper.dateToXMLGregorianCalendar(lastWorkedDate));
        }

        if (NullChecker.isNotNullOrEmpty(dateOfAccident)) {
            employmentData.setInjuryStartTime(DateTimeHelper.dateTimeToXMLGregorianCalendar(dateOfAccident, timeOfAccident));
        }

        employmentData.setWorkStatusChanges(wsChanges);

        claim.setEmploymentData(employmentData);

        // Set the Incident Objects
        IncidentFNOI.EntityInjuryIncident.BodyParts bodyParts = new IncidentFNOI.EntityInjuryIncident.BodyParts();
        List<BodyPartDetailsFNOI> bodyPartDetailsList = bodyParts.getEntry();


        /* Went a different direction ...Keeping around for now
         for (Map.Entry<String, String> bpItem : detailedBodyPartMapList.entrySet()) {
         BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
         bp.setPrimaryBodyPart(bpItem.getKey());
         bp.setDetailedBodyPart(bpItem.getValue());
         bodyPartDetailsList.add(bp);
         }
         */
        if (bodyPartList != null) {
            for (String bpl : bodyPartList) {
                if ("head".equals(bpl)) {
                    for (String bpd : headDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                } else if ("neck".equals(bpl)) {
                    for (String bpd : neckDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                } else if ("upper".equals(bpl)) {
                    for (String bpd : upperDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                } else if ("trunk".equals(bpl)) {
                    for (String bpd : trunkDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                } else if ("lower".equals(bpl)) {
                    for (String bpd : lowerDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                } else if ("multiple".equals(bpl)) {
                    for (String bpd : multipleDetailedBodyPartsList) {
                        BodyPartDetailsFNOI bp = new BodyPartDetailsFNOI();
                        bp.setPrimaryBodyPart(bpl);
                        bp.setDetailedBodyPart(bpd);
                        bodyPartDetailsList.add(bp);
                    }
                }
            }
        }

        injury = this.getInjury();
        if (injury.getGeneralInjuryType().equalsIgnoreCase("occupational")) {
            injury.setDetailedInjuryType(injury.getOccupationalInjuryType());
//            logger.error("injuryType: "+injury.getOccupationalInjuryType());
        } else if (injury.getGeneralInjuryType().equalsIgnoreCase("det")) {
            injury.setDetailedInjuryType(injury.getSpecificDetailedInjuryType());
//            logger.error("injuryType: "+injury.getDetailedInjuryType());
        } else if (injury.getGeneralInjuryType().equalsIgnoreCase("cumulative")) {
            injury.setDetailedInjuryType(injury.getCumulativeInjuryType());
//            logger.error("injuryType: "+injury.getCumulativeInjuryType());         
        } else if (injury.getGeneralInjuryType().equalsIgnoreCase("multiple")) {
            injury.setDetailedInjuryType(injury.getMultipleInjuryType());
//            logger.error("injuryType: "+injury.getMultipleInjuryType());
        }
        injury.setBodyParts(bodyParts);
        incident = this.getIncident();

        // setDeathDate
        if (NullChecker.isNotNullOrEmpty(deathDateQuestion) && deathDateQuestion.equals(YesNo.Y.name())) {
            if (NullChecker.isNotNullOrEmpty(deathDate)) {
                claim.setDeathDate(DateTimeHelper.dateToXMLGregorianCalendar(deathDate));
            }
            incident.setSeverity("fatal");
        }
        incident.setEntityInjuryIncident(injury);
        boolean add = incidentList.add(incident);
        claim.setIncidents(incidents);

        //colin kirk
        //since this is specifically the bean for the FNOI
        //form it shouldn't be an issue to hard-code the topic and subject
        //at least for now we're just hardcoding the topic and subject fields 
        // if the comments field is filled out
        if (NullChecker.isNotNullOrEmpty(claim.getNotesString())) {
            note = this.getNote();
            topic = this.getTopic();
            note.setBody(claim.getNotesString());
            note.setSubject("Portal Comments");
            //we're using the typekey name here
            topic.setDescription("insured_ext");
            topic.setDisplayName("insured_ext");
            note.setTopic(topic);
            noteList.add(note);

            claim.setNotes(notes);
        }

        // Set LossLocation
        if (NullChecker.isNotNullOrEmpty(lossLocation)) {
            claim.setLossLocation(lossLocation);
        }

        // Policy
        PolicyFNOI.Contacts policyContacts = new PolicyFNOI.Contacts();
        PolicyFNOI.PolicyLocations policyLocations = new PolicyFNOI.PolicyLocations();
        List<ClaimContactFNOI> policyClaimContacts = policyContacts.getEntry();
        List<PolicyLocationFNOI> policyLocationsList = policyLocations.getEntry();

        policyHolder = this.getPolicyHolder();
        if (NullChecker.isNotNullOrEmpty(policyHolderEP)) {
            policyHolder.setEntityPerson(policyHolderEP);
        }
        buildContacts(policyClaimContacts, policyHolder, policyHolderRole);

        if (NullChecker.isNotNullOrEmpty(policyFromDate)) {
            policy.setEffectiveDate(DateTimeHelper.dateToXMLGregorianCalendar(policyFromDate));
        }

        if (NullChecker.isNotNullOrEmpty(policyToDate)) {
            policy.setExpirationDate(DateTimeHelper.dateToXMLGregorianCalendar(policyToDate));
        }

        policy.setContacts(policyContacts);
        policyLocation = this.getPolicyLocation();
        if (NullChecker.isNotNullOrEmpty(policyLocationAddress)) {
            policyLocation.setAddress(policyLocationAddress);
        }
        policyLocationsList.add(policyLocation);
        policy.setPolicyLocations(policyLocations);
        claim.setPolicy(policy);

        return claim;
    }

    private void buildContacts(List<ClaimContactFNOI> claimContacts,
            ContactBO contact, ClaimContactRoleFNOI role) {

        ClaimContactFNOI cc = new ClaimContactFNOI();
        ClaimContactFNOI.Roles roles = new ClaimContactFNOI.Roles();
        List<ClaimContactRoleFNOI> ccRole = roles.getEntry();
        ccRole.add(role);

        // Build the ClaimContact
        cc.setContactFNOI(contact);
        cc.setRoles(roles);

        // Add to the List<ClaimContact> 
        claimContacts.add(cc);
    }

    private String getReporterInfo() {
        return " Name=\"" + reporterEP.getFirstName() + " " + reporterEP.getLastName() + "\" Phone=" + reporter.getWorkPhone() + " Email=" + reporter.getEmailAddress1();
    }
}
