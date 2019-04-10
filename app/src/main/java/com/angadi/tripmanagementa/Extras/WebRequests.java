package com.angadi.tripmanagementa.Extras;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebRequests
{
    @POST(Constants.Signup)
    Call<JsonObject> signup (@Body JsonObject task);

}
