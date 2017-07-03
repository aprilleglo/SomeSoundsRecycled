package net.aprille.somesoundsrecycled;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.aprille.somesoundsrecycled.models.Sound;

import java.io.File;
import java.io.IOException;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.exceptions.RealmMigrationNeededException;

import static io.realm.Realm.getInstance;

public class SoundActivity extends AppCompatActivity {

    Realm realm;
    RealmConfiguration nRealmConfig;
    RealmRecyclerView nSounds;

    RealmResults<Sound> sounds;

    public  String DirectoryFinal;
    public File BloisUserDir;
    public  File BloisSoundDir;
    public  File BloisDir;
    public String BloisUserDirPath;
    public String BloisSoundDirPath;
    public  String thisSoundImageFilePath;
    public String thisIconThumbFilePath;
    boolean isLandscape;

    RealmConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(getApplicationContext());

        Realm realm;


        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();

        try {
            Log.e("TravellerLog :: ", "normal function coool :)"  );
            realm = Realm.getDefaultInstance();

        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(config);
                //Realm file has been deleted.
                Log.e("TravellerLog :: ", "old realm deleted"  );
                realm = Realm.getDefaultInstance();
            } catch (IllegalStateException fuckYouTooAndroid){
                Realm.init(getApplicationContext());
                Log.e("TravellerLog :: ", "illegal statement exceptions"  );

                Realm.setDefaultConfiguration(config);
                realm = Realm.getDefaultInstance();

            }

        } finally {
            realm = Realm.getDefaultInstance();
        }


//        try {
//            realm = Realm.getDefaultInstance();
//        } catch (IllegalStateException fuckYouTooAndroid) {
//            Realm.init(getApplicationContext());
//            RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//            Realm.setDefaultConfiguration(config);
//            realm = Realm.getDefaultInstance();
//        }

        nSounds = (RealmRecyclerView) findViewById(R.id.user_realm_recycler_view);


        BloisUserDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "BloisData/users");
        BloisSoundDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "BloisData/sounds");
        BloisSoundDirPath = BloisSoundDir.toString();
        BloisDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "BloisData");


        realm.beginTransaction();
