package net.goutalk.fowit.Bean;

public class PhoneDto {
    private String name;        //联系人姓名
    private String telPhone;    //电话号码

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    private boolean choose=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public PhoneDto() {
    }

    public PhoneDto(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }
}
