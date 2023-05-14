package com.lowjungxuan.proz.versionchecker.util

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

val tokenCa = CredentialAttributes(generateServiceName("ldd", "token"))

///密码存储相关工具类
object CredentialUtil {


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
