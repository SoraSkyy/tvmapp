package tembusu.tvm.tvmapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.loopj.android.http.JsonHttpResponseHandler;

public class AddParticipantActivity extends AppCompatActivity {

    EditText name_input;
    EditText bib_input;
    EditText wave_input;
    TextView status_text;

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    RequestHandler r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        bib_input = (EditText)findViewById(R.id.bib_input);
        wave_input = (EditText)findViewById(R.id.wave_input);
        status_text = (TextView)findViewById(R.id.status);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        r = new RequestHandler(this, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
                status_text.setTextColor(Color.GREEN);
                status_text.setText("Successfully added " + bib_input.getText());
                clearFields();
            }

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                String errString;
                try {
                    errString = errorResponse.toString();

                } catch (Exception e) {
                    errString = "No error code";
                }
                status_text.setTextColor(Color.RED);
                status_text.setText("ERROR: Failed to add! - " + errString);
            }
        });
    }

    public void scanQR(View view) {
        Intent intent = new Intent(AddParticipantActivity.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                bib_input.post(new Runnable() {
                    @Override
                    public void run() {
                        bib_input.setText(barcode.displayValue);
                    }
                });
            }
        }
    }

    public void clearFields() {

        bib_input.getText().clear();
        //wave_input.getText().clear();
    }

    public void submit(View view) {
        r.sendNewParticipantRequest(bib_input.getText().toString(), wave_input.getText().toString());
        status_text.setTextColor(Color.BLACK);
        status_text.setText("Request pending...");
    }
}
