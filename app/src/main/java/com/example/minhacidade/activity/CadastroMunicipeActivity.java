package com.example.minhacidade.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhacidade.R;
import com.example.minhacidade.config.ConfiguracaoFirebase;
import com.example.minhacidade.helper.Base64Custom;
import com.example.minhacidade.helper.UsuarioFibase;
import com.example.minhacidade.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroMunicipeActivity extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editCelular, editCpf, editRua, editNumero, editBairro, editCidade, editUf;
    private Button btCadastrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_municipe);

        //Adiciona o botão <- Up Navigation
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNome = (EditText)findViewById(R.id.editNome);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editSenha = (EditText)findViewById(R.id.editSenha);
        editCelular = (EditText)findViewById(R.id.editCelular);
        editCpf = (EditText)findViewById(R.id.editCpf);
        editRua = (EditText)findViewById(R.id.editRua);
        editNumero = (EditText)findViewById(R.id.editNumero);
        editBairro = (EditText)findViewById(R.id.editBairro);
        editCidade = (EditText)findViewById(R.id.editCidade);
        editUf = (EditText)findViewById(R.id.editUf);
        btCadastrar = (Button)findViewById(R.id.btCadastrar);


        //Metodo cadastrar
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recupenando textos
                String nome = editNome.getText().toString();
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String celular = editCelular.getText().toString();
                String cpf = editCpf.getText().toString();
                String rua = editRua.getText().toString();
                String numero = editNumero.getText().toString();
                String bairro = editBairro.getText().toString();
                String cidade = editCidade.getText().toString();
                String uf = editUf.getText().toString();

                //Consistencia de campos
                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){
                            if(!celular.isEmpty()){
                                if(!cpf.isEmpty()){
                                    if(!rua.isEmpty()){
                                        if(!numero.isEmpty()){
                                            if(!bairro.isEmpty()){
                                                if(!cidade.isEmpty()){
                                                    if(!uf.isEmpty()){

                                                        Usuario usuario = new Usuario();
                                                        usuario.setNome(nome);
                                                        usuario.setEmail(email);
                                                        usuario.setSenha(senha);
                                                        usuario.setCelular(celular);
                                                        usuario.setCpf(cpf);
                                                        usuario.setRua(rua);
                                                        usuario.setNumero(numero);
                                                        usuario.setBairro(bairro);
                                                        usuario.setCidade(cidade);
                                                        usuario.setUp(uf);

                                                        cadastrarUsuarioFirebase(usuario);

                                                    }else{
                                                        Mensagem("Preencha o uf!");
                                                    }
                                                }else{
                                                    Mensagem("Preencha a cidade!");
                                                }
                                            }else{
                                                Mensagem("Preencha o bairro!");
                                            }
                                        }else{
                                            Mensagem("Preencha o numero!");
                                        }
                                    }else{
                                        Mensagem("Preencha a rua!");
                                    }
                                }else{
                                    Mensagem("Preencha o cpf!");
                                }
                            }else{
                                Mensagem("Preencha o celular!");
                            }
                        }else{
                            Mensagem("Preencha a senha!");
                        }
                    }else{
                        Mensagem("Preencha o e-mail!");
                    }
                }else{
                    Mensagem("Preencha o nome!");
                }
            }
        });


    }

    public void cadastrarUsuarioFirebase(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
            usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Mensagem("Sucesso ao cadastrar usuário!");
                    finish();

                    try {
                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId(identificadorUsuario);
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                        Mensagem("Erro ");
                    }

                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
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

    private void Mensagem(String mensagem){
        Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_LONG).show();
    }


}