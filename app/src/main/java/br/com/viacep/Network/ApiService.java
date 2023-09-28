package br.com.viacep.Network;

import br.com.viacep.Model.Address;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("{cep}/json/")
    Call<Address> getAddress(@Path("cep") String cep);

}
