package Memorandum_Project;

public class Account {
    private String type;
    private String bank;
    private String io;
    private double amt;
    private String date;
    private String notes;

    public Account() {
    }

    public Account( String type, String bank, String io, double amt, String date, String notes) {
        this.type = type;
        this.bank = bank;
        this.io = io;
        this.amt = amt;
        this.date = date;
        this.notes = notes;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setBank(String bank){
        this.bank = bank;
    }
    public void setIo(String io){
        this.io = io;
    }
    public void setAmt(double amt){
        this.amt = amt;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getType() {
        return type;
    }

    public String getBank() {
        return bank;
    }

    public String getIo() {
        return io;
    }

    public double getAmt() {
        return amt;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }
}
