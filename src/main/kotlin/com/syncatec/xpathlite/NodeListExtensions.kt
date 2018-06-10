package com.syncatec.xpathlite

import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal fun NodeList.asList(): List<Node> =
    (0 until length).map { item(it) }
