package com.example.minhacidade.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.minhacidade.R;
import com.example.minhacidade.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrincipalMunicipeActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_municipe);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipalMunicipe);
        toolbar.setTitle("Home Municipe");
        toolbar.setSubtitle(usuarioAtual.getEmail());
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair :
                deslogarUsuario();
                finish();
                break;

            case R.id.menuConfiguracoes :
                abrirConfiguracoes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirConfiguracoes(){
        Intent intent = new Intent(PrincipalMunicipeActivity.this, ConfiguracaoMunicipeActivity.class);
        startActivity(intent);
    }
}