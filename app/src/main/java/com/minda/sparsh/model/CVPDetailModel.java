package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CVPDetailModel {
    @SerializedName("Data")
    CVPDetailData data;

    public CVPDetailData getData() {
        return data;
    }

    public void setData(CVPDetailData data) {
        this.data = data;
    }

    public class CVPDetailData{
        @SerializedName("CustomerMomId")
        String CustomerMomId;
        @SerializedName("Customer")
        String Customer;
        @SerializedName("DateOfVisit")
        String DateOfVisit;
        @SerializedName("StartTime")
        String StartTime;
        @SerializedName("EndTime")
        String EndTime;
        @SerializedName("Agenda")
        String Agenda;
        @SerializedName("Location")
        String Location;
        @SerializedName("CustLocation")
        String CustLocation;
        @SerializedName("InternalCustomer")
        String InternalCustomer;
        @SerializedName("ExternalCustomer")
        String ExternalCustomer;
        @SerializedName("overAllDiscussion")
        List<OverAllDiscussion> overAllDisussion;
        @SerializedName("actionPoint")
        List<ActionPoint> actionPoint;

        public String getCustomerMomId() {
            return CustomerMomId;
        }

        public void setCustomerMomId(String customerMomId) {
            CustomerMomId = customerMomId;
        }

        public String getCustomer() {
            return Customer;
        }

        public void setCustomer(String customer) {
            Customer = customer;
        }

        public String getDateOfVisit() {
            return DateOfVisit;
        }

        public void setDateOfVisit(String dateOfVisit) {
            DateOfVisit = dateOfVisit;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getAgenda() {
            return Agenda;
        }

        public void setAgenda(String agenda) {
            Agenda = agenda;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getInternalCustomer() {
            return InternalCustomer;
        }

        public void setInternalCustomer(String internalCustomer) {
            InternalCustomer = internalCustomer;
        }

        public String getExternalCustomer() {
            return ExternalCustomer;
        }

        public void setExternalCustomer(String externalCustomer) {
            ExternalCustomer = externalCustomer;
        }

        public List<OverAllDiscussion> getOverAllDisussion() {
            return overAllDisussion;
        }

        public void setOverAllDisussion(List<OverAllDiscussion> overAllDisussion) {
            this.overAllDisussion = overAllDisussion;
        }

        public List<ActionPoint> getActionPoint() {
            return actionPoint;
        }

        public void setActionPoint(List<ActionPoint> actionPoint) {
            this.actionPoint = actionPoint;
        }

        public String getCustLocation() {
            return CustLocation;
        }

        public void setCustLocation(String custLocation) {
            CustLocation = custLocation;
        }

        public class OverAllDiscussion{
            @SerializedName("Sno")
            String Sno;
            @SerializedName("Business")
            String Business;
            @SerializedName("Plant")
            String Plant;
            @SerializedName("Fuction")
            String Fuction;
            @SerializedName("Discussion")
            String Discussion;
            @SerializedName("Action")
            String Action;

            public String getSno() {
                return Sno;
            }

            public void setSno(String sno) {
                Sno = sno;
            }

            public String getBusiness() {
                return Business;
            }

            public void setBusiness(String business) {
                Business = business;
            }

            public String getPlant() {
                return Plant;
            }

            public void setPlant(String plant) {
                Plant = plant;
            }

            public String getFuction() {
                return Fuction;
            }

            public void setFuction(String fuction) {
                Fuction = fuction;
            }

            public String getDiscussion() {
                return Discussion;
            }

            public void setDiscussion(String discussion) {
                Discussion = discussion;
            }

            public String getAction() {
                return Action;
            }

            public void setAction(String action) {
                Action = action;
            }
        }
        public class ActionPoint{
            @SerializedName("Sno")
            String Sno;
            @SerializedName("Business")
            String Business;
            @SerializedName("Plant")
            String Plant;
            @SerializedName("Fuction")
            String Fuction;
            @SerializedName("ActionPoint")
            String ActionPoint;
            @SerializedName("ResponsiblePerson")
            String ResponsiblePerson;
            @SerializedName("TargetDate")
            String TargetDate;
            @SerializedName("Remark")
            String Remark;
            @SerializedName("CompletedDate")
            String CompletedDate;
            @SerializedName("Status")
            String Status;

            public String getSno() {
                return Sno;
            }

            public void setSno(String sno) {
                Sno = sno;
            }

            public String getBusiness() {
                return Business;
            }

            public void setBusiness(String business) {
                Business = business;
            }

            public String getPlant() {
                return Plant;
            }

            public void setPlant(String plant) {
                Plant = plant;
            }

            public String getFuction() {
                return Fuction;
            }

            public void setFuction(String fuction) {
                Fuction = fuction;
            }

            public String getActionPoint() {
                return ActionPoint;
            }

            public void setActionPoint(String actionPoint) {
                ActionPoint = actionPoint;
            }

            public String getResponsiblePerson() {
                return ResponsiblePerson;
            }

            public void setResponsiblePerson(String responsiblePerson) {
                ResponsiblePerson = responsiblePerson;
            }

            public String getTargetDate() {
                return TargetDate;
            }

            public void setTargetDate(String targetDate) {
                TargetDate = targetDate;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String remark) {
                Remark = remark;
            }

            public String getCompletedDate() {
                return CompletedDate;
            }

            public void setCompletedDate(String completedDate) {
                CompletedDate = completedDate;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }
        }
    }
}
