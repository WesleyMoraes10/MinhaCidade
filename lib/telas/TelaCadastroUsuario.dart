import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:minhacidade/model/Usuario.dart';
import 'package:minhacidade/telas/TelaLogin.dart';
import 'package:minhacidade/telas/TelaMunicipe.dart';

class TelaCadastroUsuario extends StatefulWidget {
  @override
  _TelaCadastroUsuarioState createState() => _TelaCadastroUsuarioState();
}

class _TelaCadastroUsuarioState extends State<TelaCadastroUsuario> {

  //Controladores
  TextEditingController _controllerNome = TextEditingController();
  TextEditingController _controllerSenha = TextEditingController();
  TextEditingController _controllerEmail = TextEditingController();
  TextEditingController _controllerCelular = TextEditingController();
  TextEditingController _controllerCpf = TextEditingController();
  TextEditingController _controllerRua = TextEditingController();
  TextEditingController _controllerNumero = TextEditingController();
  TextEditingController _controllerBairro = TextEditingController();
  TextEditingController _controllerCidade = TextEditingController();
  TextEditingController _controllerUf = TextEditingController();
  String _mensagemErro = "";

  validarCampos(){
    //Recuperar os dados
    String nome = _controllerNome.text;
    String email = _controllerEmail.text;
    String senha = _controllerSenha.text;
    String celular = _controllerCelular.text;
    String cpf = _controllerCpf.text;
    String rua = _controllerRua.text;
    String numero = _controllerNumero.text;
    String bairro = _controllerBairro.text;
    String cidade = _controllerCidade.text;
    String uf = _controllerUf.text;

    if( nome.isNotEmpty ){
      if( email.isNotEmpty ){
        if( senha.isNotEmpty && senha.length >=6){
          if( celular.isNotEmpty ){
            if( cpf.isNotEmpty ){
              if( rua.isNotEmpty ){
                if( numero.isNotEmpty ){
                  if( bairro.isNotEmpty ){
                    if( cidade.isNotEmpty ){
                      if( uf.isNotEmpty ){
                        setState(() {
                          _mensagemErro = "";

                          Usuario usuario = Usuario();
                          usuario.nome = nome;
                          usuario.senha = senha;
                          usuario.email = email;
                          usuario.celular = celular;
                          usuario.cpf = cpf;
                          usuario.rua = rua;
                          usuario.numero = numero;
                          usuario.bairro = bairro;
                          usuario.cidade = cidade;
                          usuario.uf = uf;

                          _cadastrarUsuario(usuario);
                        });
                      }else{
                        setState(() {
                          _mensagemErro = "*Preencha o UF!";
                        });
                      }
                    }else{
                      setState(() {
                        _mensagemErro = "*Preencha a cidade!";
                      });
                    }
                  }else{
                    setState(() {
                      _mensagemErro = "*Preencha o bairro!";
                    });
                  }
                }else{
                  setState(() {
                    _mensagemErro = "*Preencha o numero!";
                  });
                }
              }else{
                setState(() {
                  _mensagemErro = "*Preencha a rua!";
                });
              }
            }else{
              setState(() {
                _mensagemErro = "*Preencha o CPF!";
              });
            }
          }else{
            setState(() {
              _mensagemErro = "*Preencha o celular!";
            });
          }
        }else{
          setState(() {
            _mensagemErro = "*Preencha senha!";
          });
        }
      }else{
        setState(() {
          _mensagemErro = "*Preencha o E-mail utilizando @!";
        });
      }
    }else{
      setState(() {
        _mensagemErro = "*Preencha o nome!";
      });
    }

  }

  _cadastrarUsuario(Usuario usuario){

    FirebaseAuth auth = FirebaseAuth.instance;

    auth.createUserWithEmailAndPassword(
        email: usuario.email,
        password: usuario.senha
    ).then((firebaseUser){

      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => TelaMunicipe()
          ),
      );

      _limparCampos();

    }).catchError((error){
      print("ERRO AO CADASTRAR: "+error.toString()+usuario.nome+" "+usuario.email+" "+usuario.senha);
      setState(() {
        _mensagemErro = "Erro ao cadastrar";
      });
    });

  }

  _limparCampos(){

    _controllerNome.clear();

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Cadastro de Usuário"),
      ),
      body: Container(
        decoration: BoxDecoration(color: Color(0xff000a12)),
        padding: EdgeInsets.all(16),
        child: Center(
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.only(bottom: 32),
                  child: Image.asset(
                    "imagens/usuario.png",
                    width: 100,
                    height: 80,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    //autofocus: true,
                    controller: _controllerNome,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Nome",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    //autofocus: true,
                    controller: _controllerEmail,
                    keyboardType: TextInputType.emailAddress,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "E-mail",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerSenha,
                    obscureText: true,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Senha",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerCelular,
                    keyboardType: TextInputType.number,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Celular",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerCpf,
                    keyboardType: TextInputType.number,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "CPF",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerRua,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Rua",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerNumero,
                    keyboardType: TextInputType.number,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "N.°",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerBairro,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Bairro",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerCidade,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "Cidade",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 8),
                  child: TextField(
                    controller: _controllerUf,
                    keyboardType: TextInputType.text,
                    style: TextStyle(fontSize: 20),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      hintText: "UF",
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(top: 16, bottom: 10),
                  child: RaisedButton(
                      child: Text(
                        "Cadastrar",
                        style: TextStyle(
                          color: Colors.white, fontSize: 20,
                        ),
                      ),
                      color: Colors.blue,
                      padding: EdgeInsets.fromLTRB(32, 16, 32, 16),
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(32)
                      ),
                      onPressed: (){
                        validarCampos();
                      }
                  ),
                ),
                Center(
                  child: Text(
                    _mensagemErro,
                    style: TextStyle(
                        color: Colors.red,
                        fontSize: 20
                    ),
                  ),
                ),
                ],
            ),
          ),
        ),
      ),
    );
  }
}
