package model;

public class Cashier {
    private String cashierID;
    private String cashierName;
    private String cashierNIC;
    private String cashierAddress;
    private String contact;
    private String password;

    public Cashier() {
    }

    public Cashier(String cashierID, String cashierName, String cashierNIC, String cashierAddress, String contact, String password) {
        this.cashierID = cashierID;
        this.cashierName = cashierName;
        this.cashierNIC = cashierNIC;
        this.cashierAddress = cashierAddress;
        this.contact = contact;
        this.password = password;
    }

    public String getCashierID() {
        return cashierID;
    }

    public void setCashierID(String cashierID) {
        this.cashierID = cashierID;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierNIC() {
        return cashierNIC;
    }

    public void setCashierNIC(String cashierNIC) {
        this.cashierNIC = cashierNIC;
    }

    public String getCashierAddress() {
        return cashierAddress;
    }

    public void setCashierAddress(String cashierAddress) {
        this.cashierAddress = cashierAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
