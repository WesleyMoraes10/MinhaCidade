package com.example.minhacidade.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhacidade.R;
import com.example.minhacidade.config.ConfiguracaoFirebase;
import com.example.minhacidade.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView txtAbreCadastro;
    private EditText editEmail;
    private EditText editSenha;
    private Button btEntrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        txtAbreCadastro = (TextView)findViewById(R.id.txtAbreCadastro);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btEntrar = (Button) findViewById(R.id.btEntrar);

        txtAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroMunicipeActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperando dados
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                //validar se e-mail e senha foram digitados
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        Usuario usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        logarUsuario(usuario);
                    }else{
                        Mensagem("Preencha a senha!");
                    }
                }else{
                    Mensagem("Preencha o e-mail!");
                }
            }
        });


    }

    public void logarUsuario(Usuario usuario){
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    chamaTelaPrincipal();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha não correnpondem a um usuário cadastrado!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Mensagem(excecao);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual != null){
            chamaTelaPrincipal();
        }
    }

    public void chamaTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, PrincipalMunicipeActivity.class);
        startActivity(intent);
    }

    private void Mensagem(String mensagem){
        Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_LONG).show();
    }
}