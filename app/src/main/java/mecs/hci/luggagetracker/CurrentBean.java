package mecs.hci.luggagetracker;


import com.punchthrough.bean.sdk.Bean;

public class CurrentBean {
private static Bean bean;

    public static Bean getBean() {
        return bean;
    }
public static void setBean(Bean newBean){
    bean = newBean;
}
}
