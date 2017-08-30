package com.example.ShoongCart;

/**
 * Created by choeyujin on 2017. 8. 25..
 */

public class PostData {

    String name;    //이름 저장
    String nation;   //국적 저장
    int imgId;      //국기 이미지의 리소스 아이디

    public PostData(String name, String nation, int imgId) {
        this.name= name;
        this.nation=nation;
        this.imgId=imgId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public String getNation() {
        return nation;
    }

    public int getImgId() {
        return imgId;
    }
}