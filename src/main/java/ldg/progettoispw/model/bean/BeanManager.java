package ldg.progettoispw.model.bean;

public class BeanManager {
    //SINGLETON
    private static BeanManager instance;
    private UserBean userBean;

    public static synchronized BeanManager getInstance() {
        if (instance == null) {
            instance = new BeanManager();
        }
        return instance;
    }

    //get e set dell'istanza di UserBean
    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
