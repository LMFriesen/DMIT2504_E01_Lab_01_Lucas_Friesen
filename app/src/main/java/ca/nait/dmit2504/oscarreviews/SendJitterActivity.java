package ca.nait.dmit2504.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendJitterActivity extends AppCompatActivity {
    private static final String TAG = SendJitterActivity.class.getSimpleName();
    private EditText mReviewEdit;
    private EditText mNomineeEdit;
    private RadioButton mCategoryButton;
    private Button mSubmitButton;


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all the views in the layout
        mReviewEdit = findViewById(R.id.editText_review);
        mNomineeEdit = findViewById(R.id.editText_nomineeName);
        mCategoryButton = findViewById(R.id.radioButton_bestPicture);

        mSubmitButton = findViewById(R.id.button_submit);

        // Add a click event handler to the submit button using a Java 8 lambda expression
//        mSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//            }
//        });
//        mSubmitButton.setOnClickListener((View view) -> {
//
//        });

        mSubmitButton.setOnClickListener((View view) -> {
            // Generate an implementation of the Retrofit interface
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.youcode.ca")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            YoucodeService youcodeService = retrofit.create(YoucodeService.class);

            // Call a method in your service
            String review = mReviewEdit.getText().toString();
            String reviewer = "Lucas Friesen";
            String nominee = mNomineeEdit.getText().toString();
            String category = mCategoryButton.getText().toString();
            String password = "oscar275";

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
