package com.example.addressmanager;

public class Address {
    private String name = "";
    private String address = "";
    private String type = "";
    private String zip = "";

    public Address() {
    }

    public Address(String name, String address, String type, String zip) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.zip = zip;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


}
