package john.zhao.arunningman.model;

public class SelectInfo {

    private String address;
    private String city;
    private double latitude;
    private double longtitude;

    public String getAddress() {
        return address;
    }

    public SelectInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SelectInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public SelectInfo setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public SelectInfo setLongtitude(double longtitude) {
        this.longtitude = longtitude;
        return this;
    }
}
