package br.com.viacep.Network;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiClient {

    private static ApiService INSTANCE;

    public static ApiService getClient (){

        if(INSTANCE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://viacep.com.br/ws/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(ApiService   .class);

        }
        return INSTANCE;
    }

}
