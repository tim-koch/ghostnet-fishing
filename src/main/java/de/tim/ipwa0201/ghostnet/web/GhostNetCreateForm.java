package de.tim.ipwa0201.ghostnet.web;

public class GhostNetCreateForm {

    private double latitude;
    private double longitude;
    private String sizeEstimate;

    private boolean anonymous;

    private String reporterName;
    private String phoneNumber;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSizeEstimate() {
        return sizeEstimate;
    }

    public void setSizeEstimate(String sizeEstimate) {
        this.sizeEstimate = sizeEstimate;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