//           Sound newSound = realm.createObject(Sound.class, "20140424_Q15");
//                newSound.setSoundName("Q15 les oiseaux de la loire");
//                newSound.setSoundPhoto("20140424_Q15.png");
//                newSound.setSoundLikes(12);
//                newSound.setSoundFile("20140424_Q15.wav");
                RealmResults<Sound> sounds = realm.where(Sound.class).findAll();
        Log.e("TravellerLog :: ", "sounds  " + sounds.size() );

        realm.commitTransaction();
        SoundRecyclerViewAdapter soundAdapter = new SoundRecyclerViewAdapter(getBaseContext(), sounds, true, false);
        nSounds.setAdapter(soundAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   nRealm.deleteAll();
        realm.close();
        realm = null;

    }

    private void loadSounds() {
 //       final RealmResults<Sound> sounds = realm.where(Sound.class).findAll();
        if (sounds != null){
            Log.e("TravellerLog :: ", "sounds exist yeh"  );
        } else {
            Log.e("TravellerLog :: ", "sounds doesn't exist"  );
        }

        SoundRecyclerViewAdapter soundAdapter = new SoundRecyclerViewAdapter(getBaseContext(), sounds,true, false);
        nSounds.setAdapter(soundAdapter);
    }

    public Realm buildDatabase(){
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();

        try {
            Log.e("TravellerLog :: ", "normal function coool :)"  );
            return getInstance(config);

        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(config);
                //Realm file has been deleted.
                Log.e("TravellerLog :: ", "old realm deleted"  );
                return getInstance(config);
            } catch (IllegalStateException fuckYouTooAndroid){
                Realm.init(getApplicationContext());
                Log.e("TravellerLog :: ", "illegal statement exceptions"  );

                Realm.setDefaultConfiguration(config);
                return getInstance(config);

                    //No Realm file to remove.
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface IMyViewHolderClicks {
        public void onLike(View caller);
        public void onPlay(ImageView callerImage);
    }


    public class SoundRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<
                Sound, SoundRecyclerViewAdapter.ViewHolder> {
        Sound thisSound;
        MediaPlayer mediaPlayer; //THIS IS THE CHANGE I DONE
        Uri thisSoundUri;
        String thisSoundFileString;

        public SoundRecyclerViewAdapter(
                Context context,
                RealmResults<Sound> realmResults,
                boolean automaticUpdate,
                boolean animateIdType ) {
            super(context, realmResults, automaticUpdate, animateIdType);
        }

        public class ViewHolder extends RealmViewHolder implements View.OnClickListener {

            private ImageView mImage;
            private ImageView mIconLike;
            private ImageButton mButton_likes;
            private TextView mTitle;
            private TextView mLikes;
            private LinearLayout mLikePress;
            private ImageButton mButton_plays;
            File thisSoundFileNamePath;
            public IMyViewHolderClicks mListener;


            public ViewHolder(LinearLayout container) {
                super(container);

                Log.e("TravellerLog :: ", "ViewHolder "   );
                mImage = (ImageView) container.findViewById(R.id.sound_image);
                this.mTitle = (TextView) container.findViewById(R.id.sound_title);
                mIconLike = (ImageView) container.findViewById(R.id.icon_likes);
                mLikePress = (LinearLayout)  container.findViewById(R.id.likes_press);
                mLikes = (TextView) container.findViewById(R.id.sound_likes);
                this.mButton_plays = (ImageButton) container.findViewById(R.id.button_play);
                mImage.setOnClickListener(this);
                mLikePress.setOnClickListener(this);

            }



            @Override
            public void onClick(View v) {
                if (v instanceof ImageView){
                    Log.e("TravellerLog :: ", "onplay inside onclick " + getAdapterPosition() );
                    thisSound = realmResults.get(getAdapterPosition());

                    thisSoundFileString = BloisSoundDirPath + "/" + thisSound.getSoundFile();

                    thisSoundUri = android.net.Uri.parse(thisSoundFileString);
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    mediaPlayer = new  MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(getContext(), thisSoundUri);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   mediaPlayer.start();

                    Log.e("TravellerLog :: ", "onplay inside onclick " + thisSound.getSoundName()) ;
         //           mListener.onPlay((ImageView)v);
                } else {
                    Log.e("TravellerLog :: ", "onlike inside onclick " + thisSound.getSoundID() );
//                    Sound realmSound = realm.where(Sound.class).equalTo("SoundID", thisSound.getSoundID()).findFirst();
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    thisSound.setSoundLikes(thisSound.getSoundLikes()+1);
                    realm.commitTransaction();
                    Log.e("TravellerLog :: ", "onlike inside onclick " + thisSound.getSoundLikes() );

                }
            }



        }

        // The Viewholder which we inflate here the layout for items in recycleview here note_item

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            Log.e("TravellerLog :: ", "onCreateRealmViewHolder " );
            View v = inflater.inflate(R.layout.grid_item_view, viewGroup, false);

           return new ViewHolder((LinearLayout) v);
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            final Sound sound = realmResults.get(position);

            thisSoundImageFilePath = BloisSoundDirPath + "/" + sound.getSoundPhoto();
              Picasso.with(viewHolder.mImage.getContext())
                    .load(new File(thisSoundImageFilePath))
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.people_placeholder)
                    .into(viewHolder.mImage);

            viewHolder.mTitle.setText(sound.getSoundName());
            thisIconThumbFilePath = BloisDir + "/ic_thump_up.png";
            Picasso.with(viewHolder.mIconLike.getContext())
                    .load(new File(thisIconThumbFilePath))
                    .resize(24, 24)
                    .into(viewHolder.mIconLike);
            viewHolder.mLikes.setText(String.valueOf(sound.getSoundLikes()));
            Picasso.with(viewHolder.mButton_plays.getContext())
                    .load(R.drawable.ic_play_arrow_black_24dp)
                    .into(viewHolder.mButton_plays);

        }
    }



}
