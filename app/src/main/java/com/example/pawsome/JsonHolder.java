package com.example.pawsome;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonHolder {
    @Headers("x-api-key:57c81164-d644-493e-86dd-429f3ac90def")
    @GET("breeds")
    Call<List<Breed>> getBreed();

    @Headers("x-api-key:57c81164-d644-493e-86dd-429f3ac90def")
    @Multipart
    @POST("images/upload")
    Call<Image_Post> createImage(@Part MultipartBody.Part file, @Part("sub_id")RequestBody sub_id);

    @Headers("x-api-key:57c81164-d644-493e-86dd-429f3ac90def")
    @GET("images")
    Call<List<Uploaded_image_id>>getimageid();

    @Headers("x-api-key:57c81164-d644-493e-86dd-429f3ac90def")
    @GET("images/{image_id}/analysis")
    Call<List<Image_analysis>> getanalysis(@Path("image_id") String image_id);

    @Headers("x-api-key:57c81164-d644-493e-86dd-429f3ac90def")
    @DELETE("images/{image_id}")
    Call<Response> delete_id(@Path("image_id") String image_id);

}