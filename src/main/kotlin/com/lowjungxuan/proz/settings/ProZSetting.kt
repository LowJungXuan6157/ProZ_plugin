package com.lowjungxuan.proz.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "com.lowjungxuan.proz.settings.ProZSetting",
    storages = [Storage("ProZSetting.xml")]
)
class ProZSetting : PersistentStateComponent<ProZSetting.State> {

    private var myState = State()

    class State {
        var enabledCategories = mutableSetOf("GetX", "Bloc")  // default to enabled
    }

    override fun getState(): State {
        return myState
    }

    override fun loadState(state: State) {
        state.enabledCategories
        myState = state
    }
}
