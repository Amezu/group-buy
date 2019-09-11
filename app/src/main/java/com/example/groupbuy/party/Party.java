package com.example.groupbuy.party;


import androidx.annotation.NonNull;
import java.io.Serializable;

public class Party implements Serializable{
    public String partyName;
    public String owner;
    public String id;
    public String ownerId;
    public Party(String partyName, String owner, String id, String ownerId) {
        this.partyName = partyName;
        this.owner = owner;
        this.id = id;
        this.ownerId = ownerId;
    }

    @NonNull
    @Override
    public String toString() {
        return partyName;
    }

}
