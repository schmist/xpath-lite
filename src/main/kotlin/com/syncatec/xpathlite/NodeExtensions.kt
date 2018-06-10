package com.syncatec.xpathlite

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node

internal fun Node.childrenByName(name: String): List<Node> =
    childNodes
        .asList()
        .filter { it.nodeName == name }

internal fun Node.toElement(): Element =
    this as Element

internal fun Node.toDocument(): Document =
    this as Document
