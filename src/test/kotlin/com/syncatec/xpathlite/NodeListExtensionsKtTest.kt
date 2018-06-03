package com.syncatec.xpathlite

import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Test

class NodeListExtensionsKtTest {

    @Test
    fun returnsList() {
        val node = node("<a><b/><b/></a>")
        val nodes = node.toElement().getElementsByTagName("b")

        val result = nodes.asList()

        assertThat(result, hasSize(2))
    }
}
