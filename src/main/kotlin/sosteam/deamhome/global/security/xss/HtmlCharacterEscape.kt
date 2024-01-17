package sosteam.deamhome.global.security.xss

import com.fasterxml.jackson.core.SerializableString
import com.fasterxml.jackson.core.io.CharacterEscapes
import com.fasterxml.jackson.core.io.SerializedString
import org.apache.commons.text.StringEscapeUtils

class HtmlCharacterEscape : CharacterEscapes() {
    private val asciiEscapes: IntArray = standardAsciiEscapesForJSON()

    init {
        val hi = asciiEscapes['<'.code]
        asciiEscapes['<'.code] = ESCAPE_CUSTOM
        asciiEscapes['>'.code] = ESCAPE_CUSTOM
        asciiEscapes['\"'.code] = ESCAPE_CUSTOM
        asciiEscapes['('.code] = ESCAPE_CUSTOM
        asciiEscapes[')'.code] = ESCAPE_CUSTOM
        asciiEscapes['#'.code] = ESCAPE_CUSTOM
        asciiEscapes['\''.code] = ESCAPE_CUSTOM
    }

    override fun getEscapeCodesForAscii(): IntArray {
        return asciiEscapes
    }

    override fun getEscapeSequence(ch: Int): SerializableString {
        return SerializedString(StringEscapeUtils.escapeHtml4(ch.toChar().toString()))
    }
}