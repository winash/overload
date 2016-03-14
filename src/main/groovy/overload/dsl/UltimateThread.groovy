package overload.dsl

class UltimateThread implements Builder {


    public static final String ULTIMATETHREADGROUPDATA = "ultimatethreadgroupdata"

    Node build(Node node, Map parameters = [:]) {
        Node hashTree = ht(node)
        def tgNode = makeNode()
        def mapList = parameters["list"]
        // list of maps to create as a list
        def cProp = NodeBuilder.newInstance().collectionProp(name: ULTIMATETHREADGROUPDATA)
                {
                    mapList.eachWithIndex { Map m, int idx ->
                        collectionProp(name: "sched-${idx}") {
                            stringProp(m.get("count"),name: "${m.get("count")}")
                            stringProp(0,name: "0")
                            stringProp(m.get("rampup") ,name: "${m.get("rampup")}")
                            stringProp(m.get("hold"),name: "${m.get("hold")}")
                            stringProp(m.get("shutdown"),name: "${m.get("shutdown")}")

                        }

                    }
                }
        tgNode.append(cProp)
        hashTree.append(tgNode)
        node.append(hashTree)
        hashTree
    }



    String getResourceFile() {
        return "UltimateThread.xml"
    }


}