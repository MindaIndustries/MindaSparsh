package com.minda.sparsh;


import com.minda.sparsh.model.ARPDModel;
import com.minda.sparsh.model.AbnormalityView_Model;
import com.minda.sparsh.model.AboutUsDetails;
import com.minda.sparsh.model.AccessRequestApproverDetailsModel;
import com.minda.sparsh.model.AccessRequestDetailsModel;
import com.minda.sparsh.model.AccessRequestPlantDetailModel;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.model.AddTargetDate_Model;
import com.minda.sparsh.model.ApproveList;
import com.minda.sparsh.model.AutoFillMobileModel;
import com.minda.sparsh.model.BannarModel;
import com.minda.sparsh.model.Business_Model;
import com.minda.sparsh.model.CategoryAbnormality;
import com.minda.sparsh.model.DDModel;
import com.minda.sparsh.model.DWMDetailResponse;
import com.minda.sparsh.model.DWMOprationDetailResponse;
import com.minda.sparsh.model.Department_Model;
import com.minda.sparsh.model.Domain_Model;
import com.minda.sparsh.model.DwmResponse;
import com.minda.sparsh.model.GetAbnormalityImage_Model;
import com.minda.sparsh.model.Group_Model;
import com.minda.sparsh.model.IAMCreateRequestModel;
import com.minda.sparsh.model.IAMGetAccessSubTypeModel;
import com.minda.sparsh.model.IAMGetAccessTypeSpinnerModel;
import com.minda.sparsh.model.IAMGetAuthorizationProfileModel;
import com.minda.sparsh.model.IAMGetBusinessModel;
import com.minda.sparsh.model.IAMGetCategorySpinnerModel;
import com.minda.sparsh.model.IAMGetDomainModel;
import com.minda.sparsh.model.IAMGetListOfNames;
import com.minda.sparsh.model.IAMGetPlantModel;
import com.minda.sparsh.model.IAMGetRequestTypeSpinnerModel;
import com.minda.sparsh.model.IAMGetSubCategoryModel;
import com.minda.sparsh.model.LoginResponse;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.model.Plant_Model;
import com.minda.sparsh.model.Sub_Department_Model;
import com.minda.sparsh.model.UserDetail_Model;
import com.minda.sparsh.model.VersionModel;
import com.minda.sparsh.model.ViewAppointmentModel;
import com.minda.sparsh.model.VisitorDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface {


    @GET("GetAppVersion")
    Call<List<VersionModel>> getAppVersion();

    @GET("GetAboutUs")
    Call<List<AboutUsDetails>> getAboutDetails();

    // @FormUrlEncoded
    @GET("GetLogin")
    Call<List<LoginResponse>> GetLogin(@Query("username") String username, @Query("pass") String pass, @Query("CKey") String CKey);

    @GET("ReadPushNot")
    Call<List<NotificationModel>> ReadPushNot(@Query("userid") String userid, @Query("pushid") String pushid, @Query("CKey") String CKey);


    @GET("GetPushNot")
    Call<List<NotificationModel>> GetPushNot(@Query("userid") String UserId, @Query("CKey") String CKey);

    @GET("UpdateDeviceId")
    Call<List<NotificationModel>> UpdateDeviceId(@Query("userid") String userid, @Query("deviceid") String deviceid, @Query("CKey") String CKey);

    @GET("GetSparshImage")
    Call<List<BannarModel>> GetBanner(@Query("") String UserId);


    @GET("GetGroup")
    Call<List<Group_Model>> GetGroup();


    @GET("GetDomain1")
    Call<List<Domain_Model>> GetDomain(@Query("CKey") String CKey);


    @GET("GetDepartment")
    Call<List<Department_Model>> GetDepartment(@Query("CKey") String CKey);


    @GET("GetPlant1")
    Call<List<Plant_Model>> GetPlant(@Query("CKey") String CKey, @Query("Empcode") String Business);


    @GET("GetPlantByUser")
    Call<List<Plant_Model>> GetPlantByUser(@Query("CKey") String CKey, @Query("EmailId") String EmailId);


    @GET("GetBusiness1")
    Call<List<Business_Model>> GetBusiness(@Query("CKey") String CKey, @Query("Domain") String Domain);

//    @FormUrlEncoded
//    @POST("GetAbnormalityByDepartment")
//    Call<List<AbnormalityView_Model>> GetAbnormalityDetail(@Field("CKey") String CKey,@Field("plant") String plant,@Field("department") String department  );

    @FormUrlEncoded
    @POST("AddAbnormalityNew")
    Call<List<AddAbnormality_Model>> AddAbnormality(@Field("CKey") String CKey, @Field("group") String group, @Field("domain") String domain,
                                                    @Field("business") String business, @Field("plant") String plant, @Field("department") String department,
                                                    @Field("imagepath") String imagepath, @Field("description") String description, @Field("benefits") String benefits,
                                                    @Field("abnormalitydate") String abnormalitydate, @Field("UploadedBy") String UploadedBy, @Field("Category") int Categeory);

    @FormUrlEncoded
    @POST("UpdateAbnormalityNew")
    Call<List<AddAbnormality_Model>> UpdateAbnormality(@Field("CKey") String CKey, @Field("AbnormalID") Integer AbnormalID, @Field("ImagePathAfter") String ImagePathAfter,
                                                       @Field("Action") String Action, @Field("ImplementationDate") String ImplementationDate,
                                                       @Field("Benefits") String Benefits, @Field("UpdatedBy") String UpdatedBy);


    @GET("GetAbnormalityImage")
    Call<List<GetAbnormalityImage_Model>> GetAbnormalityImage(@Query("CKey") String CKey, @Query("ID") Integer ID);


    //    @FormUrlEncoded
    @GET("GetUserDetail")
    Call<List<UserDetail_Model>> GetUserDetail(@Query("CKey") String CKey, @Query("EmailID") String EmailID);

    @GET("GetSubDepartment")
    Call<List<Sub_Department_Model>> GetDepartmentDetail(@Query("CKey") String CKey, @Query("Department") String Department);


    @GET("SetTargetDate")
    Call<List<AddTargetDate_Model>> SetTargetDate(@Query("CKey") String CKey, @Query("AbnormalID") Integer AbnormalID,
                                                  @Query("TargetDate") String TargetDate);


    @GET("GetAbnormality")
    Call<List<AbnormalityView_Model>> GetAbnormalityDetail(@Query("CKey") String CKey, @Query("plant") String plant,
                                                           @Query("department") String department, @Query("Domain") String Domain,
                                                           @Query("Business") String Business, @Query("Category") int Category);


    @GET("GetDashboard")
    Call<List<DDModel>> GetDData(@Query("CKey") String CKey, @Query("Domain") String Domain, @Query("Business") String Business,
                                 @Query("Plant") String Plant);


    @GET("GetManufacturingDetails")
    Call<List<DWMDetailResponse>> GetManufacturingDetails(@Query("userid") String userid, @Query("date") String date,
                                                          @Query("CKey") String CKey);

    @FormUrlEncoded
    @POST("NewManufacturingHead")
    Call<List<DwmResponse>> GetNewManufacturingHead(@Field("EmpCode") String EmpCode, @Field("DailyMMeeting") String DailyMMeeting,
                                                    @Field("DailyOneLine") String DailyOneLine, @Field("BoardReview") String BoardReview,
                                                    @Field("ProductionRules") String ProductionRules, @Field("DailyShortageReview") String DailyShortageReview,
                                                    @Field("SReview") String SReview, @Field("MaterialFlow") String MaterialFlow,
                                                    @Field("BreakdownReview") String BreakdownReview
            , @Field("LossessReview") String LossessReview
            , @Field("DepartmentDWM") String DepartmentDWM
            , @Field("QualityReview") String QualityReview
            , @Field("ConcernCorner") String ConcernCorner
            , @Field("RedBinArea") String RedBinArea
            , @Field("MControl") String MControl
            , @Field("QIPReview") String QIPReview
            , @Field("InternalRejection") String InternalRejection
            , @Field("ToolDuplicationReview") String ToolDuplicationReview
            , @Field("AllProductionDepartment") String AllProductionDepartment
            , @Field("CustomerVisits") String CustomerVisits
            , @Field("Materialsaving") String Materialsaving
            , @Field("InventoryReview") String InventoryReview
            , @Field("in1Review") String in1Review
            , @Field("EnergyReview") String EnergyReview
            , @Field("UnitAddress") String UnitAddress
            , @Field("Training") String Training
            , @Field("BESTSupport") String BESTSupport
            , @Field("UnitQCDreview") String UnitQCDreview
            , @Field("MeetingasperInterunit") String MeetingasperInterunit
            , @Field("MMC") String MMC
            , @Field("SystemMeeting") String SystemMeeting
            , @Field("Indent") String Indent
            , @Field("Development") String Development
            , @Field("SAPAdherence") String SAPAdherence
            , @Field("ManufacturingRoadMap") String ManufacturingRoadMap
            , @Field("SafetyReview") String SafetyReview
            , @Field("MPCPReview") String MPCPReview
            , @Field("CommunicationMeeting") String CommunicationMeeting
            , @Field("WorstSupplierMeeting") String WorstSupplierMeeting
            , @Field("SupplierVisits") String SupplierVisits
            , @Field("OtherAct") String OtherAct
            , @Field("OtherAct2") String OtherAct2
            , @Field("OtherAct3") String OtherAct3
            , @Field("OtherAct4") String OtherAct4
            , @Field("OtherAct5") String OtherAct5
            , @Field("OtherAct6") String OtherAct6
            , @Field("OtherActG") String OtherActG
            , @Field("OtherActH") String OtherActH
            , @Field("DailyMMeetingRmk") String DailyMMeetingRmk
            , @Field("DailyOneLineRmk") String DailyOneLineRmk
            , @Field("BoardReviewRmk") String BoardReviewRmk
            , @Field("ProductionRulesRmk") String ProductionRulesRmk
            , @Field("DailyShortageReviewRmk") String DailyShortageReviewRmk
            , @Field("SReviewRmk") String SReviewRmk
            , @Field("MaterialFlowRmk") String MaterialFlowRmk
            , @Field("BreakdownReviewRmk") String BreakdownReviewRmk
            , @Field("LossessReviewRmk") String LossessReviewRmk
            , @Field("DepartmentDWMRmk") String DepartmentDWMRmk
            , @Field("QualityReviewRmk") String QualityReviewRmk
            , @Field("ConcernCornerRmk") String ConcernCornerRmk
            , @Field("RedBinAreaRmk") String RedBinAreaRmk
            , @Field("MControlRmk") String MControlRmk
            , @Field("QIPReviewRmk") String QIPReviewRmk
            , @Field("InternalRejectionRmk") String InternalRejectionRmk
            , @Field("ToolDuplicationReviewRmk") String ToolDuplicationReviewRmk
            , @Field("AllProductionDepartmentRmk") String AllProductionDepartmentRmk
            , @Field("CustomerVisitsRmk") String CustomerVisitsRmk
            , @Field("MaterialsavingRmk") String MaterialsavingRmk
            , @Field("InventoryReviewRmk") String InventoryReviewRmk
            , @Field("in1ReviewRmk") String in1ReviewRmk
            , @Field("EnergyReviewRmk") String EnergyReviewRmk
            , @Field("UnitAddressRmk") String UnitAddressRmk
            , @Field("TrainingRmk") String TrainingRmk
            , @Field("BESTSupportRmk") String BESTSupportRmk
            , @Field("UnitQCDreviewRmk") String UnitQCDreviewRmk
            , @Field("MeetingasperInterunitRmk") String MeetingasperInterunitRmk
            , @Field("MMCRmk") String MMCRmk
            , @Field("SystemMeetingRmk") String SystemMeetingRmk
            , @Field("IndentRmk") String IndentRmk
            , @Field("DevelopmentRmk") String DevelopmentRmk
            , @Field("SAPAdherenceRmk") String SAPAdherenceRmk
            , @Field("ManufacturingRoadMapRmk") String ManufacturingRoadMapRmk
            , @Field("SafetyReviewRmk") String SafetyReviewRmk
            , @Field("MPCPReviewRmk") String MPCPReviewRmk
            , @Field("CommunicationMeetingRmk") String CommunicationMeetingRmk
            , @Field("WorstSupplierMeetingRmk") String WorstSupplierMeetingRmk
            , @Field("SupplierVisitsRmk") String SupplierVisitsRmk
            , @Field("OtherActRmk") String OtherActRmk
            , @Field("OtherAct2Rmk") String OtherAct2Rmk
            , @Field("OtherAct3Rmk") String OtherAct3Rmk
            , @Field("OtherAct4Rmk") String OtherAct4Rmk
            , @Field("OtherAct5Rmk") String OtherAct5Rmk
            , @Field("OtherAct6Rmk") String OtherAct6Rmk
            , @Field("OtherActGRmk") String OtherActGRmk
            , @Field("OtherActHRmk") String OtherActHRmk
            , @Field("Active") String Active
            , @Field("IsDeleted") String IsDeleted
            , @Field("ReportingDate") String ReportingDate
            , @Field("SaveAsDraft") String SaveAsDraft
            , @Field("Frenquency") String Frenquency
            , @Field("Time") String Time
            , @Field("ActType") String ActType
            , @Field("CKey") String CKey
            , @Field("CheckOnCheck") String CheckOnCheck, @Field("CheckOnCheckRmk") String CheckOnCheckRmk);


    @FormUrlEncoded
    @POST("GetOperationDetails")
    Call<List<DWMOprationDetailResponse>> GetGetOperationDetails(@Field("userid") String userid, @Field("date") String date,
                                                                 @Field("CKey") String CKey);

    // @Headers("content-type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("NewOperationHead")
    Call<List<DwmResponse>> GetNewOperationHead(
            @Field("OperationHeadId") String OperationHeadId,
            @Field("EmpCode") String EmpCode,
            @Field("DWMGemba") String DWMGemba,
            @Field("QualityConcerns") String QualityConcerns,
            @Field("QualityWarrnaty") String QualityWarrnaty,
            @Field("QualitySatisfection") String QualitySatisfection,
            @Field("QualityQIP") String QualityQIP,
            @Field("CustomerVisits") String CustomerVisits,
            @Field("CostingInventory") String CostingInventory,
            @Field("CostingCopq") String CostingCopq
            , @Field("CostingDWM") String CostingDWM
            , @Field("CostingVA") String CostingVA
            , @Field("PeopleUnit") String PeopleUnit
            , @Field("PeopleReview") String PeopleReview
            , @Field("PeopleTraning") String PeopleTraning
            , @Field("PeopleBEST") String PeopleBEST
            , @Field("PeopleCPD") String PeopleCPD
            , @Field("ManagementCorp") String ManagementCorp
            , @Field("ManagementProtivity") String ManagementProtivity
            , @Field("ManagementStaff") String ManagementStaff
            , @Field("ManagementQCD") String ManagementQCD
            , @Field("ManagementInterUnit") String ManagementInterUnit
            , @Field("RegularMMC") String RegularMMC
            , @Field("RegularSystem") String RegularSystem
            , @Field("RegularIndent") String RegularIndent
            , @Field("RegularSAP") String RegularSAP
            , @Field("RegularManufect") String RegularManufect
            , @Field("RegularMPCP") String RegularMPCP
            , @Field("RegularSafety") String RegularSafety
            , @Field("SupplierCommu") String SupplierCommu
            , @Field("SupplierWost") String SupplierWost
            , @Field("SupplierAncillaries") String SupplierAncillaries
            , @Field("StrategicContri") String StrategicContri
            , @Field("StrategicReview") String StrategicReview
            , @Field("DevelopmentReview") String DevelopmentReview
            , @Field("DevelopmentMeeting") String DevelopmentMeeting
            , @Field("DevelopmentCustomer") String DevelopmentCustomer
            , @Field("DWMGembaRemark") String DWMGembaRemark
            , @Field("QualityConcernsRemark") String QualityConcernsRemark
            , @Field("QualityWarrnatyRemark") String QualityWarrnatyRemark
            , @Field("QualitySatisfectionRemark") String QualitySatisfectionRemark
            , @Field("QualityQIPRemark") String QualityQIPRemark
            , @Field("CustomerVisitsRemark") String CustomerVisitsRemark
            , @Field("CostingInventoryRemark") String CostingInventoryRemark
            , @Field("CostingCopqRemark") String CostingCopqRemark
            , @Field("CostingDWMRemark") String CostingDWMRemark
            , @Field("CostingVARemark") String CostingVARemark
            , @Field("PeopleUnitRemark") String PeopleUnitRemark
            , @Field("PeopleReviewRemark") String PeopleReviewRemark
            , @Field("PeopleTraningRemark") String PeopleTraningRemark
            , @Field("PeopleBESTRemark") String PeopleBESTRemark
            , @Field("PeopleCPDRemark") String PeopleCPDRemark
            , @Field("ManagementCorpRemark") String ManagementCorpRemark
            , @Field("ManagementProtivityRemark") String ManagementProtivityRemark
            , @Field("ManagementStaffRemark") String ManagementStaffRemark
            , @Field("ManagementQCDRemark") String ManagementQCDRemark
            , @Field("ManagementInterUnitRemark") String ManagementInterUnitRemark
            , @Field("RegularMMCRemark") String RegularMMCRemark
            , @Field("RegularSystemRemark") String RegularSystemRemark
            , @Field("RegularIndentRemark") String RegularIndentRemark
            , @Field("RegularSAPRemark") String RegularSAPRemark
            , @Field("RegularManufectRemark") String RegularManufectRemark
            , @Field("RegularMPCPRemark") String RegularMPCPRemark
            , @Field("RegularSafetyRemark") String RegularSafetyRemark
            , @Field("SupplierCommuRemark") String SupplierCommuRemark
            , @Field("SupplierWostRemark") String SupplierWostRemark
            , @Field("SupplierAncillariesRemark") String SupplierAncillariesRemark
            , @Field("StrategicContriRemark") String StrategicContriRemark
            , @Field("StrategicReviewRemark") String StrategicReviewRemark
            , @Field("DevelopmentReviewRemark") String DevelopmentReviewRemark
            , @Field("DevelopmentMeetingRemark") String DevelopmentMeetingRemark
            , @Field("DevelopmentCustomerRemark") String DevelopmentCustomerRemark
            , @Field("Active") String Active
            , @Field("IsDeleted") String IsDeleted
            , @Field("ReportingDate") String ReportingDate
            , @Field("CKey") String CKey
            , @Field("ActType") String ActType
            , @Field("OtherAct") String OtherAct
            , @Field("OtherAct2") String OtherAct2
            , @Field("OtherAct3") String OtherAct3
            , @Field("OtherAct4") String OtherAct4
            , @Field("OtherAct5") String OtherAct5
            , @Field("OtherAct6") String OtherAct6
            , @Field("OtherActG") String OtherActG
            , @Field("OtherActH") String OtherActH
            , @Field("OtherActRemark") String OtherActRemark
            , @Field("OtherAct2Remark") String OtherAct2Remark
            , @Field("OtherAct3Remark") String OtherAct3Remark
            , @Field("OtherAct4Remark") String OtherAct4Remark
            , @Field("OtherAct5Remark") String OtherAct5Remark
            , @Field("OtherAct6Remark") String OtherAct6Remark
            , @Field("OtherActGRemark") String OtherActGRemark
            , @Field("OtherActHRemark") String OtherActHRemark
            , @Field("CheckOnCheck") String CheckOnCheck, @Field("CheckOnCheckRmk") String CheckOnCheckRmk);


    @GET("NewVisitor")
    Call<List<AddAbnormality_Model>> NewVisitor(@Query("CKey") String CKey, @Query("FirstName") String FirstName, @Query("LastName") String LastName,
                                                @Query("Mobile") String Mobile, @Query("EmailId") String EmailId,
                                                @Query("EmpCode") String EmpCode, @Query("CompanyName") String CompanyName, @Query("Address") String Address,
                                                @Query("City") String City, @Query("PinCode") String PinCode, @Query("Gender") String Gender, @Query("Purpose") String Purpose,
                                                @Query("TimeIn") String TimeIn, @Query("TimeOut") String TimeOut, @Query("VisitingDate") String VisitingDate,
                                                @Query("AppointmentDate") String AppointmentDate,
                                                @Query("UnitCode") String UnitCode, @Query("AddPerson") String AddPerson, @Query("id") String id);

    @GET("GetVisitorList")
    Call<List<ViewAppointmentModel>> GetVisitorList(@Query("CKey") String CKey, @Query("EmpCode") String EmpCode);


    @GET("GetPdf")
    Call<List<AddAbnormality_Model>> GetPdf(@Query("Type") String type, @Query("CKey") String CKey);

    @GET("GetApprovalList")
    Call<List<ApproveList>> GetApprovalList(@Query("CKey") String CKey, @Query("EmpCode") String EmpCode);

    @GET("GetAccessRequestDetail")
    Call<List<AccessRequestDetailsModel>> GetAccessRequestDetail(@Query("CKey") String CKey, @Query("RequestId") String RequestId);

    @GET("GetAccessRequestPlantDetail")
    Call<List<AccessRequestPlantDetailModel>> GetAccessRequestPlantDetail(@Query("CKey") String CKey, @Query("AccessRequestNo") String AccessRequestNo);


    @GET("GetAccessRequestApproverDetail")
    Call<List<AccessRequestApproverDetailsModel>> GetAccessRequestApproverDetail(@Query("CKey") String CKey, @Query("AccessRequestNo") String AccessRequestNo);

    @GET("GetAccessRequestProcessorDetail")
    Call<List<ARPDModel>> GetAccessRequestProcessorDetail(@Query("CKey") String CKey, @Query("AccessRequestNo") String AccessRequestNo, @Query("CategoryId") String CategoryId);

    @GET("GetAccessRequestProcessorDetailExt")
    Call<List<ARPDModel>> GetAccessRequestProcessorDetailExt(@Query("CKey") String CKey, @Query("AccessRequestNo") String AccessRequestNo);


    @GET("IAMApprove")
    Call<String> IAMApprove(@Query("CKey") String CKey, @Query("ApprovalId") String ApprovalId, @Query("AccessRequestNo") String AccessRequestNo,
                            @Query("EmpCode") String EmpCode, @Query("ApprovalText") String ApprovalText, @Query("Attachment") String Attachment, @Query("ApprovalLevel") String ApprovalLevel);

    @GET("IAMUnApprove")
    Call<String> IAMUnApprove(@Query("CKey") String CKey, @Query("ApprovalId") String ApprovalId, @Query("AccessRequestNo") String AccessRequestNo,
                              @Query("EmpCode") String EmpCode, @Query("RejectionText") String RejectionText, @Query("Attachment") String Attachment, @Query("ApprovalLevel") String ApprovalLevel);


    @GET("IAMBacktoRequestor")
    Call<String> IAMBacktoRequestor(@Query("CKey") String CKey, @Query("ApprovalId") String ApprovalId, @Query("AccessRequestNo") String AccessRequestNo,
                                    @Query("EmpCode") String EmpCode, @Query("RejectionText") String RejectionText, @Query("Attachment") String Attachment, @Query("ApprovalLevel") String ApprovalLevel);


    @GET("GetVisitorDetail")
    Call<List<VisitorDetailModel>> GetVisitorDetail(@Query("CKey") String CKey, @Query("VisitorId") String VisitorId);

    @GET("GetVisitorDetailbyMobile")
    Call<List<AutoFillMobileModel>> GetVisitorDetailbyMobile(@Query("CKey") String CKey, @Query("Mobile") String Mobile);


    @GET("UpdateVisitorStatus")
    Call<List<AddAbnormality_Model>> UpdateVisitorStatus(@Query("CKey") String CKey, @Query("VisitorId") String VisitorId, @Query("Status") String Status);


    @GET("UpdateVisitorDetail")
    Call<List<AddAbnormality_Model>> UpdateVisitorDetail(@Query("CKey") String CKey, @Query("VisitorId") String VisitorId, @Query("FirstName") String FirstName, @Query("LastName") String LastName,
                                                         @Query("Mobile") String Mobile, @Query("EmailId") String EmailId, @Query("EmpCode") String EmpCode,
                                                         @Query("CompanyName") String CompanyName, @Query("Address") String Address, @Query("City") String City,
                                                         @Query("PinCode") String PinCode, @Query("Gender") String Gender, @Query("Purpose") String Purpose,
                                                         @Query("TimeIn") String TimeIn, @Query("TimeOut") String TimeOut, @Query("VisitingDate") String VisitingDate,
                                                         @Query("AppointmentDate") String AppointmentDate, @Query("UnitCode") String UnitCode, @Query("AddPerson") String AddPerson);


    @GET("IAMGetRequestType")
    Call<List<IAMGetRequestTypeSpinnerModel>> IAMGetRequestType(@Query("CKey") String CKey);

    @GET("IAMGetAccessType")
    Call<List<IAMGetAccessTypeSpinnerModel>> IAMGetAccessType(@Query("CKey") String CKey);

    @GET("IAMGetCategory")
    Call<List<IAMGetCategorySpinnerModel>> IAMGetCategory(@Query("RequestType") String RequestType, @Query("CKey") String CKey);

    @GET("IAMGetSubCategory")
    Call<List<IAMGetSubCategoryModel>> IAMGetSubCategory(@Query("CategoryId") String CategoryId, @Query("CKey") String CKey);

    @GET("IAMGetDomain")
    Call<List<IAMGetDomainModel>> IAMGetDomain(@Query("CKey") String CKey);

    @GET("IAMGetAccessSubType")
    Call<List<IAMGetAccessSubTypeModel>> IAMGetAccessSubType(@Query("AccessType") String accessType, @Query("CKey") String CKey);


    @GET("IAMGetAuthorizationProfile")
    Call<List<IAMGetAuthorizationProfileModel>> IAMGetAuthorizationProfile(@Query("CategoryId") String CategoryId, @Query("CKey") String CKey);

    @GET("IAMGetBusiness")
    Call<List<IAMGetBusinessModel>> IAMGetBusiness(@Query("DomainId") String DomainId, @Query("CKey") String CKey);

    @GET("IAMGetPlant")
    Call<List<IAMGetPlantModel>> IAMGetPlant(@Query("BusinessId") String BusinessId, @Query("CKey") String CKey);


    @GET("IAMGetListofNames")
    Call<List<IAMGetListOfNames>> IAMGetListofNames(@Query("PrefixText") String PrefixText, @Query("CKey") String CKey);


    /*
        @GET("IAMCreateRequest")
        Call<List<IAMCreateRequestModel>> IAMCreateRequest(@Query("RequestTypeId") String RequestTypeId, @Query("AccessTypeId") String AccessTypeId,
                                                           @Query("AccessForTypeId") String AccessForTypeId, @Query("EmpCode") String EmpCode,
                                                           @Query("SourceTypeId") String SourceTypeId, @Query("SourceEmpCode") String SourceEmpCode,
                                                           @Query("Organization") String Organization, @Query("Purpose") String Purpose,
                                                           @Query("SourceName") String SourceName, @Query("AccessSubTypeId") String AccessSubTypeId,
                                                           @Query("CategoryId") String CategoryId, @Query("SubCategoryId") String SubCategoryId,
                                                           @Query("CategoryName") String CategoryName, @Query("SubCategoryName") String SubCategoryName,
                                                           @Query("ProfileId") String ProfileId, @Query("ProfileName") String ProfileName,
                                                           @Query("RequirementDetail") String RequirementDetail, @Query("CategoryList") String CategoryList,
                                                           @Query("UnitList") String UnitList, @Query("CKey") String CKey);

    */
    @FormUrlEncoded
    @POST("IAMCreateRequest")
    Call<List<IAMCreateRequestModel>> IAMCreateRequest(@Field("RequestTypeId") String RequestTypeId, @Field("AccessTypeId") String AccessTypeId,
                                                       @Field("AccessForTypeId") String AccessForTypeId, @Field("EmpCode") String EmpCode,
                                                       @Field("SourceTypeId") String SourceTypeId, @Field("SourceEmpCode") String SourceEmpCode,
                                                       @Field("Organization") String Organization, @Field("Purpose") String Purpose,
                                                       @Field("SourceName") String SourceName, @Field("AccessSubTypeId") String AccessSubTypeId,
                                                       @Field("CategoryId") String CategoryId, @Field("SubCategoryId") String SubCategoryId,
                                                       @Field("CategoryName") String CategoryName, @Field("SubCategoryName") String SubCategoryName,
                                                       @Field("ProfileId") String ProfileId, @Field("ProfileName") String ProfileName,
                                                       @Field("RequirementDetail") String RequirementDetail, @Field("CategoryList") String CategoryList,
                                                       @Field("UnitList") String UnitList, @Field("CKey") String CKey, @Field("FileName") String FileName, @Field("FileByte") String FileByte, @Field("_Domains") String Domains, @Field("_BusinessID") String BusinessID, @Field("_DomainNames") String DomainNames, @Field("BusinessIdName") String BusinessIdName, @Field("_PlantName") String PlantName, @Field("_PlantCode") String PlantCode, @Field("_AccessSubTypeName") String AccessSubTypeName, @Field("_AccessTypeName") String AccessTypeName, @Field("_RequestTypeName") String RequestTypeName);


    @GET("GetCategorys")
    Call<List<CategoryAbnormality>> getCategory(@Query("CKey") String CKey);
}