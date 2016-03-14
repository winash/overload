package overload.dsl

trait Builder {

     static Builder newInstance = this.constructors[0].newInstance()

     def buildNode(Node node, Map parameters) {
        Node hashTree = ht(node)
        def tgNode = makeNode()
        refit(tgNode, parameters)
        hashTree.append(tgNode)
        node.append(hashTree)
        hashTree
    }

    def resource(){
        this.getClass().getClassLoader().getResourceAsStream(getResourceFile()).text
    }

    abstract String getResourceFile()

    Map remap() { [:] }

    def nb = {
        NodeBuilder.newInstance()
    }

    def refit = { Node n, Map parameters ->
        def map = remap()
        def newMap = [:]
        parameters.each { k, v ->
            if(map.containsKey(k)) {
                newMap[map[k]] = v
            } else
                newMap[k] = v
        }
            n.depthFirst().each { Node ch ->
                if(ch.@name) {
                    def secondPart = ch.@name.toString().split("[.]")[1]
                    if (secondPart && newMap.containsKey(secondPart)) {
                    ch.setValue(newMap.get(secondPart))
                }
                }
            }

    }

    def makeNode(){
        new XmlParser().parseText(resource())
    }




    def ht = {
        nb.call().hashTree()
    }

    abstract Node build(Node Node, Map parameters)


}