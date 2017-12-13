pushd .
cd src\main\resources\xsd\cc\api\fnoi\gx\note
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/Note/NoteTopicTypeModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/Note/NoteModel.gx
popd
pushd .
cd  src\main\resources\xsd\cc\api\fnoi\gx\address
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/address/AddressModel.gx
popd
pushd .
cd  src\main\resources\xsd\cc\api\fnoi\gx\claim
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/claim/ClaimModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/claim/EmploymentDataModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/claim/WorkStatusModel.gx
popd
pushd .
cd  src\main\resources\xsd\cc\api\fnoi\gx\contact
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/contact/ClaimContactModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/contact/ClaimContactRoleModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/contact/ContactModel.gx
popd
pushd .
cd  src\main\resources\xsd\cc\api\fnoi\gx\incident
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/incident/BodilyInjuryPointModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/incident/BodyPartDetailsModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/incident/IncidentModel.gx
popd
pushd .
cd  src\main\resources\xsd\cc\api\fnoi\gx\policy
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/policy/PolicyLocationModel.gx
wget -N  http://%1:8080/cc/ws/compsource/cc/api/fnoi/gx/policy/PolicyModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\coi\gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/coi/gx/CertificateOfInsurance_ExtModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\newsubmission\gx\account
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/account/AccountModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\newsubmission\gx\contact
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/contact/ContactModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/contact/PolicyContactRoleModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/contact/WC7Addlinterest_ExtModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\newsubmission\gx\location
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/location/AddressModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/location/PolicyLocationModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\newsubmission\gx\policy
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/policy/PolicyLineAnswerModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/policy/PeriodAnswerModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/policy/PolicyLineModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/policy/PolicyPeriodModel.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/newsubmission/gx/policy/PriorPolicyModel.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\payrollreport\gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/csauditinformation_model.gx
copy csauditinformation_model.gx \guidewire\portal\WebServiceClient\src\main\resources\wsdl\csauditinformation_model.xsd /y
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/csauditinformationdetails_model.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/cscoveredemployee_model.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/cspolicyperiod_model.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/cspremiumdetails_model.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/cssuppldiseaseexposure_model.gx
wget -N  http://%1:8180/pc/ws/compsource/pc/api/payrollreport/gx/csupdatecoveredemployeeresponse_model.gx
popd
pushd .
cd  src\main\resources\xsd\pc\api\portalauth\webservice\bcportalauthwsc
wget -N  http://%1:8180/pc/ws/compsource/pc/api/portalauth/webservice/bcportalauthWSC/soapheaders.xsd
popd
pushd .
cd  src\main\resources\xsd\pc\api\portalauth\webservice\ccportalauthwsc
wget -N  http://%1:8180/pc/ws/compsource/pc/api/portalauth/webservice/ccportalauthWSC/soapheaders.xsd
popd








