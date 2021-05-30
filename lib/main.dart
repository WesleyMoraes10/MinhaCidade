import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:minhacidade/telas/TelaLogin.dart';
import 'package:minhacidade/telas/TelaMunicipe.dart';

void main() {
  /*
  WidgetsFlutterBinding.ensureInitialized();

  Firestore.instance
    .collection("usuario")
    .document("001")
    .setData({"nome":"wesley"});*/

  runApp(MaterialApp(
    home: TelaLogin(),
    theme: ThemeData(
      primaryColor: Color(0xff000a12),
      accentColor: Color(0xff4f5b62)
    ),
    debugShowCheckedModeBanner: false,
  ));

}

