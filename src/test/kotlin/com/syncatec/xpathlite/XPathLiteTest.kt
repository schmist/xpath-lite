package com.syncatec.xpathlite

import com.syncatec.xpathlite.XPathLite.ResultType.*
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class XPathLiteTest {

    private lateinit var xPath: XPathLite

    companion object {
        private const val xml = "" +
            "<a>" +
            "   <b>" +
            "       <b/>" +
            "       <c/>" +
            "   </b>" +
            "   <b>" +
            "       <c d='foo'/>" +
            "       <d>bar</d>" +
            "   </b>" +
            "</a>"
    }

    @Before
    fun setUp() {
        xPath = XPathLite()
    }

    @Test(expected = IllegalStateException::class)
    fun throwsExceptionIfExpectedRootNodeIsNotARootNode() {
        val node = node(xml)

        xPath.evaluate("/a", node, NODE)
    }

    @Test
    fun returnsNullIfExpectedRootNodeNameDoesNotMatch() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/b", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsRootElement() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a", node, NODE)

        assertThat(result.toNode(), iz(node.toDocument().documentElement.toNode()))
    }

    @Test
    fun returnsNodeForEmptyExpression() {
        val node = node(xml)

        val result = xPath.evaluate("", node, NODE)

        assertThat(result.toNode(), iz(node))
    }

    @Test
    fun returnsChildOfRoot() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("b"))
    }

    @Test
    fun returnsChildOfChildOfRoot() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun returnsNullIfChildOfRootDoesNotExist() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/c", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsChildOfNode() {
        val node = node(xml)

        val result = xPath.evaluate("b", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("b"))
    }

    @Test
    fun returnsChildOfChildOfNode() {
        val node = node(xml)

        val result = xPath.evaluate("b/c", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun ignoresMultipleAndTrailingSlashes() {
        val node = node(xml)

        val result = xPath.evaluate("b///c/", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun returnsNullIfChildOfNodeDoesNotExist() {
        val node = node(xml)

        val result = xPath.evaluate("c", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsAttributeNode() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c/@d", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("d"))
    }

    @Test
    fun returnsNullIfAttributeNodeDoesNotExist() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c/@e", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsNodeListForAllMatchingNodes() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c", node, NODELIST)

        assertThat(result.toNodeList()?.length, iz(2))
    }

    @Test
    fun returnsNodeListForOnlyDirectChildren() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b", node, NODELIST)

        assertThat(result.toNodeList()?.length, iz(2))
    }

    @Test
    fun returnsAttributeNodeText() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c/@d", node, STRING)

        assertThat(result.toString(), iz("foo"))
    }

    @Test
    fun returnsNodeText() {
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/d", node, STRING)

        assertThat(result.toString(), iz("bar"))
    }

    private fun Any?.toNode(): Node? =
        this as? Node

    private fun Any?.toNodeList(): NodeList? =
        this as? NodeList

    private fun Any?.toString(): String? =
        this as? String
}