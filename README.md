# xpath-lite
![Build Status](https://travis-ci.org/schmist/xpath-lite.svg?branch=master)

The aim of this library is to provide a simple and performant but **not** feature-complete XPath implementation for the JVM written in Kotlin. It is meant to extract information from small and plain XML files.

Currently supported XPath features (this list might grow over time):
* Selects from the root node:`/a/b/c`
* Selects from the current node: `a/b/c`
* Selects an attribute: `a/b/@c`

Usage:
```kotlin
val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
val doc = documentBuilder.parse(File("file.xml"))
val xPath = XPathLite()
val result = xPath.evaluate("/a/b/c", doc, NODELIST) as NodeList
```

The available evaluation return types are as follow:
* `NODELIST` retrieves all nodes matching the provided expression.
* `NODE` returns only the first seen node.
* `STRING` returns the text content of the first seen node.

The evaluation returns an object of type `Any?` which needs to be cast to `NodeList`, `Node`, or `String` according to the provided return type. If no matching nodes could be found, the result of the evaluation is either an empty `NodeList` or `null`.