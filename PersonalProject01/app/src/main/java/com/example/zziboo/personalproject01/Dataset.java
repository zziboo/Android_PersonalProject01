package com.example.zziboo.personalproject01;

/**
 * Created by zziboo on 2017-05-17.
 */

public class Dataset {

        String title, url, day;

        public Dataset(String title, String url, String day){
            this.title = title;
            this.url = url;
            this.day = day;
        }

        public String getTitle(){
            return title;
        }

        public String getURL(){
            return url;
        }

        public String getDay(){
            return day;
        }
}
