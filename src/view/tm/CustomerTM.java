package view.tm;

import javafx.scene.control.Button;

public class CustomerTM {
    private String cusID;
    private String cusName;
    private String cusNIC;
    private String cusAddress;
    private String contact;
    private double income;
    private Button update;
    private Button remove;

    public CustomerTM() {
    }

    public CustomerTM(String cusID, String cusName, String cusNIC, String cusAddress, String contact, double income, Button update, Button remove) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusNIC = cusNIC;
        this.cusAddress = cusAddress;
        this.contact = contact;
        this.income = income;
        this.update = update;
        this.remove = remove;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusNIC() {
        return cusNIC;
    }

    public void setCusNIC(String cusNIC) {
        this.cusNIC = cusNIC;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
