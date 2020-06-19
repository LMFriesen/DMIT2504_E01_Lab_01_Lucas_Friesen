package ca.nait.dmit2504.oscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ViewReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_jitters);
        // Add a item click event handler to display the login name of the listview item

        // Generate an implementation of the Retrofit interface
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.youcode.ca/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        YoucodeService youcodeService = retrofit.create(YoucodeService.class);

        // Call a method in your service
        Call<String> getCall = youcodeService.listOscarReviews();
        getCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(final Call<String> call, final Response<String> response) {
                String responseBody = response.body();
                // Convert the single string of values into a list of values
                List<Jitter> jittersList = new ArrayList<>();
                String[] jittersArray = responseBody.split("\r\n");
                for (int index = 0; index < jittersArray.length; index += 5) {
                    Jitter currentJitter = new Jitter();
                    String date = jittersArray[index];
                    String reviewer = jittersArray[index + 1];
                    String category = jittersArray[index + 2];
                    String nominee = jittersArray[index + 3];
                    String review = jittersArray[index + 4];

                    currentJitter.setDate(date);
                    currentJitter.setReviewer(reviewer);
                    currentJitter.setCategory(category);
                    currentJitter.setNominee(nominee);
                    currentJitter.setReview(review);
                    jittersList.add(currentJitter);
                }
                ListView jittersListView = findViewById(R.id.activity_custom_list_view_jitters_listview);
                JittersListViewAdapter jitterAdapter = new JittersListViewAdapter(getApplicationContext(), jittersList);
                jittersListView.setAdapter(jitterAdapter);
            }
            @Override
            public void onFailure(final Call<String> call, final Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch jitters was not successful.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
