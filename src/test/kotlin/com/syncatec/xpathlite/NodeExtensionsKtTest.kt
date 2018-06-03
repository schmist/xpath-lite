package com.syncatec.xpathlite

import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class NodeExtensionsKtTest {

    @Test
    fun retrievesDirectChildrenByName() {
        val xml = "" +
            "<foo>" +
            "   <a>" +
            "       <a>" +
            "       </a>" +
            "   </a>" +
            "</foo>"
        val node = node(xml)

        val result = node.childrenByName("a")

        assertThat(result.size, iz(1))
        assertThat(result.first().nodeName, iz("a"))
    }
}
