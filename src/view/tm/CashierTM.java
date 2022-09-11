package view.tm;

import javafx.scene.control.Button;

public class CashierTM {
    private String cashierID;
    private String cashierName;
    private String cashierNIC;
    private String cashierAddress;
    private String contact;
    private String status;
    private Button update;
    private Button remove;

    public CashierTM() {
    }

    public CashierTM(String cashierID, String cashierName, String cashierNIC, String cashierAddress, String contact, String status, Button update, Button remove) {
        this.cashierID = cashierID;
        this.cashierName = cashierName;
        this.cashierNIC = cashierNIC;
        this.cashierAddress = cashierAddress;
        this.contact = contact;
        this.status = status;
        this.update = update;
        this.remove = remove;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
