package ca.nait.dmit2504.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendJitterActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = SendJitterActivity.class.getSimpleName();
    private EditText mReviewEdit;
    private EditText mNomineeEdit;
    private Button mSubmitButton;
    String category = "film";

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.menu_item_list_jitters:
                Intent listJittersIntent = new Intent(this, MainActivity.class);
                startActivity(listJittersIntent);
                return true;
            case R.id.menu_item_post_jitter:
                Intent postJitterIntent = new Intent(this, SendJitterActivity.class);
                startActivity(postJitterIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton_bestPicture:
                if (checked) {
                    category = "Best Picture";
                }
                break;
            case R.id.radioButton_BestActor:
                if (checked) {
                    category = "Best Actor";
                }
                break;
            case R.id.radioButton_bestActress:
                if (checked) {
                    category = "Best Actress";
                }
                break;
            case R.id.radioButton_bestEditing:
                if (checked) {
                    category = "Film Editing";
                }
                break;
            case R.id.radioButton_bestEffects:
                if (checked) {
                    category = "Visual Effects";
                }
                break;
        }
        return;
    }


    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username_pref", "Lucas Friesen");
        String password = prefs.getString("password_pref", "oscar275");
        String url = prefs.getString("url_pref", "http://www.youcode.ca/Lab01Servlet");
        String colorName = prefs.getString("backgroundColor_pref", "#678db5");
        ConstraintLayout layout = findViewById(R.id.linearLayout_activity_main);
        layout.setBackgroundColor(Color.parseColor(colorName));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jitters_list_view);

        // Find all the views in the layout
        mReviewEdit = findViewById(R.id.editText_review);
        mNomineeEdit = findViewById(R.id.editText_nomineeName);
        mSubmitButton = findViewById(R.id.button_submit);

        mSubmitButton.setOnClickListener((View view) -> {
            // Generate an implementation of the Retrofit interface
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.youcode.ca")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            YoucodeService youcodeService = retrofit.create(YoucodeService.class);


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String reviewer = prefs.getString("username_pref", "Lucas Friesen");
            String password = prefs.getString("password_pref", "oscar275");
            String review = mReviewEdit.getText().toString();
            String nominee = mNomineeEdit.getText().toString();

            Call<String> getCall = youcodeService.postReview(review, reviewer, nominee, category, password);
            getCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(final Call<String> call, final Response<String> response) {
                    Log.i(TAG,"Post was successful");
                    // Create all EditText fields
                    mNomineeEdit.setText("");
                    mReviewEdit.setText("");
                    Toast.makeText(SendJitterActivity.this, "Post was successful", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(final Call<String> call, final Throwable t) {
                    Log.e(TAG, "Post was not successful");
                }
            });
        });



    }
}
