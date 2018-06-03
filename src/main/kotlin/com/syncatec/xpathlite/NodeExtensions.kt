package com.syncatec.xpathlite

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node

fun Node.childrenByName(name: String): List<Node> =
    childNodes
        .asList()
        .filter { it.nodeName == name }

fun Node.toElement(): Element =
    this as Element

fun Node.toDocument(): Document =
    this as Document
