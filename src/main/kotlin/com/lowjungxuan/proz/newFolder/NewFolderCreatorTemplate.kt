package com.lowjungxuan.proz.newFolder

import com.lowjungxuan.proz.utils.toCamelCase
import com.lowjungxuan.proz.utils.toSnakeCase

object NewFolderCreatorTemplate {
    fun getXPage(name: String): String = """import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '${name.toSnakeCase()}_controller.dart';

class ${name.toCamelCase()}Page extends GetView<${name.toCamelCase()}Controller> {
  const ${name.toCamelCase()}Page({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('${name.toCamelCase()} Page'),
      ),
    );
  }
}"""

    fun getXController(name: String) = """import 'package:get/get.dart';

class ${name.toCamelCase()}Controller extends GetxController {
  final ${name.toCamelCase()}State state = ${name.toCamelCase()}State();
  
  @override
  void onInit() {
    // Ideal place to initialize your variables or start API calls.
    super.onInit();
  }

  @override
  void onReady() {
    // Run anything that needs to happen after all initializations are done.
    super.onReady();
  }

  @override
  void onClose() {
    // Clean up resources like listeners or streams.
    super.onClose();
  }
}

class ${name.toCamelCase()}Binding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut(() => ${name.toCamelCase()}Controller());
  }
}

class ${name.toCamelCase()}State {
  ${name.toCamelCase()}State() {
    ///Initialize variables
  }
}"""

    fun bloc(name: String): String = """import 'package:bloc/bloc.dart';

import '${name.toSnakeCase()}_event.dart';
import '${name.toSnakeCase()}_state.dart';

class ${name.toCamelCase()}Bloc extends Bloc<${name.toCamelCase()}Event, ${name.toCamelCase()}State> {
  ${name.toCamelCase()}Bloc() : super(${name.toCamelCase()}State().init()) {
    on<InitEvent>(_init);
  }

  void _init(InitEvent event, Emitter<${name.toCamelCase()}State> emit) async {
    emit(state.clone());
  }
}"""
    fun blocEvent(name: String):String = """abstract class ${name.toCamelCase()}Event {}

class InitEvent extends ${name.toCamelCase()}Event {}"""

    fun blocState(name: String):String = """class ${name.toCamelCase()}State {
  ${name.toCamelCase()}State init() {
    return ${name.toCamelCase()}State();
  }

  ${name.toCamelCase()}State clone() {
    return ${name.toCamelCase()}State();
  }
}"""
    fun blocView(name: String) = """import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '${name.toSnakeCase()}_bloc.dart';
import '${name.toSnakeCase()}_event.dart';
import '${name.toSnakeCase()}_state.dart';

class ${name.toCamelCase()}Page extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (BuildContext context) => ${name.toCamelCase()}Bloc()..add(InitEvent()),
      child: Builder(builder: (context) => _buildPage(context)),
    );
  }

  Widget _buildPage(BuildContext context) {
    final bloc = BlocProvider.of<${name.toCamelCase()}Bloc>(context);
    return const Scaffold(
      body: Center(
        child: Text('${name.toCamelCase()} Page'),
      ),
    );
  }
}"""
}