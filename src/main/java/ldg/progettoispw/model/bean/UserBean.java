package ldg.progettoispw.model.bean;


public class UserBean {
    // i 6 elementi sono: name, surname, birth, email, password, subjects
    private String[] userdata= new String[6];
    public void set(String[] strings){this.userdata=strings;}
    public String[] get(){return this.userdata;}
}