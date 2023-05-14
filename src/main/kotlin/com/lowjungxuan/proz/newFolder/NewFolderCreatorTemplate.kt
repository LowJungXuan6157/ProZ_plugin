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
    return const Center(
        child: Text('${name.toCamelCase()} Page'),
    );
  }
}
"""

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
}
"""

    fun bloc(): String = """import 'package:bloc/bloc.dart';

import '${'$'}{folderName.toSnakeCase()}_event.dart';
import '${'$'}{folderName.toSnakeCase()}_state.dart';

class TestBloc extends Bloc<TestEvent, TestState> {
  TestBloc() : super(TestState().init()) {
    on<InitEvent>(_init);
  }

  void _init(InitEvent event, Emitter<TestState> emit) async {
    emit(state.clone());
  }
}
"""
}