package tembusu.tvm.tvmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by marvinchin on 3/2/17.
 */

public class RequestHandler {

    private Context c_;

    public RequestHandler(Context c) {
        c_ = c;
    }

    public void sendNewParticipantRequest(String name, String bibNum, String waveNum) {
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();

        AsyncHttpResponseHandler respHandler = new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
                Log.d("Send Request", "SUCCESS");
                Log.d("Send Request", response.toString());
            }

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                Log.d("Send Request", "FAILURE");
                Log.d("Send Request", errorResponse.toString());
            }
        };

        try {
            jsonParams.put(c_.getString(R.string.participant_key_name), name);
            jsonParams.put(c_.getString(R.string.participant_key_bib), bibNum);
            jsonParams.put(c_.getString(R.string.participant_key_wave), waveNum);
        } catch (Exception e) {
            Log.d("NewParticipantRequest", e.getMessage());
        }

        try {
            StringEntity entity = new StringEntity(jsonParams.toString());
            String url = getAbsoluteUrl("participants.json");
            client.post(c_, url, entity, "application/json", respHandler);
        } catch (Exception e) {
            Log.d("NewParticipantRequest", e.getMessage());
        }



    }

    private String getAbsoluteUrl(String endpoint) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c_);
        String serverAddress = sharedPref.getString("Server Address", "");
        String portNum = sharedPref.getString("Port Number", "");
        String baseUrl = serverAddress + ":" + portNum;

        return baseUrl + "/" + endpoint;
    }

}
