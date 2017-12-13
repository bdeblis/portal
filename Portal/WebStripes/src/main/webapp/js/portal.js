/*! Portal Custom Java Script */
function hideDivs() {
    if (!$('#emailCheckbox').attr("checked")) {
        $('#emailDiv').hide();
    }
    if (!$('#faxCheckbox').attr("checked")) {
        $('#faxDiv').hide();
    }
}

function setQuestionDefaults() {
    if (!($('#isAlternateEmployerNo').attr("checked") ||
            $('#isAlternateEmployerYes').attr("checked"))) {
        $('#isAlternateEmployerNo').prop("checked", true);
    }
    if (!($('#isWaiverOfSubrogationNo').attr("checked") ||
            $('#isWaiverOfSubrogationYes').attr("checked"))) {
        $('#isWaiverOfSubrogationNo').prop("checked", true);
    }
    if (!($('#withThirtyDayNoticeNo').attr("checked") ||
            $('#withThirtyDayNoticeYes').attr("checked"))) {
        $('#withThirtyDayNoticeNo').prop("checked", true);
    }
}

// Hide/Show Email Div
$('#emailCheckbox').change(function() {
    if ($(this).attr("checked")) {

        $('#emailDiv').fadeIn();
        return;
    }
    $('#emailDiv').fadeOut();
});

// Hide/Show Fax Div
$('#faxCheckbox').change(function() {
    if ($(this).attr("checked")) {

        $('#faxDiv').fadeIn();
        return;
    }
    $('#faxDiv').fadeOut();
}
);

// Hide/Show Detailed Body Part Divs
/* $("input[id^='bodyPartID']").change(function() {
 var i = this.id.substr(this.id.length-1,this.id.length);
 if ($(this).attr("checked")) {
 
 $('#detailedBodyDiv'.i).fadeIn();
 return;
 }
 $('#detailedBodyDiv'.i).fadeOut();
 }
 ); */
$('#bodyPartID1').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv1').fadeIn();
        return;
    }
    $('#detailedBodyDiv1').fadeOut();
}
);

$('#bodyPartID2').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv2').fadeIn();
        return;
    }
    $('#detailedBodyDiv2').fadeOut();
}
);

$('#bodyPartID3').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv3').fadeIn();
        return;
    }
    $('#detailedBodyDiv3').fadeOut();
}
);

$('#bodyPartID4').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv4').fadeIn();
        return;
    }
    $('#detailedBodyDiv4').fadeOut();
}
);

$('#bodyPartID5').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv5').fadeIn();
        return;
    }
    $('#detailedBodyDiv5').fadeOut();
}
);

$('#bodyPartID6').change(function() {
    if ($(this).attr("checked")) {

        $('#detailedBodyDiv6').fadeIn();
        return;
    }
    $('#detailedBodyDiv6').fadeOut();
}
);

function hideBodyPartDivs() {
    //$("div[id^='detailedBodyDiv']").hide();
    if (!$('#bodyPartID1').attr("checked")) {
        $('#detailedBodyDiv1').hide();
    }
    if (!$('#bodyPartID2').attr("checked")) {
        $('#detailedBodyDiv2').hide();
    }
    if (!$('#bodyPartID3').attr("checked")) {
        $('#detailedBodyDiv3').hide();
    }
    if (!$('#bodyPartID4').attr("checked")) {
        $('#detailedBodyDiv4').hide();
    }
    if (!$('#bodyPartID5').attr("checked")) {
        $('#detailedBodyDiv5').hide();
    }
    if (!$('#bodyPartID6').attr("checked")) {
        $('#detailedBodyDiv6').hide();
    }
}

function hideShowInjuryDivs() {
    var v = $('#generalInjury').val();
    
    switch (v)
    {
        case "occupational":
          $('#cumulative').hide();
          $('#det').hide();
          $('#multiple').hide();
          $('#occupational').hide().fadeIn();
          $("#occupational").val($("#occupational option:first").val());
            break;
        case "specific":
          $('#cumulative').hide();
          $('#det').hide().fadeIn();
          $('#multiple').hide();
          $('#occupational').hide();
          $("#det").val($("#det option:first").val());
            break;
        case "multiple":
          $('#cumulative').hide();
          $('#det').hide();
          $('#multiple').hide().fadeIn();
          $('#occupational').hide();
          $("#multiple").val($("#multiple option:first").val());
            break;
        case "cumulative":
          $('#cumulative').hide().fadeIn();
          $('#det').hide();
          $('#multiple').hide();
          $('#occupational').hide();
          $("#cumulative").val($("#cumulative option:first").val());
            break;
        default:
         $('#cumulative').hide();
         $('#det').hide();
         $('#multiple').hide();
         $('#occupational').hide();
    }
}

function hideShowReturnToWorkDiv() {
    var v = $('#returnToWorkQuestion').val();
    switch (v)
    {
        case "Y":
            $('#returnToWorkDiv').fadeIn();
            break;
        case "N":
            $('#returnToWorkDiv').hide();
            break;
        default:
            $('#returnToWorkDiv').hide();
    }
}

function hideShowDeathDateDiv() {
    var v = $('#deathDateQuestion').val();
    switch (v)
    {
        case "Y":
            $('#deathDateDiv').fadeIn();
            break;
        case "N":
            $('#deathDateDiv').hide();
            break;
        default:
            $('#deathDateDiv').hide();
    }
}

// Hide/Show Email Div
$('#generalInjury').change(function() {
    hideShowInjuryDivs();
});

$('#returnToWorkQuestion').change(function() {
    hideShowReturnToWorkDiv();
});

$('#deathDateQuestion').change(function() {
    hideShowDeathDateDiv();
});

var witnessRowNum = 0;
$('#addWitness').live('click', function() {
    if(witnessRowNum >= 2)
        return false;
    witnessRowNum++;
    var witnessRows = $('#witnessRows');
    $('<div class="portal-row" id="witnessRowNum' + witnessRowNum + '">' +
            '<div class="portal-col-33">' +
            '<label for="witnessEP.heading" class="portal-label-33">Witness:</label>' +
            '</div>' +
            '<div class="portal-col-66">' +
            '<input placeholder="First Name" name="witnessList[' + witnessRowNum + '].firstName" class="portal-width-40" type="text">' +
            '<input placeholder="Last Name" name="witnessList[' + witnessRowNum + '].lastName" class="portal-width-40" type="text">' +
            '</div>' +
            '<div class="portal-error">'+
            '<label for="witnessList[' + witnessRowNum + '].firstName" class="error"></label>'+
            '<label for="witnessList[' + witnessRowNum + '].lastName" class="error"></label>'+
            '</div>'+
            '</div>').appendTo(witnessRows);
    placeholderCheck();
    return false;
});


$.validator.addMethod(
    "validateLastNameNotMissing",
    function (value, element, params) {
        var thing1 = $('input[name="' + params[0] + '"]').val(),
            thing2 = $('input[name="' + params[1] + '"]').val();
        if ((!isBlankOrEmpty(thing1) && isBlankOrEmpty(thing2))){
            {
                return false;
            }
        } 
        return true;
    },
    "This requires both items."   //Users should never see this -- always supply a customized error message.
);



$.validator.addMethod(
    "validateFirstNameNotMissing",
    function (value, element, params) {
        var thing1 = $('input[name="' + params[0] + '"]').val(),
            thing2 = $('input[name="' + params[1] + '"]').val();
        if ((isBlankOrEmpty(thing1) && !isBlankOrEmpty(thing2))){
            {
                return false;
            }
        } 
        return true;
    },
    "This requires both items."   //Users should never see this -- always supply a customized error message.
);



$('.datepick').each(function() {
    $(this).datepicker({
        dateFormat: "mm-dd-yy",
        changeMonth: true,
        changeYear: true,
        step: "-100:+0",
        minDate: "-100Y",
        maxDate: "+0Y",
        yearRange: "-100:+0",
        fixFocusIE: true,    
        onSelect: function(dateText, inst) {
            this.fixFocusIE = true;
        },
        onClose: function(dateText, inst) {
                  this.fixFocusIE = true;
            },
            beforeShow: function(input, inst) {
                  var result = $.browser.msie ? !this.fixFocusIE : true;
                  this.fixFocusIE = false;
                  return result;
            }
       
    });
});

