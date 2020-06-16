package ca.nait.dmit2504.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();   // simpleName = "MainActivity"
    public String category = "film";
    private ListView mOscarListView;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        // Get the menu inflater
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.menu_item_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.menu_item_list_jitters) {
            Intent intent = new Intent(this, ViewReviewsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jitters_list_view);

        //find the textview in the layout
        mOscarListView = findViewById(R.id.jittersListView);

        //Generate an implementation of the Retrofit interface
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.youcode.ca/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        YoucodeService youcodeService = retrofit.create(YoucodeService.class);

        //Call a method in your service
        Call<String> getCall = youcodeService.listOscarReviews();
        getCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(final Call<String> call, final Response<String> response) {
                Log.i(TAG, "Success getting data");
                String responseBodyText = response.body();
                String[] responseArray = responseBodyText.split("\r\n");

                ArrayAdapter<String> jittersAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, responseArray);
                mOscarListView.setAdapter(jittersAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Failure to get data");
            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username_pref", "Lucas Friesen");
        String password = prefs.getString("password_pref", "oscar275");
        String url = prefs.getString("url_pref", "http://www.youcode.ca/Lab01Servlet");
        String colorName = prefs.getString("backgroundColor_pref", "#678db5");
        ConstraintLayout layout = findViewById(R.id.linearLayout_activity_main);
        layout.setBackgroundColor(Color.parseColor(colorName));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton_bestPicture:
                if (checked) {
                    category = "film";
                }
                break;
            case R.id.radioButton_BestActor:
                if (checked) {
                    category = "actor";
                }
                break;
            case R.id.radioButton_bestActress:
                if (checked) {
                    category = "actress";
                }
                break;
            case R.id.radioButton_bestEditing:
                if (checked) {
                    category = "editing";
                }
                break;
            case R.id.radioButton_bestEffects:
                if (checked) {
                    category = "effects";
                }
                break;
        }
        return;
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
}
