package com.syncatec.xpathlite

import com.syncatec.xpathlite.XPathLite.ResultType.*
import org.w3c.dom.Node
import org.w3c.dom.Node.*

class XPathLite {

    enum class ResultType {
        NODE,
        NODELIST,
        STRING
    }

    fun evaluate(expression: String, node: Node, resultType: ResultType): Any? {
        val nodes = when {
            expression.startsWith("/") -> evaluateRootNode(segments(expression), node)
            else -> evaluateNode(segments(expression), node)
        }
        return when (resultType) {
            NODE -> nodes.firstOrNull()
            STRING -> nodes.firstOrNull()?.let { extractText(it) }
            NODELIST -> nodes.toNodeList()
        }
    }

    private fun evaluateRootNode(segments: List<String>, node: Node): List<Node> {
        if (node.nodeType != DOCUMENT_NODE) {
            throw IllegalStateException("Node ${node.nodeName} is not a document node.")
        }
        val rootNode = node.toDocument().documentElement
        val segment = segments.first()
        if (rootNode.nodeName != segment) {
            return emptyList()
        }
        return evaluateNode(segments.drop(1), rootNode)
    }

    private fun evaluateNode(segments: List<String>, node: Node): List<Node> =
        when (segments.size) {
            0 -> listOf(node)
            1 -> lastSegment(segments.first(), node)
            else -> node
                .childrenByName(segments.first())
                .flatMap { evaluateNode(segments.drop(1), it) }
        }

    private fun lastSegment(segment: String, node: Node): List<Node> =
        when (segment.first()) {
            '@' -> listOf(node.toElement().getAttributeNode(segment.substring(1)))
            else -> node.childrenByName(segment)
        }.filterNotNull()

    private fun segments(expression: String) =
        expression.split('/').filter { it.isNotEmpty() }

    private fun extractText(node: Node): String? =
        when (node.nodeType) {
            ELEMENT_NODE -> node.firstChild?.nodeValue
            ATTRIBUTE_NODE -> node.nodeValue
            else -> null
        }
}
