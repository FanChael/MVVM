package com.hl.modules_login.model.bean;

import com.hl.anotation.NotProguard;

@NotProguard
public class UserBean {

    /**
     * admin : false
     * chapterTops : []
     * collectIds : []
     * email :
     * icon :
     * id : 56087
     * nickname : 小坑神周杰伦
     * password :
     * publicName : 小坑神周杰伦
     * token :
     * type : 0
     * username : 小坑神周杰伦
     */

    private int id;
    private String nickname;
    private String publicName;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
