package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("UM_USER_ID")
    @Expose
    private String uMUSERID;
    @SerializedName("UM_USER_DESC")
    @Expose
    private String uMUSERDESC;
    @SerializedName("UM_USER_PWD")
    @Expose
    private String uMUSERPWD;
    @SerializedName("UM_MASCOM_CODE")
    @Expose
    private String uMMASCOMCODE;
    @SerializedName("UM_DIV_CODE")
    @Expose
    private String uMDIVCODE;
    @SerializedName("UM_GRUP_NAME")
    @Expose
    private String uMGRUPNAME;
    @SerializedName("UM_GRUP_CODE")
    @Expose
    private Integer uMGRUPCODE;
    @SerializedName("UM_SUBGRUP_NAME")
    @Expose
    private String uMSUBGRUPNAME;
    @SerializedName("UM_EMP_DESIG")
    @Expose
    private String uMEMPDESIG;
    @SerializedName("UM_DESIG_CODE")
    @Expose
    private String uMDESIGCODE;
    @SerializedName("UM_DEPT_NAME")
    @Expose
    private String uMDEPTNAME;
    @SerializedName("UM_DEPT_CODE")
    @Expose
    private String uMDEPTCODE;
    @SerializedName("UM_LOGGED_IN")
    @Expose
    private String uMLOGGEDIN;
    @SerializedName("UM_UPLOAD_FLAG")
    @Expose
    private String uMUPLOADFLAG;
    @SerializedName("UM_USER_RIGHT")
    @Expose
    private String uMUSERRIGHT;
    @SerializedName("UM_USER_TYPE")
    @Expose
    private String uMUSERTYPE;
    @SerializedName("UM_EMAIL_ID")
    @Expose
    private String uMEMAILID;
    @SerializedName("UM_USER_PRFL")
    @Expose
    private String uMUSERPRFL;
    @SerializedName("UM_USER_RIGHTS")
    @Expose
    private Object uMUSERRIGHTS;
    @SerializedName("UM_OBSO_FLAG")
    @Expose
    private String uMOBSOFLAG;
    @SerializedName("UM_APPROVED_FLAG")
    @Expose
    private String uMAPPROVEDFLAG;
    @SerializedName("UM_ADDED_BY")
    @Expose
    private String uMADDEDBY;
    @SerializedName("UM_ADDED_DT")
    @Expose
    private Object uMADDEDDT;
    @SerializedName("UM_APROV_BY")
    @Expose
    private String uMAPROVBY;
    @SerializedName("UM_APROV_DT")
    @Expose
    private Object uMAPROVDT;
    @SerializedName("UM_PW_NEWSET")
    @Expose
    private String uMPWNEWSET;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Anniversary")
    @Expose
    private String anniversary;
    @SerializedName("DOJ")
    @Expose
    private String dOJ;
    @SerializedName("LastAppraisalDate")
    @Expose
    private String lastAppraisalDate;
    @SerializedName("UM_EMP_LEVEL")
    @Expose
    private Integer uMEMPLEVEL;
    @SerializedName("UM_REPORTING_TO")
    @Expose
    private String uMREPORTINGTO;
    @SerializedName("UM_REPORTING_TO_NAME")
    @Expose
    private String uMREPORTINGTONAME;
    @SerializedName("UM_REPORTING_TO_EMAIL")
    @Expose
    private String uMREPORTINGTOEMAIL;
    @SerializedName("ProfileIMage")
    @Expose
    private String profileIMage;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("LastLoginDate")
    @Expose
    private String lastLoginDate;
    @SerializedName("ForgetStatus")
    @Expose
    private Boolean forgetStatus;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("AppLogin")
    @Expose
    private Boolean appLogin;
    @SerializedName("WebLogin")
    @Expose
    private Boolean webLogin;
    @SerializedName("AppLastLogin")
    @Expose
    private String appLastLogin;
    @SerializedName("AppLogOut")
    @Expose
    private Object appLogOut;
    @SerializedName("Qualification")
    @Expose
    private Object qualification;
    @SerializedName("WorkExp")
    @Expose
    private Object workExp;
    @SerializedName("Resume")
    @Expose
    private Object resume;
    @SerializedName("PresentPRofile")
    @Expose
    private Object presentPRofile;
    @SerializedName("ExpYear")
    @Expose
    private Integer expYear;
    @SerializedName("ExpMonth")
    @Expose
    private Integer expMonth;
    @SerializedName("Location")
    @Expose
    private Object location;
    @SerializedName("State")
    @Expose
    private Object state;
    @SerializedName("City")
    @Expose
    private Object city;
    @SerializedName("Country")
    @Expose
    private Object country;
    @SerializedName("PinCode")
    @Expose
    private Object pinCode;
    @SerializedName("ExpBeforeMinda")
    @Expose
    private Object expBeforeMinda;
    @SerializedName("ExpMinda")
    @Expose
    private Object expMinda;
    @SerializedName("TotalExp")
    @Expose
    private Object totalExp;
    @SerializedName("Qualification1")
    @Expose
    private Object qualification1;
    @SerializedName("Institute1")
    @Expose
    private Object institute1;
    @SerializedName("PassedOutDate1")
    @Expose
    private String passedOutDate1;
    @SerializedName("Grade1")
    @Expose
    private String grade1;
    @SerializedName("Qualification2")
    @Expose
    private Object qualification2;
    @SerializedName("Institute2")
    @Expose
    private Object institute2;
    @SerializedName("PassedOutDate2")
    @Expose
    private String passedOutDate2;
    @SerializedName("Grade2")
    @Expose
    private String grade2;
    @SerializedName("Qualification3")
    @Expose
    private Object qualification3;
    @SerializedName("Institute3")
    @Expose
    private Object institute3;
    @SerializedName("PassedOutDate3")
    @Expose
    private String passedOutDate3;
    @SerializedName("Grade3")
    @Expose
    private Object grade3;
    @SerializedName("AuthFor")
    @Expose
    private String authFor;
    @SerializedName("CreatedBy")
    @Expose
    private Object createdBy;
    @SerializedName("InActiveReason")
    @Expose
    private String inActiveReason;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("InactiveDate")
    @Expose
    private String inactiveDate;
    @SerializedName("FunctionID1")
    @Expose
    private Integer functionID;
    @SerializedName("Deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("APREMPCODE")
    @Expose
    private String aPREMPCODE;
    @SerializedName("CoAprEmpCode")
    @Expose
    private String coAprEmpCode;
    @SerializedName("AprType")
    @Expose
    private Boolean aprType;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("MiddleName")
    @Expose
    private Object middleName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("WrkShiftDate")
    @Expose
    private Object wrkShiftDate;
    @SerializedName("WrkShiftID")
    @Expose
    private Integer wrkShiftID;
    @SerializedName("DeviceId")
    @Expose
    private Object deviceId;
    @SerializedName("LastPromotion")
    @Expose
    private Object lastPromotion;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("AuthFor2")
    @Expose
    private Object authFor2;
    @SerializedName("SaviorNO")
    @Expose
    private Object saviorNO;
    @SerializedName("GFHEmpCode")
    @Expose
    private String gFHEmpCode;
    @SerializedName("InE")
    @Expose
    private Object inE;
    @SerializedName("Depu_UnitCode")
    @Expose
    private Object depuUnitCode;
    @SerializedName("Depu_UnitName")
    @Expose
    private Object depuUnitName;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("CVPAccess")
    boolean CVPAccess;
    @SerializedName("BUSINESS")
    String BUSINESS;
    @SerializedName("BusinessId")
    int BusinessId;
    @SerializedName("DomainID")
    int DomainID;
    @SerializedName("DomainName")
    String DomainName;
    @SerializedName("FunctionID")
    int FunctionID;
    @SerializedName("FunctionName")
    String FunctionName;


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUMUSERID() {
        return uMUSERID;
    }

    public void setUMUSERID(String uMUSERID) {
        this.uMUSERID = uMUSERID;
    }

    public String getUMUSERDESC() {
        return uMUSERDESC;
    }

    public void setUMUSERDESC(String uMUSERDESC) {
        this.uMUSERDESC = uMUSERDESC;
    }

    public String getUMUSERPWD() {
        return uMUSERPWD;
    }

    public void setUMUSERPWD(String uMUSERPWD) {
        this.uMUSERPWD = uMUSERPWD;
    }

    public String getUMMASCOMCODE() {
        return uMMASCOMCODE;
    }

    public void setUMMASCOMCODE(String uMMASCOMCODE) {
        this.uMMASCOMCODE = uMMASCOMCODE;
    }

    public String getUMDIVCODE() {
        return uMDIVCODE;
    }

    public void setUMDIVCODE(String uMDIVCODE) {
        this.uMDIVCODE = uMDIVCODE;
    }

    public String getUMGRUPNAME() {
        return uMGRUPNAME;
    }

    public void setUMGRUPNAME(String uMGRUPNAME) {
        this.uMGRUPNAME = uMGRUPNAME;
    }

    public Integer getUMGRUPCODE() {
        return uMGRUPCODE;
    }

    public void setUMGRUPCODE(Integer uMGRUPCODE) {
        this.uMGRUPCODE = uMGRUPCODE;
    }

    public String getUMSUBGRUPNAME() {
        return uMSUBGRUPNAME;
    }

    public void setUMSUBGRUPNAME(String uMSUBGRUPNAME) {
        this.uMSUBGRUPNAME = uMSUBGRUPNAME;
    }

    public String getUMEMPDESIG() {
        return uMEMPDESIG;
    }

    public void setUMEMPDESIG(String uMEMPDESIG) {
        this.uMEMPDESIG = uMEMPDESIG;
    }

    public String getUMDESIGCODE() {
        return uMDESIGCODE;
    }

    public void setUMDESIGCODE(String uMDESIGCODE) {
        this.uMDESIGCODE = uMDESIGCODE;
    }

    public String getUMDEPTNAME() {
        return uMDEPTNAME;
    }

    public void setUMDEPTNAME(String uMDEPTNAME) {
        this.uMDEPTNAME = uMDEPTNAME;
    }

    public String getUMDEPTCODE() {
        return uMDEPTCODE;
    }

    public void setUMDEPTCODE(String uMDEPTCODE) {
        this.uMDEPTCODE = uMDEPTCODE;
    }

    public String getUMLOGGEDIN() {
        return uMLOGGEDIN;
    }

    public void setUMLOGGEDIN(String uMLOGGEDIN) {
        this.uMLOGGEDIN = uMLOGGEDIN;
    }

    public String getUMUPLOADFLAG() {
        return uMUPLOADFLAG;
    }

    public void setUMUPLOADFLAG(String uMUPLOADFLAG) {
        this.uMUPLOADFLAG = uMUPLOADFLAG;
    }

    public String getUMUSERRIGHT() {
        return uMUSERRIGHT;
    }

    public void setUMUSERRIGHT(String uMUSERRIGHT) {
        this.uMUSERRIGHT = uMUSERRIGHT;
    }

    public String getUMUSERTYPE() {
        return uMUSERTYPE;
    }

    public void setUMUSERTYPE(String uMUSERTYPE) {
        this.uMUSERTYPE = uMUSERTYPE;
    }

    public String getUMEMAILID() {
        return uMEMAILID;
    }

    public void setUMEMAILID(String uMEMAILID) {
        this.uMEMAILID = uMEMAILID;
    }

    public String getUMUSERPRFL() {
        return uMUSERPRFL;
    }

    public void setUMUSERPRFL(String uMUSERPRFL) {
        this.uMUSERPRFL = uMUSERPRFL;
    }

    public Object getUMUSERRIGHTS() {
        return uMUSERRIGHTS;
    }

    public void setUMUSERRIGHTS(Object uMUSERRIGHTS) {
        this.uMUSERRIGHTS = uMUSERRIGHTS;
    }

    public String getUMOBSOFLAG() {
        return uMOBSOFLAG;
    }

    public void setUMOBSOFLAG(String uMOBSOFLAG) {
        this.uMOBSOFLAG = uMOBSOFLAG;
    }

    public String getUMAPPROVEDFLAG() {
        return uMAPPROVEDFLAG;
    }

    public void setUMAPPROVEDFLAG(String uMAPPROVEDFLAG) {
        this.uMAPPROVEDFLAG = uMAPPROVEDFLAG;
    }

    public String getUMADDEDBY() {
        return uMADDEDBY;
    }

    public void setUMADDEDBY(String uMADDEDBY) {
        this.uMADDEDBY = uMADDEDBY;
    }

    public Object getUMADDEDDT() {
        return uMADDEDDT;
    }

    public void setUMADDEDDT(Object uMADDEDDT) {
        this.uMADDEDDT = uMADDEDDT;
    }

    public String getUMAPROVBY() {
        return uMAPROVBY;
    }

    public void setUMAPROVBY(String uMAPROVBY) {
        this.uMAPROVBY = uMAPROVBY;
    }

    public Object getUMAPROVDT() {
        return uMAPROVDT;
    }

    public void setUMAPROVDT(Object uMAPROVDT) {
        this.uMAPROVDT = uMAPROVDT;
    }

    public String getUMPWNEWSET() {
        return uMPWNEWSET;
    }

    public void setUMPWNEWSET(String uMPWNEWSET) {
        this.uMPWNEWSET = uMPWNEWSET;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(String anniversary) {
        this.anniversary = anniversary;
    }

    public String getDOJ() {
        return dOJ;
    }

    public void setDOJ(String dOJ) {
        this.dOJ = dOJ;
    }

    public String getLastAppraisalDate() {
        return lastAppraisalDate;
    }

    public void setLastAppraisalDate(String lastAppraisalDate) {
        this.lastAppraisalDate = lastAppraisalDate;
    }

    public Integer getUMEMPLEVEL() {
        return uMEMPLEVEL;
    }

    public void setUMEMPLEVEL(Integer uMEMPLEVEL) {
        this.uMEMPLEVEL = uMEMPLEVEL;
    }

    public String getUMREPORTINGTO() {
        return uMREPORTINGTO;
    }

    public void setUMREPORTINGTO(String uMREPORTINGTO) {
        this.uMREPORTINGTO = uMREPORTINGTO;
    }

    public String getUMREPORTINGTONAME() {
        return uMREPORTINGTONAME;
    }

    public void setUMREPORTINGTONAME(String uMREPORTINGTONAME) {
        this.uMREPORTINGTONAME = uMREPORTINGTONAME;
    }

    public String getUMREPORTINGTOEMAIL() {
        return uMREPORTINGTOEMAIL;
    }

    public void setUMREPORTINGTOEMAIL(String uMREPORTINGTOEMAIL) {
        this.uMREPORTINGTOEMAIL = uMREPORTINGTOEMAIL;
    }

    public String getProfileIMage() {
        return profileIMage;
    }

    public void setProfileIMage(String profileIMage) {
        this.profileIMage = profileIMage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Boolean getForgetStatus() {
        return forgetStatus;
    }

    public void setForgetStatus(Boolean forgetStatus) {
        this.forgetStatus = forgetStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getAppLogin() {
        return appLogin;
    }

    public void setAppLogin(Boolean appLogin) {
        this.appLogin = appLogin;
    }

    public Boolean getWebLogin() {
        return webLogin;
    }

    public void setWebLogin(Boolean webLogin) {
        this.webLogin = webLogin;
    }

    public String getAppLastLogin() {
        return appLastLogin;
    }

    public void setAppLastLogin(String appLastLogin) {
        this.appLastLogin = appLastLogin;
    }

    public Object getAppLogOut() {
        return appLogOut;
    }

    public void setAppLogOut(Object appLogOut) {
        this.appLogOut = appLogOut;
    }

    public Object getQualification() {
        return qualification;
    }

    public void setQualification(Object qualification) {
        this.qualification = qualification;
    }

    public Object getWorkExp() {
        return workExp;
    }

    public void setWorkExp(Object workExp) {
        this.workExp = workExp;
    }

    public Object getResume() {
        return resume;
    }

    public void setResume(Object resume) {
        this.resume = resume;
    }

    public Object getPresentPRofile() {
        return presentPRofile;
    }

    public void setPresentPRofile(Object presentPRofile) {
        this.presentPRofile = presentPRofile;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
    }

    public Object getExpBeforeMinda() {
        return expBeforeMinda;
    }

    public void setExpBeforeMinda(Object expBeforeMinda) {
        this.expBeforeMinda = expBeforeMinda;
    }

    public Object getExpMinda() {
        return expMinda;
    }

    public void setExpMinda(Object expMinda) {
        this.expMinda = expMinda;
    }

    public Object getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(Object totalExp) {
        this.totalExp = totalExp;
    }

    public Object getQualification1() {
        return qualification1;
    }

    public void setQualification1(Object qualification1) {
        this.qualification1 = qualification1;
    }

    public Object getInstitute1() {
        return institute1;
    }

    public void setInstitute1(Object institute1) {
        this.institute1 = institute1;
    }

    public String getPassedOutDate1() {
        return passedOutDate1;
    }

    public void setPassedOutDate1(String passedOutDate1) {
        this.passedOutDate1 = passedOutDate1;
    }

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public Object getQualification2() {
        return qualification2;
    }

    public void setQualification2(Object qualification2) {
        this.qualification2 = qualification2;
    }

    public Object getInstitute2() {
        return institute2;
    }

    public void setInstitute2(Object institute2) {
        this.institute2 = institute2;
    }

    public String getPassedOutDate2() {
        return passedOutDate2;
    }

    public void setPassedOutDate2(String passedOutDate2) {
        this.passedOutDate2 = passedOutDate2;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public Object getQualification3() {
        return qualification3;
    }

    public void setQualification3(Object qualification3) {
        this.qualification3 = qualification3;
    }

    public Object getInstitute3() {
        return institute3;
    }

    public void setInstitute3(Object institute3) {
        this.institute3 = institute3;
    }

    public String getPassedOutDate3() {
        return passedOutDate3;
    }

    public void setPassedOutDate3(String passedOutDate3) {
        this.passedOutDate3 = passedOutDate3;
    }

    public Object getGrade3() {
        return grade3;
    }

    public void setGrade3(Object grade3) {
        this.grade3 = grade3;
    }

    public String getAuthFor() {
        return authFor;
    }

    public void setAuthFor(String authFor) {
        this.authFor = authFor;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getInActiveReason() {
        return inActiveReason;
    }

    public void setInActiveReason(String inActiveReason) {
        this.inActiveReason = inActiveReason;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(String inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public Integer getFunctionID() {
        return functionID;
    }

    public void setFunctionID(Integer functionID) {
        this.functionID = functionID;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getAPREMPCODE() {
        return aPREMPCODE;
    }

    public void setAPREMPCODE(String aPREMPCODE) {
        this.aPREMPCODE = aPREMPCODE;
    }

    public String getCoAprEmpCode() {
        return coAprEmpCode;
    }

    public void setCoAprEmpCode(String coAprEmpCode) {
        this.coAprEmpCode = coAprEmpCode;
    }

    public Boolean getAprType() {
        return aprType;
    }

    public void setAprType(Boolean aprType) {
        this.aprType = aprType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getWrkShiftDate() {
        return wrkShiftDate;
    }

    public void setWrkShiftDate(Object wrkShiftDate) {
        this.wrkShiftDate = wrkShiftDate;
    }

    public Integer getWrkShiftID() {
        return wrkShiftID;
    }

    public void setWrkShiftID(Integer wrkShiftID) {
        this.wrkShiftID = wrkShiftID;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public Object getLastPromotion() {
        return lastPromotion;
    }

    public void setLastPromotion(Object lastPromotion) {
        this.lastPromotion = lastPromotion;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Object getAuthFor2() {
        return authFor2;
    }

    public void setAuthFor2(Object authFor2) {
        this.authFor2 = authFor2;
    }

    public Object getSaviorNO() {
        return saviorNO;
    }

    public void setSaviorNO(Object saviorNO) {
        this.saviorNO = saviorNO;
    }

    public String getGFHEmpCode() {
        return gFHEmpCode;
    }

    public void setGFHEmpCode(String gFHEmpCode) {
        this.gFHEmpCode = gFHEmpCode;
    }

    public Object getInE() {
        return inE;
    }

    public void setInE(Object inE) {
        this.inE = inE;
    }

    public Object getDepuUnitCode() {
        return depuUnitCode;
    }

    public void setDepuUnitCode(Object depuUnitCode) {
        this.depuUnitCode = depuUnitCode;
    }

    public Object getDepuUnitName() {
        return depuUnitName;
    }

    public void setDepuUnitName(Object depuUnitName) {
        this.depuUnitName = depuUnitName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isCVPAccess() {
        return CVPAccess;
    }

    public void setCVPAccess(boolean CVPAccess) {
        this.CVPAccess = CVPAccess;
    }

    public String getBUSINESS() {
        return BUSINESS;
    }

    public void setBUSINESS(String BUSINESS) {
        this.BUSINESS = BUSINESS;
    }
}
