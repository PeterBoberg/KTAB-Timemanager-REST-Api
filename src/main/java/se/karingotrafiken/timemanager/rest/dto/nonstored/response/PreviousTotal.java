package se.karingotrafiken.timemanager.rest.dto.nonstored.response;

public class PreviousTotal {

    private int year;
    private int month;
    private double netTotal;

    public PreviousTotal() {
    }

    public PreviousTotal(int year, int month, double netTotal) {
        this.year = year;
        this.month = month;
        this.netTotal = netTotal;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }
}
