package com.example.communityapplication.ModelClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityModel {




        @SerializedName("topimage")
        @Expose
        private String topimage;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("bottomimage1")
        @Expose
        private String bottomimage1;
        @SerializedName("bottomimage2")
        @Expose
        private String bottomimage2;

        public String getTopimage() {
            return topimage;
        }

        public void setTopimage(String topimage) {
            this.topimage = topimage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBottomimage1() {
            return bottomimage1;
        }

        public void setBottomimage1(String bottomimage1) {
            this.bottomimage1 = bottomimage1;
        }

        public String getBottomimage2() {
            return bottomimage2;
        }

        public void setBottomimage2(String bottomimage2) {
            this.bottomimage2 = bottomimage2;
        }


}
