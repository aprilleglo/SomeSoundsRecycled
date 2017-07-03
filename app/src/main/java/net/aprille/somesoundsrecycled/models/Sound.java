package net.aprille.somesoundsrecycled.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by aprillebestglover on 7/2/17.
 */

@RealmClass
public class Sound extends RealmObject {

    @PrimaryKey
    private String soundID;

    @Index
    private String soundName;


//    private String soundDesc;

    private String soundFile;

    private String soundPhoto;

//    private String soundPhotoDesc;

//    public String timeCreated;

    @Index
    private int soundLikes = 10;

//    private RealmList<User> soundUser;

//    private Quadrant soundQuad;

//    private RealmList<Keyword> keywords;

    // getters and setters.....


    public String getSoundID() {
        return soundID;
    }

    public void setSoundID(String soundID) {
        this.soundID = soundID;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }


    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getSoundPhoto() {
        return soundPhoto;
    }

    public void setSoundPhoto(String soundPhoto)  {
        this.soundPhoto = soundPhoto;
    }


    public int getSoundLikes() {
        return soundLikes;
    }

    public void setSoundLikes(int soundLikes) {
        this.soundLikes = soundLikes;
    }


}
