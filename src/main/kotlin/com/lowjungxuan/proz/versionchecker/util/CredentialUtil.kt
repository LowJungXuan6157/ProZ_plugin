package com.lowjungxuan.proz.versionchecker.util

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.lowjungxuan.proz.versionchecker.model.UserAccount

val ca = CredentialAttributes(generateServiceName("ldd", "user"))
val tokenCa = CredentialAttributes(generateServiceName("ldd", "token"))
val openAiCa = CredentialAttributes(generateServiceName("openai", "apiKey"))

///密码存储相关工具类
object CredentialUtil {


    /**
     * 设置openAi的key
     */
    fun setOpenAiKey(apiKey: String) {
        PasswordSafe.instance.setPassword(openAiCa, apiKey)
    }

    /**
     * 读取openAi的key
     */
    val openApiKey: String get() = PasswordSafe.instance.getPassword(openAiCa) ?: ""


    /**
     * 保存一个token
     */
    fun saveToken(token: String) {
        PasswordSafe.instance.setPassword(tokenCa, token)
    }

    /**
     * 读取本机token
     */
    val token: String? get() = PasswordSafe.instance.get(tokenCa)?.getPasswordAsString()

    /**
     * 删除本机token
     */
    fun removeToken() {
        PasswordSafe.instance.set(tokenCa, null)
    }
}
