package by.baturel.spring.DTO;

public class LogInForm {

    //region Constructors
    public LogInForm(String userLogin, String userPassword) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public LogInForm() {
    }
    //endregion

    //region Fields
    private String userLogin;
    private String userPassword;
    //endregion

    //region Properties
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
    //endregion
}
