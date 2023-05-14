package com.lowjungxuan.proz.versionchecker.widget

import com.aallam.openai.api.BetaOpenAI
import com.intellij.openapi.application.EDT
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.lowjungxuan.proz.versionchecker.common.toJsonFormart
import com.lowjungxuan.proz.versionchecker.dsl.mkToHtml
import com.lowjungxuan.proz.versionchecker.inlay.MyAIChatModel
import com.lowjungxuan.proz.versionchecker.util.OpenAiUtil
import com.lowjungxuan.proz.versionchecker.util.toastWithError
import javax.swing.BorderFactory
import javax.swing.DefaultListModel
import javax.swing.ListCellRenderer

class AiChatListWidget(val project: Project) : JBList<MyAIChatModel>() {

    private val mutex = Mutex()

    init {
        model = DefaultListModel()
        cellRenderer =
            ListCellRenderer { _, p1, _, _, _ ->
                if (p1.isMe) {
                    JBLabel("Q:${p1.content}")
                } else {
                    JBLabel("<html>AI:${mkToHtml(p1.content.toString(), project)}</html>")
                }

            }

        border = BorderFactory.createEmptyBorder()
    }


    fun addQ(content: String) {
        val q = MyAIChatModel(content = java.lang.StringBuilder(content), isMe = true)
        getListModel().addElement(q)
        startChat(content)
    }

    /**
     * 开始一个会话
     */
    @OptIn(BetaOpenAI::class, DelicateCoroutinesApi::class)
    fun startChat(content: String) {
        GlobalScope.launch(Dispatchers.EDT) {
            try {
                OpenAiUtil.askSimple(content).collect { chunk ->
                    val r = chunk.choices.first().delta?.content ?: ""
                    if (r.isNotEmpty()) {
                        val index = getListModel().toArray().indexOfLast { obj ->
                            val it = obj as MyAIChatModel
                            it.id == chunk.id && it.id.isNotEmpty() && it.isMe.not()
                        }
                        mutex.withLock {
                            if (index != -1) {
                                val ele = getListModel()[index]
                                ele.content.append(r)
                                getListModel()[index] = ele
                                println(getListModel().toArray().toJsonFormart())
                            } else {
                                val newChat = MyAIChatModel(
                                    content = StringBuilder(r),
                                    id = chunk.id
                                )
                                getListModel().addElement(newChat)

                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println("出错了...$e")
                project.toastWithError("$e")
            }
        }
    }


    private fun getListModel(): DefaultListModel<MyAIChatModel> {
        return model as DefaultListModel<MyAIChatModel>
    }


}