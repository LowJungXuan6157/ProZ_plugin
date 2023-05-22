package com.lowjungxuan.proz.inlineHint

class DocumentMarkupModelScanner {
    companion object {
        val instance: Any
        fun getInstance(): DocumentMarkupModelScanner? {
            if (instance == null) instance = DocumentMarkupModelScanner()
            return instance
        }
        var NAME = "ManualScanner"
    }
}