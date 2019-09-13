package com.example.groupbuy.groups;

import java.io.Serializable;

public class Group implements Serializable {
    public Group(String name, String groupId, String owner) {
        this.name = name;
        this.groupId = groupId;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;
    private String groupId;
    private String owner;
}
