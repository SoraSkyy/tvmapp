package tembusu.tvm.tvmapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class AddParticipantActivity extends AppCompatActivity {

    EditText name_input;
    EditText bib_input;
    EditText wave_input;
    TextView status_text;

    RequestHandler r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        name_input = (EditText)findViewById(R.id.name_input);
        bib_input = (EditText)findViewById(R.id.bib_input);
        wave_input = (EditText)findViewById(R.id.wave_input);
        status_text = (TextView)findViewById(R.id.status);

        r = new RequestHandler(this, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
                status_text.setTextColor(Color.GREEN);
                status_text.setText("Successfully added " + name_input.getText());
                clearFields();
            }

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                String errString = errorResponse.toString();
                status_text.setTextColor(Color.RED);
                status_text.setText("ERROR: Failed to add! - " + errString);
            }
        });
    }

    public void clearFields() {
        name_input.getText().clear();
        bib_input.getText().clear();
        wave_input.getText().clear();
    }

    public void submit(View view) {
        r.sendNewParticipantRequest(name_input.getText().toString(), bib_input.getText().toString(), wave_input.getText().toString());
        status_text.setTextColor(Color.BLACK);
        status_text.setText("Request pending...");
    }
}