$('.datepickFuture').each(function() {
    $(this).datepicker({
        dateFormat: "mm-dd-yy",
        changeMonth: true,
        changeYear: true,
        minDate: "-10Y",
        maxDate: "+20Y",
        fixFocusIE: true,    
        onSelect: function(dateText, inst) {
            this.fixFocusIE = true;
        },
        onClose: function(dateText, inst) {
                  this.fixFocusIE = true;
            },
            beforeShow: function(input, inst) {
                  var result = $.browser.msie ? !this.fixFocusIE : true;
                  this.fixFocusIE = false;
                  return result;
            }
       
    });
});

$('.timepick').each(function() {
    $(this).timepicker({
        'timeFormat': 'g:i a',
        'scrollDefaultTime': "08:00",
        'step': 15,
        onSelect: function(dateText, inst) {
            $(this).focus();
        }
    });
});

var attachmentRowNum = 0;
$('#addAttachment').live('click', function() {
    attachmentRowNum++;
    var attachmentRows = $('#attachmentRows');
    $('<div class="sem-field-container" id="attachmentRowNum' + attachmentRowNum + '"  >' +
            '<input name="documents[' + attachmentRowNum + ']" class="sem-field sem-text " type="file" />' +
            ' </div>' +
            '</div> ').appendTo(attachmentRows);
    if (attachmentRowNum > 3) {
        $('#addAttachmentLink').hide();
    }
    return false;
});


var ownerOfficerRowNum = 0;
//$('.ownerOfficer:not(:first)').hide();
$('#addOwnerOfficer').live('click', function() {
    if (ownerOfficerRowNum < 4) {
        ownerOfficerRowNum++; 
    } else {
        $('.onlyFiveOwnerOfficers').show();
    }
    
    if (ownerOfficerRowNum > 0) {
        $('#removeOwnerOfficer').fadeIn();
    }
    $('.ownerOfficer:hidden').first().show();
    return false;
});

$('#removeOwnerOfficer').on('click', function() {
    ownerOfficerRowNum--;
    if (ownerOfficerRowNum <= 0) {
        $('#removeOwnerOfficer').fadeOut();
    } 
    $('.onlyFiveOwnerOfficers').hide();
    $('.addlOwnerOfficer:not(:hidden):last').hide();
    return false;
});

var locationRowNum = 0;
$('#addAddlLocation').live('click', function() {
    if (locationRowNum < 3) {
        locationRowNum++; 
    } else {
        $('.onlyThreeAddlLocations').fadeIn();
        $('#addAddlLocation').hide();
    }
    
    if (locationRowNum > 0) {
        $('#removeAddlLocation').fadeIn();
    }
    $('.addlLocation:hidden').first().fadeIn();
    return false;
});

$('#removeAddlLocation').on('click', function() {
    locationRowNum--;
    if (locationRowNum <= 0) {
        $('#removeAddlLocation').fadeOut();
    }

    if (locationRowNum < 3) {
        $('#addAddlLocation').fadeIn();    
    }
    $('.onlyThreeAddlLocations').fadeOut();
    $('.addlLocation:not(:hidden):last').hide();
    return false;
});

function hideShowPrevCoverage() {
    var checkShow = $('#hadPreviousCoverageYes'),
            checkHide = $('#hadPreviousCoverageNo'),
            field = $('#previous_coverage');
    return hideShowField(checkShow, checkHide, field);
}


