package com.syncatec.xpathlite

import com.syncatec.xpathlite.XPathLite.ResultType.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class XPathLiteTest {

    private lateinit var xPath: XPathLite

    @Before
    fun setUp() {
        xPath = XPathLite()
    }

    @Test
    fun evaluatesToNode() {
        val xml = "<a/>"
        val node = rootNode(xml)

        xPath.evaluateNode("/a", node)
    }

    @Test
    fun evaluatesToNodeList() {
        val node = rootNode("<a/>")

        xPath.evaluateNodeList("/a", node)
    }

    @Test
    fun evaluatesToString() {
        val node = rootNode("<a b='c'/>")

        xPath.evaluateString("/a/@b", node)
    }

    @Test(expected = IllegalStateException::class)
    fun throwsExceptionIfExpectedRootNodeIsNotARootNode() {
        val node = node("<a/>")

        xPath.evaluate("/a", node, NODE)
    }

    @Test
    fun returnsNullIfExpectedRootNodeNameDoesNotMatch() {
        val node = rootNode("<a/>")

        val result = xPath.evaluate("/b", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsRootElement() {
        val node = rootNode("<a/>")

        val result = xPath.evaluate("/a", node, NODE)

        assertThat(result.toNode(), iz(node.toDocument().documentElement.toNode()))
    }

    @Test
    fun returnsNodeForEmptyExpression() {
        val node = node("<a/>")

        val result = xPath.evaluate("", node, NODE)

        assertThat(result.toNode(), iz(node))
    }

    @Test
    fun returnsChildOfRoot() {
        val node = rootNode("<a><b/></a>")

        val result = xPath.evaluate("/a/b", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("b"))
    }

    @Test
    fun returnsChildOfChildOfRoot() {
        val node = rootNode("<a><b><c/></b></a>")

        val result = xPath.evaluate("/a/b/c", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun returnsNullIfChildOfRootDoesNotExist() {
        val node = rootNode("<a><b/></a>")

        val result = xPath.evaluate("/a/c", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsChildOfNode() {
        val node = node("<a><b/></a>")

        val result = xPath.evaluate("b", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("b"))
    }

    @Test
    fun returnsChildOfChildOfNode() {
        val node = node("<a><b><c/></b></a>")

        val result = xPath.evaluate("b/c", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun ignoresMultipleAndTrailingSlashes() {
        val node = node("<a><b><c/></b></a>")

        val result = xPath.evaluate("b///c/", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("c"))
    }

    @Test
    fun returnsNullIfChildOfNodeDoesNotExist() {
        val node = node("<a><b/></a>")

        val result = xPath.evaluate("c", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsAttributeNode() {
        val node = rootNode("<a b='c'/>")

        val result = xPath.evaluate("/a/@b", node, NODE)

        assertThat(result.toNode()?.nodeName, iz("b"))
    }

    @Test
    fun returnsNullIfAttributeNodeDoesNotExist() {
        val node = rootNode("<a/>")

        val result = xPath.evaluate("/a/@b", node, NODE)

        assertThat(result, nullValue())
    }

    @Test
    fun returnsNodeListForAllMatchingNodes() {
        val xml = "" +
            "<a>" +
            "   <b>" +
            "       <c/>" +
            "   </b>" +
            "   <b>" +
            "       <c/>" +
            "   </b>" +
            "</a>"
        val node = rootNode(xml)

        val result = xPath.evaluate("/a/b/c", node, NODELIST)

        assertThat(result.toNodeList().length, iz(2))
    }

    @Test
    fun returnsNodeListForOnlyDirectChildren() {
        val node = rootNode("<a><b><b/></b></a>")

        val result = xPath.evaluate("/a/b", node, NODELIST)

        assertThat(result.toNodeList().length, iz(1))
    }

    @Test
    fun returnsEmptyNodeListIfNoMatchingNodesExist() {
        val node = rootNode("<a/>")

        val result = xPath.evaluate("/b", node, NODELIST)

        assertThat(result.toNodeList().length, iz(0))
    }

    @Test
    fun returnsAttributeNodeText() {
        val node = rootNode("<a b='c'/>")

        val result = xPath.evaluate("/a/@b", node, STRING)

        assertThat(result.toString(), iz("c"))
    }

    @Test
    fun returnsNodeText() {
        val node = rootNode("<a>b</a>")

        val result = xPath.evaluate("/a", node, STRING)

        assertThat(result.toString(), iz("b"))
    }

    @Test
    fun evaluateNodeListReturnsNodeList() {
        val node = rootNode("<a><b/><b/></a>")

        val result = xPath.evaluateNodeList("/a/b", node)

        assertThat(result, instanceOf(NodeList::class.java))
    }

    @Test
    fun evaluateStringReturnsString() {
        val node = rootNode("<a>foo</a>")

        val result = xPath.evaluateString("/a", node)

        assertThat(result, instanceOf(String::class.java))
    }

    private fun Any?.toNode(): Node? =
        this as Node?

    private fun Any?.toNodeList(): NodeList =
        this as NodeList

    private fun Any?.toString(): String? =
        this as String?
}