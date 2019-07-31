package com.example.book_inbeta1;

public class MeetingRoom {

    private long id;
    private String name;
    private long floor;
    private String equipment;

    public MeetingRoom(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MeetingRoom(long id, String name, long floor) {
        this.id = id;
        this.name = name;
        this.floor = floor;
    }

    public MeetingRoom(long id, String name, long floor,String equipment) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.equipment = equipment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
