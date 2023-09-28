package br.com.viacep.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;

import br.com.viacep.Model.Address;
import br.com.viacep.Network.ApiClient;
import br.com.viacep.R;
import retrofit2.Call;
import retrofit2.Response;

public class SearchEndereco extends AppCompatActivity {

    private MaskEditText mMaskEditTextCep;
    private AppCompatButton mButtonSearch, mButtonSave;
    private AppCompatTextView mTextViewCep, mTextViewLogradouro, mTextViewComplemento, mTextViewBairro,
            mTextViewLocalidade, mTextViewUf, mTextViewDDD;
    private LinearLayoutCompat mLayoutCompatAdressDados;
//    private ProgressBar mProgressBarSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_endereco);

        initComponentes();

        mButtonSearch.setOnClickListener(view -> getAddress(mMaskEditTextCep.getUnMasked()));

    }

    private void initComponentes(){

        mMaskEditTextCep = findViewById(R.id.editText_inputAddress);
        mButtonSearch = findViewById(R.id.button_searchAddress);
        mButtonSave = findViewById(R.id.button_saveAddress);
        mTextViewCep = findViewById(R.id.cepDataAddress);
        mTextViewLogradouro = findViewById(R.id.logradouroDataAddress);
        mTextViewComplemento = findViewById(R.id.complementoDataAddress);
        mTextViewBairro = findViewById(R.id.bairroDataAddress);
        mTextViewLocalidade = findViewById(R.id.localidadeDataAddress);
        mTextViewUf = findViewById(R.id.ufDataAddress);
        mTextViewDDD = findViewById(R.id.dddDataAddress);
        mLayoutCompatAdressDados = findViewById(R.id.showAdress_layout);
//        mProgressBarSearch = findViewById(R.id.progressBar_searchAddress);

    }

    private void getAddress (String cep){
        if (cep.length() < 8){
            Toast.makeText(this, "Digite um CEP valido", Toast.LENGTH_SHORT).show();
        } else {
            new SearchAddress().execute(cep);
        }
    }

    private class SearchAddress extends AsyncTask<String, Void, Address> {

        @Override
        protected Address doInBackground(String... parans) {

            String cep = parans[0];

                Address address;
                Call<Address> call = ApiClient.getClient().getAddress(cep);

                try {
                    Response<Address> response = call.execute();
                    if (response.isSuccessful()){
                        address = response.body();
                        if (address != null && address.getErro() == null) {
                            return address;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            return null;
        }

        @Override
        protected void onPostExecute(Address address) {

            if (address != null) {
                showTableAddress();

                mTextViewCep.setText(address.getCep());
                mTextViewLogradouro.setText(address.getLogradouro());
                mTextViewComplemento.setText(address.getErro());
                mTextViewBairro.setText(address.getBairro());
                mTextViewLocalidade.setText(address.getLocalidade());
                mTextViewUf.setText(address.getUf());
                mTextViewDDD.setText(address.getDdd());
            } else {
                Toast.makeText(SearchEndereco.this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showTableAddress(){

        int tableAddress = mLayoutCompatAdressDados.getVisibility();

        if (tableAddress == 0) {
            mLayoutCompatAdressDados.setVisibility(View.GONE);
        } else {
            mLayoutCompatAdressDados.setVisibility(View.VISIBLE);
        }

    }

//    private void getAddress(String cep){
//        if(cep.length() < 8){
//            Toast.makeText(this, "Digite um cep valido", Toast.LENGTH_SHORT).show();
//        } else {
//
//            Call<Address> call = ApiClient.getClient().getAddress(cep);
//            call.enqueue(new Callback<Address>() {
//                @Override
//                public void onResponse(Call<Address> call, Response<Address> response) {
//
//                    Address responceBody = response.body();
//
//                    if(responceBody != null && response.body().getErro() == null){
//                        showTableAddress();
//
//                        mTextViewCep.setText(responceBody.getCep());
//                        mTextViewLogradouro.setText(responceBody.getLogradouro());
//                        mTextViewComplemento.setText(responceBody.getErro());
//                        mTextViewBairro.setText(responceBody.getBairro());
//                        mTextViewLocalidade.setText(responceBody.getLocalidade());
//                        mTextViewUf.setText(responceBody.getUf());
//                        mTextViewDDD.setText(responceBody.getDdd());
//
//                    } else {
//                        Toast.makeText(SearchEndereco.this, "Endereço não encontrado, Verifique seu CEP", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<Address> call, Throwable t) {
//                    Toast.makeText(SearchEndereco.this, "erro", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

}