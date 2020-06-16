package ca.nait.dmit2504.oscarreviews;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface YoucodeService {
    @GET("Lab01Servlet")
    Call<String> listOscarReviews();

    @FormUrlEncoded
    @POST("Lab01Servlet")
    Call<String> postReview(@Field("REVIEW")String review, @Field("REVIEWER")String reviewer, @Field("NOMINEE")String nominee, @Field("CATEGORY")String category, @Field("PASSWORD")String password);
}
