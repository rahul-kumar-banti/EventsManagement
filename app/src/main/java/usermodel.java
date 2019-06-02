import android.net.Uri;

public class usermodel {

    private  String userid;
    private String dispname,email,mobile,password,accounttype,department,profile;
    usermodel(){

    }

    public usermodel(String userid, String dispname, String email, String mobile, String password, String accounttype, String department, String profile) {
        this.userid = userid;
        this.dispname = dispname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.accounttype = accounttype;
        this.department = department;
        this.profile = profile;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setDispname(String dispname) {
        this.dispname = dispname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserid() {
        return userid;
    }

    public String getDispname() {
        return dispname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public String getDepartment() {
        return department;
    }

    public String getProfile() {
        return profile;
    }
}
