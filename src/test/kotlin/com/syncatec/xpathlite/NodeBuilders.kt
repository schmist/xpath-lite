package com.syncatec.xpathlite

import org.w3c.dom.Document
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

private val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

fun node(xml: String): Node =
    (rootNode(xml) as Document).documentElement

fun rootNode(xml: String): Node =
    documentBuilder.parse(xml.byteInputStream())
