package by.baturel.spring.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class RegistrationForm {

    //region Constructors
    public RegistrationForm() {
    }

    public RegistrationForm(String userLogin, String userPassword, String userName, String userSurname, String userLastname, Date userBirthday, BigDecimal userWeight, int userHeight, String eMail) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userLastname = userLastname;
        this.userBirthday = userBirthday;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.eMail = eMail;
    }

    //endregion

    //region Fields
    private String userLogin;
    private String userPassword;
    private String userName;
    private String userSurname;
    private String userLastname;
    private Date userBirthday;
    private BigDecimal userWeight;
    private int userHeight;
    private String eMail;
    //endregion

    //region Properties

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public BigDecimal getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(BigDecimal userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }
    //endregion
}
