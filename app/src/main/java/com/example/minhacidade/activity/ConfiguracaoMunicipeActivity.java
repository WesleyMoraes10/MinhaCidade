package com.example.minhacidade.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.minhacidade.R;
import com.example.minhacidade.config.ConfiguracaoFirebase;
import com.example.minhacidade.helper.Base64Custom;
import com.example.minhacidade.helper.Permissao;
import com.example.minhacidade.helper.UsuarioFibase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfiguracaoMunicipeActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private String [] permissoesNecessarias = new String []{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };

    private ImageButton imgCamera, imgGaleria;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView fotoPertil;
    private StorageReference storageReference;
    private String identificadorUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_municipe);

        //Configurações iniciais
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        identificadorUsuario = UsuarioFibase.getIndentificadorUsuario();

        imgCamera = (ImageButton)findViewById(R.id.imgCamera);
        imgGaleria = (ImageButton)findViewById(R.id.imgGaleria);
        fotoPertil = (CircleImageView)findViewById(R.id.fotoPerfil);

        //Validar permissão
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipalMunicipe);
        toolbar.setTitle("Configurações Perfil");
        toolbar.setSubtitle(usuarioAtual.getEmail());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_CAMERA);
                }


            }
        });

        imgGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        //Recuperar dados dos usuários
        FirebaseUser usuario = UsuarioFibase.getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        if(url != null){
            Glide.with(ConfiguracaoMunicipeActivity.this)
                    .load(url)
                    .into(fotoPertil);
        }else{
            fotoPertil.setImageResource(R.drawable.padrao);
        }

    }

    //recupera imagem
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if(requestCode != RESULT_OK){
                Bitmap imagem = null;

                try {

                    switch (requestCode){
                        case SELECAO_CAMERA:
                            imagem = (Bitmap) data.getExtras().get("data");
                            break;

                        case  SELECAO_GALERIA:
                            Uri localImagemSelecionada = data.getData();
                            imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                            break;
                    }

                    if(imagem != null){
                        fotoPertil.setImageBitmap(imagem);

                        //Recuperar dados da imagem para o firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                        byte[] dadosImagem = baos.toByteArray();

                        //Salvado imagem no Firebase
                        StorageReference imagemRef = storageReference
                                .child("imagens")
                                .child("perfil")
                                //.child(identificadorUsuario)
                                .child(identificadorUsuario+"perfil.jpg");

                        UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Mensagem("Erro ao fazer upload da imagem!");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Mensagem("Sucesso fazer upload da imagem");

                                Uri url = taskSnapshot.getDownloadUrl();
                                atualizaFotoUsuario(url);

                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }


    }

    public void atualizaFotoUsuario(Uri url){

        UsuarioFibase.atualizarFotoUsuario(url);

    }

    //verifica permissão nao validada
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults ){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                aletarValidacaoPermissao();
            }
        }

    }

    private void aletarValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necssário acertar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void Mensagem(String mensagem){
        Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_LONG).show();
    }
}