package com.smarikatripathi.healthmonitoringsystem.classes;


public class User {
    private Integer id;
    private String name;
    private String address;
    private int age;

    public User(Integer id, String name, String address, int age)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public Integer getId() { return id; }

    public String getName()
    {
        return name;
    }

    public String getAddress(){ return address; }

    public int getAge()
    {
        return age;
    }

}
