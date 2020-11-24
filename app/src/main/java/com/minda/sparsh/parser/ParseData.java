package com.minda.sparsh.parser;


import com.minda.sparsh.model.Dwm_Model;
import com.minda.sparsh.model.LoginModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 5/26/2017.
 */

public class ParseData {

    public LoginModel getLoginValue(String response) {
        LoginModel loginModel = new LoginModel();
        if (response != null && response.length() > 0) {
            try {
                JSONArray myData = new JSONArray(response);
                for (int i = 0; i < myData.length(); i++) {
                    JSONObject jo = new JSONObject(myData.getString(i));
                    loginModel.Id = jo.getString("UM_USER_ID");
                    loginModel.UserName = jo.getString("UM_USER_DESC");
                    loginModel.EmainId = jo.getString("UM_EMAIL_ID");
                    loginModel.AuthFor = jo.getString("AuthFor");
                    loginModel.UM_DIV_CODE = jo.getString("UM_DIV_CODE");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return loginModel;
    }

    public ArrayList<Dwm_Model> getDwmData(String response) {
        ArrayList<Dwm_Model> values = new ArrayList<>();
        try {
            JSONArray myData = new JSONArray(response);
            for (int i = 0; i < myData.length(); i++) {
                JSONObject jo = new JSONObject(myData.getString(i));
                Dwm_Model value = new Dwm_Model();
                value.setEmpCode(jo.optString("EmpCode"));
                value.setDailyMMeeting(jo.optInt("DailyMMeeting"));
                value.setdWNGemba(jo.optInt("DWNGemba"));
                value.setQualityReview(jo.optInt("QualityReview"));
                value.setCustomerVisits(jo.optInt("CustomerVisits"));
                value.setCostingReview(jo.optInt("CostingReview"));
                value.setPeopleUnit(jo.optInt("PeopleUnit"));
                value.setPeopleBM(jo.optInt("PeopleBM"));
                value.setPeopleLLLG(jo.optInt("PeopleLLLG"));
                value.setPeopleTraining(jo.optInt("PeopleTraining"));
                value.setPeopleBest(jo.optInt("PeopleBest"));
                value.setPeopleCDP(jo.optInt("PeopleCDP"));
                value.setManagementCorp(jo.optInt("ManagementCorp"));
                value.setManagementProtivity(jo.optInt("ManagementProtivity"));
                value.setManagementUnit(jo.optInt("ManagementUnit"));
                value.setManagementMeeting(jo.optInt("ManagementMeeting"));
                value.setRegularMMC(jo.optInt("RegularMMC"));
                value.setRegularSystem(jo.optInt("RegularSystem"));
                value.setRegularIndent(jo.optInt("RegularIndent"));
                value.setRegularDevelopment(jo.optInt("RegularDevelopment"));
                value.setRegularSAP(jo.optInt("RegularSAP"));
                value.setRegularManufact(jo.optInt("RegularManufact"));
                value.setRegularMPCP(jo.optInt("RegularMPCP"));
                value.setRegularSafety(jo.optInt("RegularSafety"));
                value.setSupplierComm(jo.optInt("SupplierComm"));
                value.setSupplierWorst(jo.optInt("SupplierWorst"));
                value.setSupplierSupplier(jo.optInt("SupplierSupplier"));
                value.setDailyMMeetingRemark(jo.optString("DailyMMeetingRemark"));
                value.setDWNGembaRemark(jo.optString("DWNGembaRemark"));
                value.setQualityReviewRemark(jo.optString("QualityReviewRemark"));
                value.setCustomerVisitsRemark(jo.optString("CustomerVisitsRemark"));
                value.setCostingReviewRemark(jo.optString("CostingReviewRemark"));
                value.setPeopleUnitRemark(jo.optString("PeopleUnitRemark"));
                value.setPeopleBMRemark(jo.optString("PeopleBMRemark"));
                value.setPeopleLLLGRemark(jo.optString("PeopleLLLGRemark"));
                value.setPeopleTrainingRemark(jo.optString("PeopleTrainingRemark"));
                value.setPeopleBestRemark(jo.optString("PeopleBestRemark"));
                value.setPeopleCDPRemark(jo.optString("PeopleCDPRemark"));
                value.setManagementCorpRemark(jo.optString("ManagementCorpRemark"));
                value.setManagementProtivityRemark(jo.optString("ManagementProtivityRemark"));
                value.setManagementUnitRemark(jo.optString("ManagementUnitRemark"));
                value.setManagementMeetingRemark(jo.optString("ManagementMeetingRemark"));
                value.setRegularMMCRemark(jo.optString("RegularMMCRemark"));
                value.setRegularSystemRemark(jo.optString("RegularSystemRemark"));
                value.setRegularIndentRemark(jo.optString("RegularIndentRemark"));
                value.setRegularDevelopmentRemark(jo.optString("RegularDevelopmentRemark"));
                value.setRegularSAPRemark(jo.optString("RegularSAPRemark"));
                value.setRegularManufactRemark(jo.optString("RegularManufactRemark"));
                value.setRegularMPCPRemark(jo.optString("RegularMPCPRemark"));
                value.setRegularSafetyRemark(jo.optString("RegularSafetyRemark"));
                value.setSupplierCommRemark(jo.optString("SupplierCommRemark"));
                value.setSupplierWorstRemark(jo.optString("SupplierWorstRemark"));
                value.setSupplierSupplierRemark(jo.optString("SupplierSupplierRemark"));
                value.setOtherActRemark(jo.optString("OtherActRemark"));
                value.setOtherAct(jo.optString("OtherAct"));


                value.setOtherActRemark2(jo.optString("OtherActRemark2"));
                value.setOtherActRemark3(jo.optString("OtherActRemark3"));
                value.setOtherActRemark4(jo.optString("OtherActRemark4"));
                value.setOtherActRemark5(jo.optString("OtherActRemark5"));

                value.setOtherAct2(jo.optString("OtherAct2"));
                value.setOtherAct3(jo.optString("OtherAct3"));
                value.setOtherAct4(jo.optString("OtherAct4"));
                value.setOtherAct5(jo.optString("OtherAct5"));

                value.setCheckoncheck(jo.optString("checkoncheck"));
                value.setCheckoncheckrmk(jo.optString("checkoncheckrmk"));
                value.setLpa(jo.optInt("Lpa"));
                value.setLPA(jo.optInt("LPA"));

                value.setCovid(jo.optInt("Covid"));
                value.setLpaCovid(jo.optInt("LpaCovid"));
                value.setLapRemark(jo.optString("LapRemark"));
                value.setCovidRemark(jo.optString("CovidRemark"));
                value.setLpaCovidremark(jo.optString("LpaCovidremark"));

                values.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
}