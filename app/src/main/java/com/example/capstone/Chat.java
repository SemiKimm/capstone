package com.example.capstone;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    //Data Transfer Object(DTO)

    //public Map<String, Boolean> user = new HashMap<>();
    //public Map<String, Boolean> comment = new HashMap<>();





    public static class comment {
        private String name;
        private String msg;

        public String getName() { return name; }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    public static class user {
        private String from;
        private String to;

        public String getFrom() { return from; }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }




}
