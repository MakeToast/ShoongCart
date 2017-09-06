package com.example.ShoongCart;

/**
 * Created by choeyujin on 2017. 8. 25..
 */

public class PostData {

    String name;    //이름 저장
    long price;
    double latitude;
    double longitude;


    public PostData(String name, long price, double latitude, double longitude)
    {
        this.name = name;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(long price)
    {
        this.price = price;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}