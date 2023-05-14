package com.lowjungxuan.proz.versionchecker.render

import com.lowjungxuan.proz.versionchecker.model.chat.IdeaMessage
import javax.swing.DefaultListModel

class ChatHistoryListModel(val list: List<IdeaMessage>) : DefaultListModel<IdeaMessage>() {

    override fun getSize(): Int {
       return list.size
    }

    override fun getElementAt(index: Int): IdeaMessage {
        return list[index]
    }


}