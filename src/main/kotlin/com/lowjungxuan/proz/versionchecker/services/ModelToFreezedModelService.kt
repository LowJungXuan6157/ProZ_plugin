package com.lowjungxuan.proz.versionchecker.services

import com.alibaba.fastjson2.JSONObject
import com.intellij.openapi.actionSystem.AnActionEvent
import com.jetbrains.lang.dart.psi.impl.DartClassDefinitionImpl
import com.lowjungxuan.proz.versionchecker.model.FreezedCovertModel
const val DEFAULT_CLASS_NAME = "Root"
interface ModelToFreezedModelService {
    fun psiElementToFreezedCovertModel(classPsiElement: DartClassDefinitionImpl) : FreezedCovertModel
    fun anActionEventToFreezedCovertModel(event: AnActionEvent) : FreezedCovertModel
    fun jsonObjectToFreezedCovertModelList(jsonObject: JSONObject,oldList: MutableList<FreezedCovertModel> = mutableListOf(),className: String=DEFAULT_CLASS_NAME) : MutableList<FreezedCovertModel>
    fun jsonObjectToFreezedCovertModel(jsonObject: JSONObject,className: String = DEFAULT_CLASS_NAME) : FreezedCovertModel

}