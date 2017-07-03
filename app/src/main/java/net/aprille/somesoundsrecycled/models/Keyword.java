package net.aprille.somesoundsrecycled.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by aprillebestglover on 7/2/17.
 */

@RealmClass
public class Keyword extends RealmObject {


    @PrimaryKey
    private String keyID;

    private String keyName;

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }


}