function hideShowTaxId() {
    var checkShow = $('#hasFederalTaxIdYes'),
            checkHide = $('#hasFederalTaxIdNo'),
            field = $('#taxId');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowQuoteAllOKBusiness() {
    var checkShow = $('#quoteAllOKBusinessNo'),
            checkHide = $('#quoteAllOKBusinessYes'),
            field = $('#quoteAllOKBusinessExplain');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowEmployeesOutsideOK() {
    var checkShow = $('#employeesOutsideOKYes'),
            checkHide = $('#employeesOutsideOKNo'),
            field = $('#employeesOutsideOKText');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowListOutsideOKStates() {
    var checkShow = $('#businessOperateOutsideOKYes'),
            checkHide = $('#businessOperateOutsideOKNo'),
            field = $('#listOutsideOKStates');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowExplainLiquidation() {
    var checkShow = $('#terminatingBusinessYes'),
            checkHide = $('#terminatingBusinessNo'),
            field = $('#terminatingBusinessText');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowDeniedInsurance() {
    var checkShow = $('#associationDeniedInsuranceYes'),
            checkHide = $('#associationDeniedInsuranceNo'),
            field = $('#associationDeniedInsuranceText');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowSubcontractors() {
    var checkShow = $('#subcontractorsUsedYes'),
            checkHide = $('#subcontractorsUsedNo'),
            field = $('#subcontractorsPercent');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowRequiredIfOtherOKBus() {
    var checkShow = $('#ownAnotherBusinessYes'),
            checkHide = $('#ownAnotherBusinessNo'),
            field = $('#requiredIfOtherOKBus');
    return hideShowField(checkShow, checkHide, field);
}

function hideShowField(radioShow, radioHide, divField) {
    radioShow.on('click', function() {
        divField.fadeIn();
    });
    radioHide.on('click', function() {
        divField.fadeOut();
    });
    return false;
}

var datePattern = /^(\d{2})-(\d{2})-(\d{4})$/;
var policyPeriodPattern = /(\d{2})-(\d{2})-(\d{4}) - (\d{2})-(\d{2})-(\d{4})/;
var ssnPattern = /^\d{3}-\d{2}-\d{4}$/;
var ssnOrEinPattern = /^\d{2}-\d{7}$|^\d{3}-\d{2}-\d{4}$/;
// var phonePattern = /^(\([2-9]\d{2}\)|[2-9]\d{2}-)[2-9]\d{2}-\d{4}$/;
var phonePattern = /^[2-9]\d{2}-\d{3}-\d{4}$/;
var ratingIdPattern = /^\d{9}$/;
var floatingPointPattern = /((\.\d+)|(\d+(\.\d+)?))$/;
var timePattern = /^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/;
var userNamePattern = /^[a-zA-Z0-9,.\&'-]{4,}$/;

                          

$.validator.addMethod(
    "validateIfOneThenBoth",
    function (value, element, params) {
        var thing1 = $('input[name="' + params[0] + '"]').val(),
            thing2 = $('input[name="' + params[1] + '"]').val();
        if ((!isBlankOrEmpty(thing1) && isBlankOrEmpty(thing2)) || 
                (!isBlankOrEmpty(thing2) && isBlankOrEmpty(thing1))){
            {
                return false;
            }
        } else {
           if ((thing1 && !value) || (!thing1 && value)) {
                return false;
            } 
        }
        return true;
    },
    "This requires both items."   //Users should never see this -- always supply a customized error message.
);

$.validator.addMethod(
    "ifEitherOneOrTwoThenThree",
    function (value, element, params) {
        var thing1 = $('input[name="' + params[0] + '"]').val(),
            thing2 = $('input[name="' + params[1] + '"]').val();
        if((thing1 || thing2) && !value) {
            return false;
        }
        return true;    
    },
    "The presence of items one or two requires a third item."  // Users should never see this -- always supply a customized error message.
);

$.validator.messages.required = "This field is required. Please select an answer.";
var quoteRequestValidator = $('#quoteRequestForm').validate({
    rules: {
        "priorPolicies[0].expirationDate": {
            required: false,
            priorPoliciesDate: true
        },
	"priorPolicies[0].anniversaryDateExt": {
            required: false,
            priorPoliciesDate: true
        },
	"priorPolicies[0].expModEffDateExt": {
            required: false,
            priorPoliciesDate: true
        },
        "priorPolicies[0].ratingIdExt": {
            required: false,
            pattern: ratingIdPattern
        },
        "priorPolicies[0].expMod": {
            required: false,
            pattern: floatingPointPattern
        },
        "isTempStaffing.booleanAnswer": {
            required: true
        },
        "opsOutsideOk.booleanAnswer": {
            required: true
        },
        "totalAnnualPayroll.integerAnswer": {
            digits: true
        },
        "accountHolderContact.name": {
            required: true
        },
        "account.busOpsDesc": {
            required: true
        },
        "accountHolderPrimaryAddress.addressLine1": {
            required: true
        },
        "accountHolderPrimaryAddress.county": {
            required: true
        },
        "accountHolderPrimaryAddress.city": {
            required: true
        },
        "accountHolderPrimaryAddress.state": {
            required: true
        },
        "accountHolderPrimaryAddress.postalCode": {
            required: true,
            zipcodeUS: true
        },
        "accountHolderContact.workPhone": {
            required: true,
            pattern: phonePattern
        },
        "primaryNamedInsured.orgTypeExt": {
            required: true
        },
        "hasFederalTaxId": {
            required: true
        },
        "accountHolderContact.taxID": {
            required: 'input[name=hasFederalTaxId][value=true]:checked',
            pattern: ssnOrEinPattern
        },
        "auditorEntityPerson.firstName": {
             validateIfOneThenBoth: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName']
         },
        "auditorEntityPerson.lastName": {
             validateIfOneThenBoth: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName']
         },
        "auditorAddress.addressLine1": {
            ifEitherOneOrTwoThenThree: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName', 'auditorAddress.addressLine1']
        },
        "auditorAddress.city": {
            ifEitherOneOrTwoThenThree: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName', 'auditorAddress.city']
        },
        "auditorAddress.state": {
            ifEitherOneOrTwoThenThree: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName', 'auditorAddress.state']
        },
        "auditor.workPhone": {
            ifEitherOneOrTwoThenThree: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName', 'auditor.workPhone'],
            pattern: phonePattern
        },
        "auditorAddress.postalCode": {
            ifEitherOneOrTwoThenThree: ['auditorEntityPerson.firstName', 'auditorEntityPerson.lastName', 'auditorAddress.postalCode'],
            zipcodeUS: true
        },
        "hadPreviousCoverage": {
            required: true
        },
        "ownAnotherBusiness.booleanAnswer": {
            required: true
        },
        "needWCAndLiability.booleanAnswer": {
            required: "input[name='ownAnotherBusiness.booleanAnswer'][value=true]:checked"
        },
        "quoteAllOKBusiness.booleanAnswer": {
            required: "input[name='ownAnotherBusiness.booleanAnswer'][value=true]:checked"
        },
        "quoteAllOKBusinessExplain.textAnswer": {
            required: function() {
                var otherBus = $("input[name='ownAnotherBusiness.booleanAnswer']").val() === 'true';
                var quoteAll = $("input[name='quoteAllOKBusiness.booleanAnswer']").val() === 'true';
                return (otherBus && quoteAll);
            }
        },
        "employeesOutsideOK.booleanAnswer": {
            required: true
        },
        "employeesOutsideOKText.textAnswer": {
            required: "input[name='employeesOutsideOK.booleanAnswer'][value=true]:checked"
        },
        "businessOperateOutsideOK.booleanAnswer": {
            required: true
        },
        "businessOperateOutsideOKText.textAnswer": {
            required: "input[name='businessOperateOutsideOK.booleanAnswer'][value=true]:checked"
        },
        "terminatingBusiness.booleanAnswer": {
            required: true
        },
        "terminatingBusinessText.textAnswer": {
            required: "input[name='terminatingBusiness.booleanAnswer'][value=true]:checked"
        },
        "associationDeniedInsurance.booleanAnswer": {
            required: true
        },
        "associationDeniedInsuranceText.textAnswer": {
            required: "input[name='associationDeniedInsurance.booleanAnswer'][value=true]:checked"
        },
        "employDomestics.booleanAnswer": {
            required: true
        },
        "farmEmployees.booleanAnswer": {
            required: true
        },
        "familyMembers.booleanAnswer": {
            required: true
        },
        "hqOutsideOK.booleanAnswer": {
            required: true
        },
        "oosEmployees.booleanAnswer": {
            required: true
        },
        "boatsPlanes.booleanAnswer": {
            required: true
        },
        "hazmat.booleanAnswer": {
            required: true
        },
        "aboveOrBelowGround.booleanAnswer": {
            required: true
        },
        "onOrOverWater.booleanAnswer": {
            required: true
        },
        "engagedInOtherBusiness.booleanAnswer": {
            required: true
        },
        "subcontractorsUsed.booleanAnswer": {
            required: true
        },
        "subcontractorsPercent.integerAnswer": {
            required: "input[name='subcontractorsUsed.booleanAnswer'][value=true]:checked",
            pattern: /^[1-9][0-9]?$|^100$/
        },
        "anyWorkSublet.booleanAnswer": {
            required: true
        },
        "safetyProgram.booleanAnswer": {
            required: true
        },
        "groupTransportation.booleanAnswer": {
            required: true
        },
        "ageRange.booleanAnswer": {
            required: true
        },
        "seasonalEmployees.booleanAnswer": {
            required: true
        },
        "volunteer.booleanAnswer": {
            required: true
        },
        "disabledEmployees.booleanAnswer": {
            required: true
        },
        "oosTravel.booleanAnswer": {
            required: true
        },
        "athleticSponsorship.booleanAnswer": {
            required: true
        },
        "physicalsRequired.booleanAnswer": {
            required: true
        },
        "otherInsurance.booleanAnswer": {
            required: true
        },
        "priorDeclined.booleanAnswer": {
            required: true
        },
        "employeeHealthPlans.booleanAnswer": {
            required: true
        },
        "laborInterchange.booleanAnswer": {
            required: true
        },
        "leaseEmployees.booleanAnswer": {
            required: true
        },
        "workAtHome.booleanAnswer": {
            required: true
        },
        "taxLiens.booleanAnswer": {
            required: true
        },
        "unpaidPremium.booleanAnswer": {
            required: true
        },
        "ownerOfficers[0].taxID": {
            pattern: ssnOrEinPattern,
            ifEitherOneOrTwoThenThree: ["ownerOfficers[0].entityPerson.firstName","ownerOfficers[0].entityPerson.lastName"]
        },
        "ownerOfficers[0].entityPerson.firstName": {
            validateIfOneThenBoth: ["ownerOfficers[0].entityPerson.firstName","ownerOfficers[0].entityPerson.lastName"]
        },
        "ownerOfficers[0].entityPerson.lastName": {
            validateIfOneThenBoth: ["ownerOfficers[0].entityPerson.firstName","ownerOfficers[0].entityPerson.lastName"]
        },
        "ownerOfficers[0].activeInBusiness": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[0].entityPerson.firstName","ownerOfficers[0].entityPerson.lastName"]
        },
        "ownerOfficers[0].coverageDesired": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[0].entityPerson.firstName","ownerOfficers[0].entityPerson.lastName"]
        },
        "ownerOfficers[1].taxID": {
            pattern: ssnOrEinPattern,
            ifEitherOneOrTwoThenThree: ["ownerOfficers[1].entityPerson.firstName","ownerOfficers[1].entityPerson.lastName"]
        },
        "ownerOfficers[1].entityPerson.firstName": {
            validateIfOneThenBoth: ["ownerOfficers[1].entityPerson.firstName","ownerOfficers[1].entityPerson.lastName"]
        },
        "ownerOfficers[1].entityPerson.lastName": {
            validateIfOneThenBoth: ["ownerOfficers[1].entityPerson.firstName","ownerOfficers[1].entityPerson.lastName"]
        },
        "ownerOfficers[1].activeInBusiness": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[1].entityPerson.firstName","ownerOfficers[1].entityPerson.lastName"]
        },
        "ownerOfficers[1].coverageDesired": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[1].entityPerson.firstName","ownerOfficers[1].entityPerson.lastName"]
        },
        "ownerOfficers[2].taxID": {
            pattern: ssnOrEinPattern,
            ifEitherOneOrTwoThenThree: ["ownerOfficers[2].entityPerson.firstName","ownerOfficers[2].entityPerson.lastName"]
        },
        "ownerOfficers[2].entityPerson.firstName": {
            validateIfOneThenBoth: ["ownerOfficers[2].entityPerson.firstName","ownerOfficers[2].entityPerson.lastName"]
        },
        "ownerOfficers[2].entityPerson.lastName": {
            validateIfOneThenBoth: ["ownerOfficers[2].entityPerson.firstName","ownerOfficers[2].entityPerson.lastName"]
        },
        "ownerOfficers[2].activeInBusiness": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[2].entityPerson.firstName","ownerOfficers[2].entityPerson.lastName"]
        },
        "ownerOfficers[2].coverageDesired": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[2].entityPerson.firstName","ownerOfficers[2].entityPerson.lastName"]
        },
        "ownerOfficers[3].taxID": {
            pattern: ssnOrEinPattern,
            ifEitherOneOrTwoThenThree: ["ownerOfficers[3].entityPerson.firstName","ownerOfficers[3].entityPerson.lastName"]
        },
        "ownerOfficers[3].entityPerson.firstName": {
            validateIfOneThenBoth: ["ownerOfficers[3].entityPerson.firstName","ownerOfficers[3].entityPerson.lastName"]
        },
        "ownerOfficers[3].entityPerson.lastName": {
            validateIfOneThenBoth: ["ownerOfficers[3].entityPerson.firstName","ownerOfficers[3].entityPerson.lastName"]
        },
        "ownerOfficers[3].activeInBusiness": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[3].entityPerson.firstName","ownerOfficers[3].entityPerson.lastName"]
        },
        "ownerOfficers[3].coverageDesired": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[3].entityPerson.firstName","ownerOfficers[3].entityPerson.lastName"]
        },
        "ownerOfficers[4].taxID": {
            pattern: ssnOrEinPattern,
            ifEitherOneOrTwoThenThree: ["ownerOfficers[4].entityPerson.firstName","ownerOfficers[4].entityPerson.lastName"]
        },
        "ownerOfficers[4].entityPerson.firstName": {
            validateIfOneThenBoth: ["ownerOfficers[4].entityPerson.firstName","ownerOfficers[4].entityPerson.lastName"]
        },
        "ownerOfficers[4].entityPerson.lastName": {
            validateIfOneThenBoth: ["ownerOfficers[4].entityPerson.firstName","ownerOfficers[4].entityPerson.lastName"]
        },
        "ownerOfficers[4].activeInBusiness": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[4].entityPerson.firstName","ownerOfficers[4].entityPerson.lastName"]
        },
        "ownerOfficers[4].coverageDesired": {
            ifEitherOneOrTwoThenThree: ["ownerOfficers[4].entityPerson.firstName","ownerOfficers[4].entityPerson.lastName"]
        },
        "additionalLocations[0].accountLocation.state": {
            validateIfOneThenBoth: ["additionalLocations[0].accountLocation.addressLine1","additionalLocations[0].accountLocation.state"]
        },
        "additionalLocations[0].accountLocation.city": {
            validateIfOneThenBoth: ["additionalLocations[0].accountLocation.addressLine1","additionalLocations[0].accountLocation.city"]
        },
        "additionalLocations[0].accountLocation.county": {
            validateIfOneThenBoth: ["additionalLocations[0].accountLocation.addressLine1","additionalLocations[0].accountLocation.county"]
        },
        "additionalLocations[0].accountLocation.postalCode": {
            validateIfOneThenBoth: ["additionalLocations[0].accountLocation.addressLine1","additionalLocations[0].accountLocation.postalCode"],
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "additionalLocations[0].accountLocation.phone":{
            pattern: phonePattern
        },
        "additionalLocations[1].accountLocation.state": {
            validateIfOneThenBoth: ["additionalLocations[1].accountLocation.addressLine1","additionalLocations[1].accountLocation.state"]
        },
        "additionalLocations[1].accountLocation.city": {
            validateIfOneThenBoth: ["additionalLocations[1].accountLocation.addressLine1","additionalLocations[1].accountLocation.city"]
        },
        "additionalLocations[1].accountLocation.county": {
            validateIfOneThenBoth: ["additionalLocations[1].accountLocation.addressLine1","additionalLocations[1].accountLocation.county"]
        },
        "additionalLocations[1].accountLocation.postalCode": {
            validateIfOneThenBoth: ["additionalLocations[1].accountLocation.addressLine1","additionalLocations[1].accountLocation.postalCode"],
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "additionalLocations[1].accountLocation.phone":{
        pattern: phonePattern
        },
        "additionalLocations[2].accountLocation.state": {
            validateIfOneThenBoth: ["additionalLocations[2].accountLocation.addressLine1","additionalLocations[2].accountLocation.state"]
        },
        "additionalLocations[2].accountLocation.city": {
            validateIfOneThenBoth: ["additionalLocations[2].accountLocation.addressLine1","additionalLocations[2].accountLocation.city"]
        },
        "additionalLocations[2].accountLocation.county": {
            validateIfOneThenBoth: ["additionalLocations[2].accountLocation.addressLine1","additionalLocations[2].accountLocation.county"]
        },
        "additionalLocations[2].accountLocation.postalCode": {
            validateIfOneThenBoth: ["additionalLocations[2].accountLocation.addressLine1","additionalLocations[2].accountLocation.postalCode"],
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "additionalLocations[2].accountLocation.phone":{
        pattern: phonePattern
        },
        "contactEntityPerson.firstName": {
            required: true
        },
        "contactEntityPerson.lastName": {
            required: true
        },
        "contactInfo.workPhone": {
            required: true,
            pattern: phonePattern
        },
        "contactInfo.emailAddress1": {
            required: true,
            email: true
        },
        "contactInfo.faxPhone": {
            required: false,
            pattern: phonePattern
        },
        "contactInfo.relationship": {
            required: true
        }
    },
    messages: {
        "priorPolicies[0].ratingIdExt": {
            pattern: "Please enter nine digits."
        },		
        "accountHolderContact.name": {
            required: "Please enter a Business Name."
        },
        "account.busOpsDesc": {
            required: "Please enter a description of Business Operations."
        },
        "totalAnnualPayroll.integerAnswer": {
            digits: "Please enter an integer value for payroll."
        },
        "accountHolderPrimaryAddress.addressLine1": {
            required: "Please enter a mailing address."
        },
        "accountHolderPrimaryAddress.county": {
            required: "Please select a county."
        },
        "accountHolderPrimaryAddress.city": {
            required: "Please enter a city."
        },
        "accountHolderPrimaryAddress.state": {
            required: "Please select a state."
        },
        "accountHolderPrimaryAddress.postalCode": {
            required: "Please enter a zip code.",
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "accountHolderContact.workPhone": {
            required: "Please enter a phone number.",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "primaryNamedInsured.orgTypeExt": {
            required: "Please select a type of business."
        },
        "accountHolderContact.taxID": {
            required: "You must enter a tax ID if it is available.",
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx"
        },
        "auditorEntityPerson.firstName": {
             validateIfOneThenBoth: "Please enter both first and last name for Contact Person"
         },
        "auditorEntityPerson.lastName": {
             validateIfOneThenBoth: "Please enter both first and last name for Contact Person"
         },
        "auditorAddress.addressLine1": {
            ifEitherOneOrTwoThenThree: "Please enter the address for Contact Person"
        },
        "auditorAddress.city": {
            ifEitherOneOrTwoThenThree: "Please enter the city for Contact Person"
        },
        "auditorAddress.state": {
            ifEitherOneOrTwoThenThree: "Please enter the state for Contact Person"
        },
        "auditor.workPhone": {
            ifEitherOneOrTwoThenThree: "Please enter the work phone for the Contact Person",
            pattern: "Please enter a valid phone number (format xxx-xxx-xxxx)"
        },
        "auditorAddress.postalCode": {
            ifEitherOneOrTwoThenThree: "Please enter a zip code for the Contact Person",
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "quoteAllOKBusinessExplain.textAnswer": {
            required: "If you choose not to obtain a quote for all OK businesses, you must explain why."
        },
        "employeesOutsideOKText.textAnswer": {
            required: "You must list the states if applicable."
        },
        "businessOperateOutsideOKText.textAnswer": {
            required: "You must list the states if applicable."
        },
        "terminatingBusinessText.textAnswer": {
            required: "You must explain the termination or liquidation if applicable."
        },
        "associationDeniedInsuranceText.textAnswer": {
            required: "You must supply names if applicable."
        },
        "subcontractorsPercent.integerAnswer": {
            required: "This field is required. Please enter a percentage between 1 and 100."
        },
        "ownerOfficers[0].taxID": {
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx",
            ifEitherOneOrTwoThenThree: "Tax ID or Social Security Number is required for all owner officers"
        },
        "ownerOfficers[0].entityPerson.firstName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[0].entityPerson.lastName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[0].activeInBusiness": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[0].coverageDesired": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[1].taxID": {
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx",
            ifEitherOneOrTwoThenThree: "Tax ID or Social Security Number is required for all owner officers"
        },
        "ownerOfficers[1].entityPerson.firstName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[1].entityPerson.lastName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[1].activeInBusiness": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[1].coverageDesired": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[2].taxID": {
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx",
            ifEitherOneOrTwoThenThree: "Tax ID or Social Security Number is required for all owner officers"
        },
        "ownerOfficers[2].entityPerson.firstName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[2].entityPerson.lastName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[2].activeInBusiness": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[2].coverageDesired": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[3].taxID": {
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx",
            ifEitherOneOrTwoThenThree: "Tax ID or Social Security Number is required for all owner officers"
        },
        "ownerOfficers[3].entityPerson.firstName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[3].entityPerson.lastName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[3].activeInBusiness": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[3].coverageDesired": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[4].taxID": {
            pattern: "Please enter a valid Federal Tax ID or Social Security Number. Format xxx-xx-xxxx or xx-xxxxxxx",
            ifEitherOneOrTwoThenThree: "Tax ID or Social Security Number is required for all owner officers"
        },
        "ownerOfficers[4].entityPerson.firstName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[4].entityPerson.lastName": {
            validateIfOneThenBoth: "Please enter first and last name for owner officer"
        },
        "ownerOfficers[4].activeInBusiness": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "ownerOfficers[4].coverageDesired": {
            ifEitherOneOrTwoThenThree: "Please choose yes or no for this owner officer"
        },
        "additionalLocations[0].accountLocation.state": {
            validateIfOneThenBoth: "Additional location must include a state."
        },
        "additionalLocations[0].accountLocation.city": {
            validateIfOneThenBoth: "Additional location must include a city."
        },
        "additionalLocations[0].accountLocation.postalCode": {
            validateIfOneThenBoth: "Additional location must include a zip code."
        },
        "additionalLocations[1].accountLocation.state": {
            validateIfOneThenBoth: "Additional location must include a state."
        },
        "additionalLocations[1].accountLocation.city": {
            validateIfOneThenBoth: "Additional location must include a city."
        },
        "additionalLocations[1].accountLocation.postalCode": {
            validateIfOneThenBoth: "Additional location must include a zip code."
        },
        "additionalLocations[2].accountLocation.state": {
            validateIfOneThenBoth: "Additional location must include a state."
        },
        "additionalLocations[2].accountLocation.city": {
            validateIfOneThenBoth: "Additional location must include a city."
        },
        "additionalLocations[2].accountLocation.postalCode": {
            validateIfOneThenBoth: "Additional location must include a zip code."
        },
        "contactEntityPerson.firstName": {
            required: "Please enter your first name"
        },
        "contactEntityPerson.lastName": {
            required: "Please enter your last name"
        },
        "contactInfo.workPhone": {
            required: "Please enter your phone number.",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "contactInfo.emailAddress1": {
            required: "Please enter your email address."
        },
        "contactInfo.faxPhone": {            
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "contactInfo.relationship": {
            required: "Please select your relationship with the business."
        }
    },
    invalidHandler: function() {
        $('#globalError').children().remove();
        $('#globalError').append('<p>' + quoteRequestValidator.numberOfInvalids() + " field(s) need to be corrected. Please correct these fields below.</p>");
        $.unblockUI();
    }
});

$.validator.addMethod("unmaskedMin", function(value, element) {
    var mn = $(element).maskMoney('unmasked')[0];
    var ret = true;
    if ($('input[name=selectedPayAmt]:checked').val() === "otherAmt") {
        ret = (mn > 0);
    }
    return ret;
}, "The dollar amount supplied must be greater than $0.00.");

$.validator.addMethod("amtGreaterThanZero", function(value, element) {
    var ret = true;
    if ($('input[name=selectedPayAmt]:checked').val() !== "otherAmt") {
        ret = ($('#amountToPay').val() > 0);
    }
    return ret;
}, "You must select a value greater than zero.");

var coiRequestValidator = $('#coiForm').validate({
    rules: {
        "certificateOfInsuranceRequest.policyNumber": {
            required: true
        },
        "certificateOfInsuranceRequest.termNumber": {
            required: true
        },
        "certificateOfInsuranceRequest.holderName": {
            required: true
        },
        "certificateOfInsuranceRequest.holderAddress.addressLine1": {
            required: true
        },
        "certificateOfInsuranceRequest.holderAddress.city": {
            required: true
        },
        "certificateOfInsuranceRequest.holderAddress.state": {
            required: true
        },
        "certificateOfInsuranceRequest.requesterPhone": {
            required: true,
            pattern: phonePattern
        },
        "certificateOfInsuranceRequest.holderAddress.postalCode": {
            required: true,
            zipcodeUS: true
        },
        "certificateOfInsuranceRequest.deliverByMail": { 
            required: function (element) {
                var boxes = $('input[type="checkbox"]');
                if (boxes.filter(':checked').length === 0) {
                    return true;
                }
                return false;
            },
            minlength: 1 
        },
        "certificateOfInsuranceRequest.emailAddress1": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.emailRecipientName1', 'certificateOfInsuranceRequest.emailAddress1'],
            email: true
        },
        "certificateOfInsuranceRequest.emailAddress2": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.emailRecipientName2', 'certificateOfInsuranceRequest.emailAddress2'],
            email: true
        },
        "certificateOfInsuranceRequest.emailAddress3": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.emailRecipientName3', 'certificateOfInsuranceRequest.emailAddress3'],
            email: true
        },
        "certificateOfInsuranceRequest.faxNumber1": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.faxRecipientName1', 'certificateOfInsuranceRequest.faxNumber1'],
            pattern: phonePattern
        },
        "certificateOfInsuranceRequest.faxNumber2": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.faxRecipientName2', 'certificateOfInsuranceRequest.faxNumber2'],
            pattern: phonePattern
        },
        "certificateOfInsuranceRequest.faxNumber3": {
            validateIfOneThenBoth: ['certificateOfInsuranceRequest.faxRecipientName3', 'certificateOfInsuranceRequest.faxNumber3'],
            pattern: phonePattern 
        },
        "certificateOfInsuranceRequest.requesterName": {
            required: true
        }
    },
    messages: {
        "certificateOfInsuranceRequest.policyNumber": {
            required: "Please choose a policy number"
        },
        "certificateOfInsuranceRequest.termNumber": {
            required: "Please choose a policy term"
        },
        "certificateOfInsuranceRequest.holderName": {
            required: "Please enter a Certificate Holder Name"
        },
        "certificateOfInsuranceRequest.holderAddress.addressLine1": {
            required: "Please enter an address for the Certificate Holder"
        },
        "certificateOfInsuranceRequest.holderAddress.city": {
            required: "Please enter a city"
        },
        "certificateOfInsuranceRequest.holderAddress.state": {
            required: "Please enter a state"
        },
        "certificateOfInsuranceRequest.requesterPhone": {
            required: "Please enter your phone number.",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "certificateOfInsuranceRequest.holderAddress.postalCode": {
            zipcodeUS: "Please enter a valid zip code in the format xxxxx or xxxxx-xxxx",
            required: "Please enter a valid zip code in the format xxxxx or xxxxx-xxxx" 
        },
        "certificateOfInsuranceRequest.deliverByMail": { 
            required: "Please choose at least one method of delivery.",
            minlength: "Please choose at least one method of delivery."
        },
        "certificateOfInsuranceRequest.emailAddress1": {
            validateIfOneThenBoth: "Please enter both recipient name and email address",
            email: "Please enter a valid email address"
        },
        "certificateOfInsuranceRequest.emailAddress2": {
            validateIfOneThenBoth: "Please enter both recipient name and email address",
            email: "Please enter a valid email address"
        },
        "certificateOfInsuranceRequest.emailAddress3": {
            validateIfOneThenBoth: "Please enter both recipient name and email address",
            email: "Please enter a valid email address"
        },
        "certificateOfInsuranceRequest.faxNumber1": {
            validateIfOneThenBoth: "Please enter both a recipient name and fax number",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "certificateOfInsuranceRequest.faxNumber2": {
            validateIfOneThenBoth: "Please enter both a recipient name and fax number",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "certificateOfInsuranceRequest.faxNumber3": {
            validateIfOneThenBoth: "Please enter both a recipient name and fax number",
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"
        },
        "certificateOfInsuranceRequest.requesterName": {
            required: "Please enter your name"
        }
    },
    invalidHandler: function() {
        $('#globalError').children().remove();
        $('#globalError').append('<p>' + coiRequestValidator.numberOfInvalids() + " field(s) need to be corrected. Please correct these fields below.</p>");
        $.unblockUI();
    }
});

var payOnlineValidator = $('#payOnlineForm').validate({
    rules: {
        "otherAmount": {
            required: "input[name='selectedPayAmt'][value=otherAmt]:checked",
            unmaskedMin: true
        },
        "selectedPayAmt": {
            required: true,
            amtGreaterThanZero: true
        }
    },
    messages: {
        "otherAmount": {
            required: "You must supply a dollar value if choosing other amount."
        },
        "selectedPayAmt": {
            required: "You must select one of the amounts listed to start an online payment."
        }
    },
    invalidHandler: function() {
        $.unblockUI();
    }
});

var registrationValidator = $('#registrationForm').validate({
    rules: {
        "registration.userName": {
            required : true,
            pattern: userNamePattern
        },
        "registration.password": {
            required: true
        },
        "registration.passwordAgain": {
            equalTo: 'input[name="registration.password"]'
        },
        "registration.email": {
            email: true
        },
        "registration.phoneNumber": {
            pattern: phonePattern   
        },
        "registration.phPolicyNumber": {
            pattern: /^\d{8}$/
        }


    },
    messages: {
        "registration.userName": {
            pattern: "At least 4 chars, and only certain special characters are allowed in the username. Those special characters include: commas (,), periods (.), hyphens ( - ), ampersands (&), and single quotation mark(\')."
        },
        "registration.password": {
            required: "Please enter a password."
        },
        "registration.passwordAgain": {
            equalTo: "Your passwords do not match. Please enter your password twice."
        },
        "registration.email": {
            email: "Please enter a valid email address."
        },
        "registration.phoneNumber": {
            pattern: "Please enter a valid phone number in the format xxx-xxx-xxxx"  
        },
        "registration.phPolicyNumber": {
            pattern: "Please enter only eight digits in the format xxxxxxxx"
        }

    },
    invalidHandler: function() {
        $.unblockUI();
    }

});


function submitProcessing() {
    $.blockUI({message: '<h1 style="text-align: center !Important;">Processing...</h1>'});
    return false;
}

function updateTerms(control, url) {

    submitProcessing();

    var xhr = $.get(url,
            {'coiPolicyNumber': $(control).val(),
                '_eventName': 'updateTerms'
            }, function(data) {
        if (xhr.getResponseHeader('Stripes-Success') === "OK") {
            $('#termsDiv').html(data);
        } else {
            alert("Session has expired!");
            window.location.reload(true);
        }
    });

    $.unblockUI();
    return false;
}

function updatePolicyPeriods(control, url) {
    if ($(control).val() === "") {
        return false;
    }

    $.blockUI({message: '<h1 style="text-align: center !Important;">Processing...</h1>'});
    $('.messages > li').each(function() {
        $(this).remove();
    });
    var xhr = $.get(url,
            {'prPolicyNumber': $(control).val(),
                '_eventName': 'updatePolicyPeriods'
            }, function(data) {
        $.unblockUI();
   
        var pers = sortPayrollPeriods(data);

        if (xhr.getResponseHeader('Stripes-Success') === "OK") {
            $('#policyPeriodSelect').html(pers);
            $('#payroll_report_form').empty();
            $('#payroll_report_submit').hide();
            $('#print').attr('disabled', 'disabled');

        var length = $('#prPublicID').children('option').length;
        if(length < 2)
            alert("There are no open, incomplete payroll periods");
        } else {
            alert("Session has expired!");
            window.location.reload(true);
        }
    });
       
    return false;
}

function sortPayrollPeriods(data) {
    var dat = $(data);
    var options = $('option', dat);
    var selectOpt = options.first();
    var dates = options.slice(1);
    var datArray = $.map(dates, function(date) {
        return date;
    });
    datArray.sort(function(a, b) {
        var dt1 = new Date($(a).text().match(/\d{2}-\d{2}-\d{4}/));
        var dt2 = new Date($(b).text().match(/\d{2}-\d{2}-\d{4}/));
        if (dt1 > dt2) {
            return 1;
        } else if (dt2 > dt1) {
            return -1;
        } else {
            return 0;
        }
    });
    selectOpt.appendTo(dat);
    $.each(datArray, function(i, date) {
        dat.append(date);
    });
    return dat.get(0);
}

function updatePolicyForm(control, url) {
    $.blockUI({message: '<h1 style="text-align: center !Important;">Processing...</h1>'});

    var xhr = $.get(url,
            {'prPublicID': $(control).val(),
                '_eventName': 'updatePolicyForm'
            }, function(data) {
        $.unblockUI();
    
        if (xhr.getResponseHeader('Stripes-Success') === "OK") {
            $('#payroll_report_form').html(data);
            $('#payroll_report_submit').show();
            $(".payAmt[name^='payrollReportTable']").each(function() {
                // this is necessary because something about this form
                // renders javascript unable to distinguish between zero and blank.
                // Tested in a debugger, both appear to be blank.
                var name = $(this).attr('name');
                var idx = name.match("payrollReportTable\\[([0-9]+)\\]");
                var isZero = $('input[name="payrollReportTable[' + idx[1] + '].auditAmountIsZero"').val();
                if ($(this).val() === "" && isZero === "true") {
                    $(this).val("0");
                }
            });
            $('#print').attr('disabled', 'disabled');
        } else {
            alert("Session has expired!");
            window.location.reload(true);
        }
        //$.unblockUI();
    });
    return false;
}

function completePayroll(control) {
    $.blockUI({message: '<h1 style="text-align: center !Important;">Processing...</h1>'});
    if(!confirm('Changing the Previously entered value will revise the Payroll report. Do you want to proceed?'))
        window.location.reload(true);
    var form = control.form;
    var params = $(form).serializeArray();
    params.push({name: '_eventName', value: control.name});
    var xhr = $.post(form.action, params, function(data) {
        $.unblockUI();
        if (xhr.getResponseHeader('Stripes-Success') === "OK") {
            if (/^<div class="error"/.test(data)) {
                $('.portal-error-internal').html(data);
                //$('#payroll_report_submit').show();
            } else {
                $('#payroll_report_form').html(data);
                $(".payAmt[name^='payrollReportTable']").each(function() {
                    if ($(this).val() === "") {
                        $(this).val("0");
                    }
                });
                $('.portal-error-internal').html(
                        "<div class=\"error\" style=\"color:#b72222; font-weight: bold\"><ul>" +
                        "<li style=\"color: #b72222;\">" +
                        "Your payroll report has been successfully submitted." +
                        "</li></ul>");
                $('.payAmt').prop('disabled', true);
                $('#complete').prop('disabled', true);
                $('#cancel').val("OK");
                $('#print').removeAttr('disabled');
                $('#payroll_report_submit').show();
                refreshPolicyPeriods();
            }
        } else {
            alert("Session has expired!");
            window.location.reload(true);
        }
    });

    return false;
}

function refreshPolicyPeriods() {
    var selText = $('#prPublicID').find(':selected').text();
    $('#prPublicID').prop('disabled', true);
    $('.messages > li').each(function() {
        $(this).remove();
    });
    var xhr = $.get('/PayrollReport.action',
            {'prPolicyNumber': $('#prPolicyNumber').val(),
                '_eventName': 'updatePolicyPeriods'
            }, function(data) {
        $('#prPublicID').prop('disabled', false);
        var pers = sortPayrollPeriods(data);
        if (xhr.getResponseHeader('Stripes-Success') === "OK") {
            $('#policyPeriodSelect').html(pers);
            $("#prPublicID option:contains(" + selText + ")").attr('selected', 'selected');
        } else {
            alert("Session has expired!");
            window.location.reload(true);
        }
    });

    return false;
}

function placeholderCheck() {

    if (!Modernizr.input.placeholder) {

        $('[placeholder]').focus(function() {
            var input = $(this);
            if (input.val() === input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function() {
            var input = $(this);
            if (input.val() === '' || input.val() === input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur();
        $('[placeholder]').parents('form').submit(function() {
            $(this).find('[placeholder]').each(function() {
                var input = $(this);
                if (input.val() === input.attr('placeholder')) {
                    input.val('');
                }
            });
        });

    }
}

/* 
 decimal_sep: character used as decimal separtor, it defaults to '.' when omitted
 thousands_sep: char used as thousands separator, it defaults to ',' when omitted
 */
Number.prototype.toMoney = function(decimals, decimal_sep, thousands_sep)
{
    var n = this,
            c = isNaN(decimals) ? 2 : Math.abs(decimals), //if decimal is zero we must take it, it means user does not want to show any decimal
            d = decimal_sep || '.', //if no decimal separator is passed we use the dot as default decimal separator (we MUST use a decimal separator)

            /*
             according to [http://stackoverflow.com/questions/411352/how-best-to-determine-if-an-argument-is-not-sent-to-the-javascript-function]
             the fastest way to check for not defined parameter is to use typeof value === 'undefined' 
             rather than doing value === undefined.
             */
            t = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep, //if you don't want to use a thousands separator you can pass empty string as thousands_sep value

            sign = (n < 0) ? '-' : '',
            //extracting the absolute value of the integer part of the number and converting to string
            i = parseInt(n = Math.abs(n).toFixed(c)) + '',
            j = ((j = i.length) > 3) ? j % 3 : 0;
    return sign + (j ? i.substr(0, j) + t : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : '');
};

fnoiPolicyNumberValidator = $("#fnoiPolicyNumber").validate({
    rules: {
        "policyNumber": {
            required: true,
            digits: true,
            minlength: 8,
            maxlength: 8
        },
        "dateOfAccident": {
            required: true,
            fnoiDate: true
        }
    },
    messages: {
        "policyNumber": {
            required: "Policy Number is required.",
            digits: "Please enter only numbers.",
            minlength: "You have entered fewer than 8 digits. Please enter the first 8 digits of your CompSource policy number",
            maxlength: "You have entered more than 8 digits. Please enter no more than the first 8 digits of your CompSource policy number"
        }
    },
    invalidHandler: function() {
        $.unblockUI();
    }
});

 $.validator.addMethod(
      "priorPoliciesDate",
      function (value) {
          if ($("#hadPreviousCoverageYes").attr('checked', 'checked')) {
            return isDate(value);
          } else {
              return true;
          }
      },
      "Please enter a date in the format MM-DD-YYYY"
    );
// overriding jQuery date validation to give us more control over the format.
 $.validator.addMethod(
      "fnoiDate",
      function (value, element) {

        var j = isDate(value);
        if(j){
          if(element){
              $(this).nextAll().eq(1).text('');
        }
        }
        return j;
      },
      "Please enter a date in the format MM-DD-YYYY"
    );
    
 $.validator.addMethod(
      "returnToWorkDateIfVisible",
      function (value) {
          if (returnToWorkQuestion.value === "true") {
            return isDate(value);
          } else {
              return true;
          }
      },
      "Please enter a date in the format MM-DD-YYYY"
    );
    
  $.validator.addMethod(
      "deathDateIfVisible",
      function (value) {
          if (deathDateQuestion.value === "Y") {
            return isDate(value);
          } else {
              return true;
          }
      },
      "Please enter a date in the format MM-DD-YYYY"
    );
    
    function isFutureDate(idate){
    var today = new Date().getTime(),
        idate = idate.split("/");

        idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
        return (today - idate) < 0 ? true : false;
    }
    
   $.validator.addMethod(
        "dateXIsNotInTheFuture",
        function(value) {
            var date = new Date();
            dateY = getDateComponents(value);
            
            var dy = parseInt(dateY.year);
            var dm = parseInt(dateY.month);
            var dd = parseInt(dateY.day);
            var dateD = new Date(dy,dm-1,dd);

            return date >= dateD;
            
        },
        "Date cannot be in the future"
    );

  $.validator.addMethod(
        "dateXIsOnOrAfterDateY",
        function(value, element, params) {
            var dateXString = $('input[name="' + params[1] + '"]').val();
                dateX = getDateComponents(dateXString);
                dateY = getDateComponents(value);
                if(!dateX || !dateY)
                    return true;
                var dx = new Date(dateX.year,dateX.month-1,dateX.day);
                var dy = new Date(dateY.year,dateY.month-1,dateY.day);
                if(dx >= dy)
                    return true;
                else
                    return false;
        },
        "{0} needs to be on or after {1}  "
    );


function isEmpty(str) {
    return (!str || 0 === str.length);
}

function isBlankOrEmpty(str) {
    return (!str || 0 === str.length || str.replace(/\s/g, '').length == 0 );
}

    
// ******************************************************************
// This function accepts a string variable and verifies if it is a
// proper date or not. It validates format matching either
// mm-dd-yyyy or mm/dd/yyyy. Then it checks to make sure the month
// has the proper number of days, based on which month it is.

// The function returns true if a valid date, false if not.
// ******************************************************************



function isDate(dateString) {
   
   if(isEmpty(dateString))
       return true;
    var isValid = true;
    var dateComps = getDateComponents(dateString);
 
    if (dateComps === null) {
        isValid = false;
    } else {
        if (dateComps.month < 1 || dateComps.month > 12) { // check month range
            isValid = false;
        }

        if (dateComps.day < 1 || dateComps.day > 31) {
            isValid = false;
        }

        if ((dateComps.month == 4 || dateComps.month == 6 || dateComps.month == 9 || dateComps.month == 11) && dateComps.day > 30) {
            isValid = false;
        }

        if (dateComps.month == 2) { // check for February 29th
            if(dateComps.day > 29){
                isValid = false;
                return isValid;
            }
            var isleap = (dateComps.year % 4 == 0 && (dateComps.year % 100 != 0 || dateComps.year % 400 == 0));
            if (dateComps.day > 28 && !isleap) {
                isValid = false;
            }
        }
    }
 
    return isValid;
}

function getDateComponents(dateString) {
    var matchArray = dateString.match(datePattern); // is the format ok?
    if (matchArray !== null) {
        return {
            month: matchArray[1],
            day: matchArray[2],
            year: matchArray[3]
        };
    } else {
        return null;
    }     
}


var fnoiValidator = $('#fnoiForm').validate({
    rules: {
        "timeOfAccident": {
            pattern: timePattern
        },
        "workDayTime": {
            pattern: timePattern
        },
        "timeReported": {
            pattern: timePattern
        },
        "claimantEP.firstName": {
            required: true
        },
        "claimantEP.lastName": {
            required: true
        },
        "claimantAddress.addressLine1": {
            required: true
        },
        "claimantAddress.city": {
            required: true
        },
        "claimantAddress.state": {
            required: true
        },
        "claimantAddress.postalCode": {
            required: true,
            zipcodeUS: true
        },
        "claimant.homePhone": {
            pattern: phonePattern
        },
        "claimant.taxID": {
            required: true,
            pattern: ssnPattern
        },
        "claimant.emailAddress1": {
            email: true  
        },
        "dateOfBirth": {
            required: true,
            fnoiDate: true
        }, 
        "dateOfHire": {
            required: true,
            fnoiDate: true
        },
        "employmentData.hireDate": {
            required: true,
            fnoiDate: true
        },
        "employmentData.wageAmount": {
            pattern: /^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$/
        },
        "claimantEP.occupation": {
            required: true
        },
        "lastWorkedDate": {
            fnoiDate: true
        },
        "dateOfAccident": {
            required: true,
            fnoiDate: true
        },
        "dateReported": {
            required: true,
            fnoiDate: true
        },
        "policyFromDate": {
            fnoiDate: true,
        },        
        "policyToDate": {
            fnoiDate: true,
        },
        "injury.generalInjuryType": {
            required: true
        },
        "bodyPartList": {
            required: true,
            minlength: 1
        },
        "claim.description": {
            required: true
        },
        "claim.empQusValidity": {
            required: true
        },
        "claim.incidentOnly":{
            required: true
        },
        "policyHolder.name": {
            required: true
        },
        "policyLocationAddress.addressLine1": {
            required: true
        },
        "policyLocationAddress.city": {
            required: true
        },
        "policyHolder.workPhone": {
            required: true
        },
        "reporterEP.firstName": {
            required: true
        },
        "reporterEP.lastName": {
            required: true
        },
        "reporter.workPhone": {
            required: true,
            pattern: phonePattern
        },
        "claim.reportedByType": {
            required: true
        },
        "deathDate": {
            deathDateIfVisible: true
        },
        "returnToWorkDate": {
            returnToWorkDateIfVisible: true
        },
        "physicianEP.firstName": {
             validateIfOneThenBoth: ['physicianEP.firstName', 'physicianEP.lastName']
         },
        "physicianEP.lastName": {
             validateIfOneThenBoth: ['physicianEP.firstName', 'physicianEP.lastName']
         },
        "physicianAddress.addressLine1": {
            ifEitherOneOrTwoThenThree: ['physicianEP.firstName', 'physicianEP.lastName', 'physicianAddress.addressLine1']
        },
        "physicianAddress.city": {
            ifEitherOneOrTwoThenThree: ['physicianEP.firstName', 'physicianEP.lastName', 'physicianAddress.city']
        },
        "physicianAddress.state": {
            ifEitherOneOrTwoThenThree: ['physicianEP.firstName', 'physicianEP.lastName', 'physicianAddress.state']
        },
        "physicianAddress.postalCode": {
            ifEitherOneOrTwoThenThree: ['physicianEP.firstName', 'physicianEP.lastName', 'physicianAddress.postalCode'],
            zipcodeUS: true
        }

    },
    messages: {
        "claimantEP.firstName": {
            required: "Please enter a first name"
        },
        "claimantEP.lastName": {
            required: "Please enter a last name"
        },
        "claimantAddress.addressLine1": {
            required: "Please enter an address"
        },
        "claimantAddress.city": {
            required: "Please enter the city"
        },
        "claimantAddress.postalCode": {
            required: "Please enter a zip code",
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "claimant.homePhone": {
            pattern: "Please enter a valid phone number (format xxx-xxx-xxxx)"
        },
        "claimant.taxID": {
            required: "Please enter a valid Social Security Number (format xxx-xx-xxxx)",
            pattern: "Please enter a valid Social Security Number (format xxx-xx-xxxx)"
        },
        "dateOfBirth": {
            required: "Please enter the date of birth",
            fnoiDate: "Please enter a valid date"
        },
        "employmentData.hireDate":{
            required: "Please enter the date of hire",
            fnoiDate: "Please enter a valid date"
        },
        "dateOfHire": {
            required: "Please enter the date of hire",
            fnoiDate: "Please enter a valid date"
        },
        "employmentData.wageAmount": {
            pattern: "Please enter a valid number for Average Wage."
        },
        "claimantEP.occupation": {
            required: "Please enter an occupation"
        },
        "dateOfAccident": {
            required: "Please enter a date"
        },
        "dateReported": {
            required: "Please enter a date",
            fnoiDate: "Please enter a valid date"
        },
        "injury.generalInjuryType": {
            required: "Please select a nature of injury or illness"
        },
        "bodyPartList": {
            required: "Please select a body part",
            minlength: "Please select at least one body part"
        },
        "claim.description": {
            required: "Please enter a description of the accident"
        },
        "claim.empQusValidity": {
            required: "Please select an answer"
        },
        "claim.incidentOnly":{
            required: "Please select an answer"
        },
        "policyHolder.name": {
            required: "Please enter the name of the employer"
        },
        "policyLocationAddress.addressLine1": {
            required: "Please enter an address"
        },
        "policyLocationAddress.city": {
            required: "Please enter a city"
        },
        "policyHolder.workPhone": {
            required: "Please enter the employer phone number",
            pattern: "Please enter a valid phone number (format xxx-xxx-xxxx)"
        },
        "reporterEP.firstName": {
            required: "Please enter your first name"
        },
        "reporterEP.lastName": {
            required: "Please enter your last name"
        },
        "reporter.workPhone": {
            required: "Please enter your phone number",
            pattern: "Please enter a valid phone number (format xxx-xxx-xxxx)"
        },
        "claim.reportedByType": {
            required: "Please select an answer"
        },
        "deathDate": {
            deathDateIfVisible: "Please enter a date in the format MM-DD-YYYY"
        },
        "returnToWorkDate": {
            returnToWorkDateIfVisible: "Please enter a date in the format MM-DD-YYYY"
        },
        "lossLocation.addressLine2": {
            validateIfOneThenBoth: "Please enter a complete address line 1."
        },
        "lossLocation.city": {
            validateIfOneThenBoth: "Please enter a city."
        },
        "lossLocation.state": {
            validateIfOneThenBoth: "Please enter a state."
        },
        "lossLocation.postalCode": {
            validateIfOneThenBoth: "Please enter a complete address.",
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "physicianEP.firstName": {
            validateIfOneThenBoth: "Please enter both first and last name"
        },
        "physicianEP.lastName": {
            validateIfOneThenBoth: "Please enter both first and last name"
        },
        "physicianAddress.addressLine1": {
            ifEitherOneOrTwoThenThree: "Please enter an address."
        },
        "physicianAddress.city": {
            ifEitherOneOrTwoThenThree: "Please enter a city."
        },
        "physicianAddress.state": {
            ifEitherOneOrTwoThenThree: "Please enter a state."
        },
        "physicianAddress.postalCode": {
            ifEitherOneOrTwoThenThree: "Please enter a zip code.",
            zipcodeUS: "Please enter a valid US zip code. Format: xxxxx or xxxxx-xxxx"
        },
        "supervisorEP.firstName":{
               validateIfOneThenBoth: "Please enter both first and last name for Supervisor"},
        "supervisorEP.lastName":{
               validateIfOneThenBoth: "Please enter both first and last name for Supervisor"},   
        "witnessList[0].firstName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
           },
        "witnessList[0].lastName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
           },
        "witnessList[1].firstName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
           },
	"witnessList[1].lastName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
           },
        "witnessList[2].firstName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
           },
        "witnessList[2].lastName":{
               validateIfOneThenBoth: "Please enter both first and last name for a witness"
        }

    },
    invalidHandler: function() {
        $('#globalError').children().remove();
        $('#globalError').append('<p>' + fnoiValidator.numberOfInvalids() + " field(s) need to be corrected. Please correct these fields below.</p>");
        $.unblockUI();
    }
});

function numberWithCommas(x) {
    var parts = x.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}

$.fn.digits = function() {
    return this.each(function() {
        $(this).text($(this).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
    });
};

function calculateSum() {
    var sum = 0;
    //iterate through each textboxes and add the values
    $(".payAmt").each(function() {
        if ($(this).val() !== "") {
            var num = $(this).parseNumber({format: "#,###", locale: "us"});
            num = Math.abs(num);
            //add only if the value is number
            if (!isNaN(num) && num.length !== 0) {
                sum += parseFloat(num);
                $(this).css("background-color", "#FFFFFF");
            } else if (num.length !== 0) {
                $(this).css("background-color", "red");
            }
        }
    });
    sum = Math.round(sum);
    $("#sum").html(numberWithCommas(sum));
    $("#printSum").html('$' + numberWithCommas(sum));
}


function PrintPayroll()
{
    var divToPrint = document.getElementById('payroll_report_print');
    mywindow = window.open();
    mywindow.document.write('<!DOCTYPE html>');
    mywindow.document.write('<html><head>');
    mywindow.document.write('<META HTTP-EQUIV="Pragma" CONTENT="no-cache">')
    mywindow.document.write('</head><body >');
    mywindow.document.write(divToPrint.innerHTML);
    mywindow.document.write('</body></html>');
    mywindow.document.close();
    mywindow.focus();

    require('js/jquery.js', function() {
        require('js/jquery.ui.js', function() {
            require('js/jshashtable-3.0.js', function() {
                require('js/jquery.numberformatter-1.2.4.js', function() {
                    require('js/jquery.column.js', function() {
                        require('js/jquery.text-align.js', function() {
                            addLoadEvent(function() {
                                $('.pay-col').each(function() {
                                    var txt = $(this).text();
                                    $(this).text(function() {
                                        var parts = txt.toString().split(".");
                                        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                        if (parts[0][0] !== '$'){
                                        return '$' + parts.join(".");
                                        } else {
                                            parts[0] = parts[0].replace(/\$\s+\$/g, "$");
                                            return parts.join(".");
                                        }
                                        
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    });

    setTimeout(function(){
        mywindow.print();
        mywindow.close();
    }, 250);

    return false;
}

// window.onload W3C cross-browser with a fallback
function addLoadEvent(func) {
  if (window.addEventListener)
    window.addEventListener("load", func, false);
  else if (window.attachEvent)
    window.attachEvent("onload", func);
  else { // fallback
    var old = window.onload;
    window.onload = function() {
      if (old) old();
      func();
    };
  }
}


/**
 *Load an externl JS file and append it to the head
 */
function require(file, callback) {
    var head = mywindow.document.getElementsByTagName("head")[0];
    var script = mywindow.document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    script.async = false;
    //real browsers
    script.onload = callback;
    //Internet explorer
    script.onreadystatechange = function() {
        if (_this.readyState === 'complete' || _this.readyState === 'loaded') {
            _this.onreadystatechange = null;
            callback();
        }
    };
    head.appendChild(script);
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function isInt(n) {
    return n % 1 === 0;
}


