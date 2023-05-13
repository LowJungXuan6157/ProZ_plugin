package com.lowjungxuan.proz

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType

class GetXContext : TemplateContextType("FLUTTER", "Flutter") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return templateActionContext.file.name.endsWith(".dart")
    }
}