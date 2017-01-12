package dbm.zoigl_kalender;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.TimeZone;

import adapter.ImplPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;



public class LauncherActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        verifyInstallation();
        ImplPagerAdapter pagerAdapter = new ImplPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //This app works with UTC Times due to synchronisation with DynamoDB
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }



    /**
     * Method that creates a UUID the first time the application is started.
     */
    public void verifyInstallation(){
        boolean uuid_exists;
        SharedPreferences sharedPreferences = getSharedPreferences("InstallSettings", MODE_PRIVATE);
        uuid_exists = sharedPreferences.getBoolean("UUID_Exists", false);

        //Create UUID if it doesn't exist yet
        if (!uuid_exists) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String uuid = java.util.UUID.randomUUID().toString();
            editor.putString("UUID", uuid);
            editor.putBoolean("UUID_Exists", true);
            editor.apply();
        }
    }


}
