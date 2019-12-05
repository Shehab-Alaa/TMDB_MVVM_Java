package com.example.moviebase.repositories;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.moviebase.clients.ApiClient;
import com.example.moviebase.clients.ApiService;
import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.Movie;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import okhttp3.internal.http2.ErrorCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository dataRepository;
    private MutableLiveData<ArrayList<Movie>> moviesList;

    public static DataRepository getInstance(){
        if (dataRepository == null){
            dataRepository = new DataRepository();
        }
        return dataRepository;
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesList(Context context , String category , int page){

        moviesList = new MutableLiveData<>();


        final Call< DataResponse > moviesCall = ApiClient.getInstance(context)
                .getApiServices()
                .getMovies(
                        category ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE ,
                        page);

        moviesCall.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                // when data is ready the data will be posted and who is observe will update UI
                if (response.isSuccessful()){
                    moviesList.postValue((ArrayList< Movie >) response.body().getMovies());
                    Log.i("Here" , "Iam Success And Post Data");
                }else{
                    moviesList.postValue(null);
                    Log.i("Here" , "Iam Not Success And Post NULL Data");
                    Log.i("Here" , "");

                    Gson gson = new Gson();
                    MyErrorMessage message=gson.fromJson(response.errorBody().charStream(),MyErrorMessage.class);
                    Log.i("Here" , "Message " + message.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                moviesList.postValue(null);
                Log.i("Here" , "Request Failure");
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });

        Log.i("Here" , "Iam Returned before Posting Data !");
        return moviesList;
    }

}


class MyErrorMessage {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
