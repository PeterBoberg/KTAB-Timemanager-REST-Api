package se.karingotrafiken.timemanager.rest.dto;

import java.util.Objects;

public class MonthAndYear implements Comparable<MonthAndYear> {

    private int year;
    private int month;

    public MonthAndYear(int year, int month) {
        this.year = year;
        this.month = month;
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

    public MonthAndYear minusOneMonth() {
        if (this.month == 1)
            return new MonthAndYear(this.year - 1, 12);
        return new MonthAndYear(this.year, this.month - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthAndYear that = (MonthAndYear) o;
        return year == that.year &&
                month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public int compareTo(MonthAndYear that) {
        if (this.year < that.year) return -1;
        if (this.year > that.year) return 1;
        if (this.month < that.month) return -1;
        if (this.month > that.month) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "MonthAndYear{" +
                "year=" + year +
                ", month=" + month +
                '}';
    }
}
