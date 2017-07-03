package net.aprille.somesoundsrecycled.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by aprillebestglover on 7/2/17.
 */

@RealmClass
public class User extends RealmObject {

    @PrimaryKey
    private String userID;

    @Index
    private String userName;

    private String userDesc;

    private String userPhoto;

    private String userPhotoDesc;

    private String timeJoined;

    private RealmList<Sound> userSounds;

    // getters and setters.....

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getUserPhotoDesc() {
        return userPhotoDesc;
    }

    public void setUserPhotoDesc(String userPhotoDesc) {
        this.userPhotoDesc = userPhotoDesc;
    }

    public String getTimeJoined() {
        return timeJoined;
    }

    public void setTimeJoined(String timeJoined) { this.timeJoined = timeJoined; }

    public RealmList<Sound> getUserSounds() { return userSounds; }

    public void setUserSounds(RealmList<Sound> userSounds) {
        this.userSounds = userSounds;
    }

}
