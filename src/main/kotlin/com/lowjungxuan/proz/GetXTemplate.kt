package com.lowjungxuan.proz

object GetXTemplate {
    const val page = """import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '${'$'}{folderName}Controller.dart';

class ${'$'}{folderName}Page extends GetView<${'$'}{folderName}Controller> {
  const ${'$'}{folderName}Page({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(
        child: Text('${'$'}{folderName}'),
    );
  }
}
"""
    const val controller = """import 'package:get/get.dart';

class ${'$'}{folderName}Controller extends GetxController {
  final ${'$'}{folderName}State state = ${'$'}{folderName}State();
}

class ${'$'}{folderName}Binding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut(() => ${'$'}{folderName}Controller());
  }
}

class ${'$'}{folderName}State {
  ${'$'}{folderName}State() {
    ///Initialize variables
  }
}
"""
}