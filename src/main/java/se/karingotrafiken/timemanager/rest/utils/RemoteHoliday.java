package se.karingotrafiken.timemanager.rest.utils;

public class RemoteHoliday {

    private RemoteDate date;
    private String localName;
    private String englishName;

    public RemoteDate getDate() {
        return date;
    }

    public void setDate(RemoteDate date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Override
    public String toString() {
        return "RemoteHoliday{localName='" + localName + '\'' +
                ", englishName='" + englishName + '\'' +
                '}';
    }
}
