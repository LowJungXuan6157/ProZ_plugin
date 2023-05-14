package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.ui.ComponentValidator
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.fields.ExtendableTextField
import com.intellij.util.ui.AsyncProcessIcon
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBFont
import org.slf4j.LoggerFactory
import com.lowjungxuan.proz.versionchecker.services.ItbugService
import com.lowjungxuan.proz.versionchecker.services.LocalhostServiceCreate
import com.lowjungxuan.proz.versionchecker.services.LoginParam
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.event.DocumentEvent
const val borderPaddings = 30
