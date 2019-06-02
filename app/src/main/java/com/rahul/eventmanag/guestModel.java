package com.rahul.eventmanag;

public class guestModel {
    String gemail,gname,gaddress,gmobile,gcompany,gdesignation,descrtion;
    guestModel(){

    }

    public guestModel(String gemail, String gname, String gaddress, String gmobile, String gcompany, String gdesignation, String descrtion) {
        this.gemail = gemail;
        this.gname = gname;
        this.gaddress = gaddress;
        this.gmobile = gmobile;
        this.gcompany = gcompany;
        this.gdesignation = gdesignation;
        this.descrtion = descrtion;
    }

    public String getGemail() {
        return gemail;
    }

    public String getGname() {
        return gname;
    }

    public String getGaddress() {
        return gaddress;
    }

    public String getGmobile() {
        return gmobile;
    }

    public String getGcompany() {
        return gcompany;
    }

    public String getGdesignation() {
        return gdesignation;
    }

    public String getDescrtion() {
        return descrtion;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public void setGaddress(String gaddress) {
        this.gaddress = gaddress;
    }

    public void setGmobile(String gmobile) {
        this.gmobile = gmobile;
    }

    public void setGcompany(String gcompany) {
        this.gcompany = gcompany;
    }

    public void setGdesignation(String gdesignation) {
        this.gdesignation = gdesignation;
    }

    public void setDescrtion(String descrtion) {
        this.descrtion = descrtion;
    }
}
