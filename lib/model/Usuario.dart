
class Usuario{
  String _nome = "";
  String _email = "";
  String _senha = "";
  String _celular = "";
  String _cpf = "";
  String _rua = "";
  String _numero = "";
  String _bairro = "";
  String _cidade = "";
  String _uf = "";


  String get uf => _uf;

  set uf(String value) {
    _uf = value;
  }

  String get cidade => _cidade;

  set cidade(String value) {
    _cidade = value;
  }

  String get bairro => _bairro;

  set bairro(String value) {
    _bairro = value;
  }

  String get numero => _numero;

  set numero(String value) {
    _numero = value;
  }

  String get rua => _rua;

  set rua(String value) {
    _rua = value;
  }

  String get cpf => _cpf;

  set cpf(String value) {
    _cpf = value;
  }

  String get celular => _celular;

  set celular(String value) {
    _celular = value;
  }

  String get senha => _senha;

  set senha(String value) {
    _senha = value;
  }

  String get email => _email;

  set email(String value) {
    _email = value;
  }

  String get nome => _nome;

  set nome(String value) {
    _nome = value;
  }
}