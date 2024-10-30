package com.example.finalproject;

public class Course {
        private String cname, cid;
        private String credit, lect, lab;

        public Course(String cid, String cname, String credit, String lect, String lab) {
            this.cid = cid;
            this.cname = cname;
            this.credit = credit;
            this.lect = lect;
            this.lab = lab;
        }

        public String getCid() {
            return cid;
        }

        public String getCname() {
            return cname;
        }

        public String getCredit() {
            return credit;
        }

        public String getLect() {
            return lect;
        }

        public String getLab() {
            return lab;
        }

        public String toString() {
            return cid + " " + cname + " " + credit + " " + lect + " " + lab;
        }
    }


