package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.OutOfState;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.OutOfStateAttachments;
import com.pwc.us.common.utils.NullChecker;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
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
public class OutOfStateActionBean extends BaseActionBean {

    private static Logger logger = LoggerFactory.getLogger(OutOfStateActionBean.class);
    private static final String FORM = "/WEB-INF/jsp/outOfStateForm.jsp";
    public static final String DIR = "/usr/share/tomcat6/temp/";
    public static final String OOS_SUCCESS = "OutOfState.Success";
    @Inject
    private OutOfState outOfState;

    @DefaultHandler
    public Resolution form() {
        Resolution ret = null;

        ret = new ForwardResolution(FORM);

        return ret;
    }

    public Resolution submit() {
        Resolution ret = null;
        try {

            OutOfStateAttachments outOfStateAttachments = new OutOfStateAttachments();

            if (NullChecker.isNotNullOrEmpty(application)) {
                outOfStateAttachments.setApplication(addFile(application));
            }

            if (NullChecker.isNotNullOrEmpty(supplemental)) {
                outOfStateAttachments.setSupplemental(addFile(supplemental));
            }

            if (NullChecker.isNotNullOrEmpty(documents)) {
                if (documents.size() >= 1) {
                    if (NullChecker.isNotNullOrEmpty(documents.get(0))) {
                        outOfStateAttachments.setAddDocument1(addFile(documents.get(0)));
                    }
                }

                if (documents.size() >= 2) {
                    if (NullChecker.isNotNullOrEmpty(documents.get(1))) {
                        outOfStateAttachments.setAddDocument2(addFile(documents.get(1)));
                    }
                }

                if (documents.size() >= 3) {
                    if (NullChecker.isNotNullOrEmpty(documents.get(2))) {
                        outOfStateAttachments.setAddDocument3(addFile(documents.get(2)));
                    }
                }

                if (documents.size() >= 4) {
                    if (NullChecker.isNotNullOrEmpty(documents.get(3))) {
                        outOfStateAttachments.setAddDocument4(addFile(documents.get(3)));
                    }
                }

                if (documents.size() >= 5) {
                    if (NullChecker.isNotNullOrEmpty(documents.get(4))) {
                        outOfStateAttachments.setAddDocument5(addFile(documents.get(4)));
                    }
                }
            }

            boolean response = outOfState.processRequest(outOfStateAttachments);
            //Condition the message based on the returned value
            if (response == true) {
                getContext().getMessages().add(
                        new LocalizableMessage(OOS_SUCCESS, response));
                ret = new RedirectResolution(OutOfStateActionBean.class);
            } else {
                logger.info("Unable to process Out of State Request " + outOfStateAttachments);
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError("OutOfState.Error.RequestFailed", outOfStateAttachments));
                ret = new ForwardResolution(this.getClass(), "form");
            }
        } catch (GwIntegrationException e) {
            logger.error("Unable to process OutOfState Request ", e);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("OutOfState.Error.RequestFailed", e));
            ret = new ForwardResolution(this.getClass(), "form");
        } catch (Exception ex) {
            logger.error("Unable to process OutOfState Request ", ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("OutOfState.Error.RequestFailed", ex));
            ret = new ForwardResolution(this.getClass(), "form");
        }
        return ret;
    }

    @DontValidate
    public Resolution cancel() {
        getContext().getMessages().add(
                new SimpleMessage("Action cancelled."));
        return new RedirectResolution(HelloActionBean.class);
    }

    private File addFile(FileBean fileBean) throws Exception {
        //File tempFile = File.createTempFile(PREFIX, SUFFIX);
        File tempFile = new File(DIR + fileBean.getFileName());
        tempFile.deleteOnExit();
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(fileBean.getInputStream(), out);
        } catch (IOException ex) {
            String msg = "Unable to process Out Of State Request due to file: " + fileBean.getFileName();
            logger.error(msg, ex);
            throw new GwIntegrationException(msg, ex);
        }

        return tempFile;
    }

    private boolean checkFile(FileBean fileBean) {
        boolean ret = true;

        if (NullChecker.isNotNullOrEmpty(fileBean)) {
            if (!(fileBean.getSize() > 0)) {
                ret = false;
            }
            String fname = fileBean.getFileName().toLowerCase();
            ret = false;
            if (fname.endsWith(".doc")) {
                return true;
            } else if (fname.endsWith(".docx")) {
                return true;
            } else if (fname.endsWith(".txt")) {
                return true;
            } else if (fname.endsWith(".rtf")) {
                return true;
            } else if (fname.endsWith(".tiff")) {
                return true;
            } else if (fname.endsWith(".jpg")) {
                return true;
            } else if (fname.endsWith(".png")) {
                return true;
            } else if (fname.endsWith(".gif")) {
                return true;
            } else if (fname.endsWith(".xls")) {
                return true;
            } else if (fname.endsWith(".xlsx")) {
                return true;
            } else if (fname.endsWith(".pdf")) {
                return true;
            }
        }
        return ret;
    }

    @ValidationMethod(on = "submit")
    public void validateDocuments(ValidationErrors errors) {

        if (!checkFile(application)) {
            ValidationError error = new SimpleError(application.getFileName() + " is not a valid file, or acceptable file type.");
            getContext().getValidationErrors().add("application", error);
        }

        if (!checkFile(supplemental)) {
            ValidationError error = new SimpleError(supplemental.getFileName() + " is not a valid file, or acceptable file type.");
            getContext().getValidationErrors().add("supplemental", error);
        }

        if (NullChecker.isNotNullOrEmpty(documents)) {
            if (documents.size() >= 1) {
                if (!checkFile(documents.get(0))) {
                    ValidationError error = new SimpleError(documents.get(0).getFileName() + " is not a valid file, or acceptable file type.");
                    getContext().getValidationErrors().add("documents", error);
                }
            }

            if (documents.size() >= 2) {
                if (!checkFile(documents.get(1))) {
                    ValidationError error = new SimpleError(documents.get(1).getFileName() + " is not a valid file, or acceptable file type.");
                    getContext().getValidationErrors().add("documents", error);
                }
            }

            if (documents.size() >= 3) {
                if (!checkFile(documents.get(2))) {
                    ValidationError error = new SimpleError(documents.get(2).getFileName() + " is not a valid file, or acceptable file type.");
                    getContext().getValidationErrors().add("documents", error);
                }
            }

            if (documents.size() >= 4) {
                if (!checkFile(documents.get(3))) {
                    ValidationError error = new SimpleError(documents.get(3).getFileName() + " is not a valid file, or acceptable file type.");
                    getContext().getValidationErrors().add("documents", error);
                }
            }

            if (documents.size() >= 5) {
                if (!checkFile(documents.get(4))) {
                    ValidationError error = new SimpleError(documents.get(4).getFileName() + " is not a valid file, or acceptable file type.");
                    getContext().getValidationErrors().add("documents", error);
                }
            }
        }
    }

    @Validate(required = true, on = "submit")
    public FileBean application;

    public FileBean getApplication() {
        return application;
    }

    public void setApplication(FileBean application) {
        this.application = application;
    }

    @Validate(required = true, on = "submit")
    public FileBean supplemental;

    public FileBean getSupplemental() {
        return supplemental;
    }

    public void setSupplemental(FileBean supplemental) {
        this.supplemental = supplemental;
    }

    public List<FileBean> documents;

    public List<FileBean> getDocuments() {
        return documents;
    }

    public void setDocuments(List<FileBean> documents) {
        this.documents = documents;
    }
}
