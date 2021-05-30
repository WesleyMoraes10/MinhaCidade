package com.example.minhacidade.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){

        if(Build.VERSION.SDK_INT >= 23){
            List<String> listaPermisao = new ArrayList<>();

            //Percorre as permissões passadas, verificando uma a uma, se já tem permissao libera.
            for(String permissao : permissoes){
               Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
               if(!temPermissao)listaPermisao.add(permissao);
            }

            //Caso a lista esteja vazia, não é necessario solicitar permissão
            if(listaPermisao.isEmpty())return true;
            String[] novasPermissoes = new String[listaPermisao.size()];
            listaPermisao.toArray(novasPermissoes);

            //Solicitando permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }

}
