package com.syncatec.xpathlite

import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal fun List<Node>.toNodeList() =
    object : NodeList {
        override fun item(index: Int): Node =
            this@toNodeList[index]

        override fun getLength(): Int =
            this@toNodeList.size
    }
