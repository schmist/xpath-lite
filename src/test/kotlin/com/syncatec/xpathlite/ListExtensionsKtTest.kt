package com.syncatec.xpathlite

import org.junit.Assert.assertThat
import org.junit.Test

class ListExtensionsKtTest {

    @Test
    fun returnsNodeList() {
        val node1 = node("<foo/>")
        val node2 = node("<bar/>")

        val result = listOf(node1, node2).toNodeList()

        assertThat(result.length, iz(2))
        assertThat(result.item(0), iz(node1))
        assertThat(result.item(1), iz(node2))
    }
}
