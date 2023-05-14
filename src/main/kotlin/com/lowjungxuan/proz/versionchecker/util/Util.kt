package com.lowjungxuan.proz.versionchecker.util

import com.google.common.base.CaseFormat
import com.intellij.util.ui.UIUtil
import com.lowjungxuan.proz.versionchecker.constance.dartKeys
import java.awt.Color
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun Color.toHexString(): String {
    return Util.toHexFromColor(this)
}

fun String.formatDartName() : String {
    return Util.removeSpecialCharacters(this)
}


fun String.firstChatToUpper() : String {
    return  CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, this)
}

class Util {
    companion object {

        val userHomePath: String get() = System.getProperty("user.home")

         fun toHexFromColor(color: Color): String {
            return UIUtil.colorToHex(color)
        }


        fun removeSpecialCharacters(string: String): String {

            var str1: String = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, string)
            str1 = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str1)
            if (dartKeys.contains(str1)) {
                str1 += "_"
            }
            return str1
        }

        /**
         * 获取本机IP列表
         */
        fun resolveLocalAddresses(): Set<InetAddress> {
            val addrs: MutableSet<InetAddress> = HashSet<InetAddress>()
            var ns: Enumeration<NetworkInterface>? = null
            try {
                ns = NetworkInterface.getNetworkInterfaces()
            } catch (_: SocketException) {
            }
            while (ns != null && ns.hasMoreElements()) {
                val n: NetworkInterface = ns.nextElement()
                val `is`: Enumeration<InetAddress> = n.inetAddresses
                while (`is`.hasMoreElements()) {
                    val i: InetAddress = `is`.nextElement()
                    if (!i.isLoopbackAddress && !i.isLinkLocalAddress && !i.isMulticastAddress
                        && !isSpecialIp(i.hostAddress)
                    ) addrs.add(i)
                }
            }
            return addrs
        }

        private fun isSpecialIp(ip: String): Boolean {
            if (ip.contains(":")) return true
            if (ip.startsWith("127.")) return true
            if (ip.startsWith("169.254.")) return true
            return ip == "255.255.255.255"
        }

        /**
         * 字符串是否包含中文
         *
         * @param str 待校验字符串
         * @return true 包含中文字符 false 不包含中文字符
         */
        fun isContainChinese(str: String): Boolean {
            val p: Pattern = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]")
            val m: Matcher = p.matcher(str)
            return m.find()
        }



        fun addStringToLineStart(text:String, value:String) : String {
            val bufferedReader = BufferedReader(InputStreamReader(ByteArrayInputStream(text.toByteArray())))
            val sb = StringBuilder()
            bufferedReader.forEachLine {
                sb.appendLine("$value $it")
            }
            return sb.toString()
        }

    }
}