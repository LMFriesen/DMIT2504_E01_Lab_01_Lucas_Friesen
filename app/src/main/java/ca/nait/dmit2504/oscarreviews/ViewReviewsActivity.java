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
                /*for (String jitterText : jittersArray) {
                    Jitter currentJitter = new Jitter();
                    String date = jitterText.substring(0, jitterText.indexOf(" *-*-* "));
                    String reviewer = jitterText.substring(1, jitterText.indexOf("\n"));
                    String category = jitterText.substring(2, jitterText.indexOf("\n"));
                    String nominee = jitterText.substring(3, jitterText.indexOf("\n"));
                    String review = jitterText.substring(4, jitterText.indexOf("\n"));
                    currentJitter.setDate(date);
                    currentJitter.setReviewer(reviewer);
                    currentJitter.setCategory(category);
                    currentJitter.setNominee(nominee);
                    currentJitter.setReview(review);
                    // add currentJitter to jittersList
                    jittersList.add(currentJitter);
                }*/
                try {
                    BufferedReader reader = new BufferedReader(new StringReader(responseBody));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Jitter currentJitter = new Jitter();
                        String date = line;
                        String reviewer = reader.readLine();
                        String category = reader.readLine();
                        String nominee = reader.readLine();
                        String review = reader.readLine();
                        currentJitter.setDate(date);
                        currentJitter.setReviewer(reviewer);
                        currentJitter.setCategory(category);
                        currentJitter.setNominee(nominee);
                        currentJitter.setReview(review);
                        // add currentJitter to jittersList
                        jittersList.add(currentJitter);
                    }


                    // Find the ListView and set the adapter for the listview
                    ListView jittersListView = findViewById(R.id.activity_custom_list_view_jitters_listview);
                    JittersListViewAdapter jitterAdapter = new JittersListViewAdapter(getApplicationContext(), jittersList);
                    jittersListView.setAdapter(jitterAdapter);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fetch jitters was not successful.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(final Call<String> call, final Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch jitters was not successful.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
