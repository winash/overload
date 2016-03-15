package overload.dsl

class AsyncPost implements Builder {

    String getResourceFile() {
        "AsyncPost.xml"
    }

    Node build(Node node, Map parameters) {
        Node hashTree = ht(node)
        def tgNode = makeNode()

        // replace the parameter list with the mapped parameters
        String params = "${parameters.url}" +
                "#${parameters.data}#${parameters.extract}" +
                "#${parameters.callback}#${parameters.key}#${parameters.delay}"

//        replace the node

        def base64 = params.bytes.encodeBase64()

        Node paramsNode = tgNode.find { it['@name'] == 'parameters' }
        paramsNode.setValue(base64)
        hashTree.append(tgNode)
        node.append(hashTree)
        hashTree
    }




}
