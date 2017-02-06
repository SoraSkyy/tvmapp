package tembusu.tvm.tvmapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String serverAddress = sharedPref.getString("Server Address", "");
        Log.d("TEST", serverAddress);
        String portNum = sharedPref.getString("Port Number", "");
        String baseUrl = serverAddress + ":" + portNum;
        RequestHandler r = new RequestHandler(baseUrl);
        */


    }

    public void editSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void testStuff(View view) {

    }

    public void addParticipant(View view) {
        Intent intent = new Intent(this, AddParticipantActivity.class);
        startActivity(intent);
    }

}
