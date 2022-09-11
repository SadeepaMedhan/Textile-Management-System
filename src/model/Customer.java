package model;

public class Customer {
    private String cusID;
    private String cusName;
    private String cusNIC;
    private String cusAddress;
    private String contact;

    public Customer() {
    }

    public Customer(String cusID, String cusName, String cusNIC, String cusAddress, String contact) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusNIC = cusNIC;
        this.cusAddress = cusAddress;
        this.contact = contact;
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
}
