package tp.com.usrestaurants;

import java.io.Serializable;

public class Restaurant implements Serializable {
    public int per_page;
    public int id;
    public String name;
    public String address;
    public String city;
    public String postal_code;
    public int price;
    public String mobile_reserve_url;
    public String image_url;
    private Double latitude;
    private Double longitude;

    public Restaurant(int per_page, int id, String name, String address, String city, String postal_code, int price, String mobile_reserve_url, String image_url, Double latitude, Double longitude) {
        this.per_page = per_page;
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.price = price;
        this.mobile_reserve_url = mobile_reserve_url;
        this.image_url = image_url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMobile_reserve_url() {
        return mobile_reserve_url;
    }

    public void setMobile_reserve_url(String mobile_reserve_url) {
        this.mobile_reserve_url = mobile_reserve_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
