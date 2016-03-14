package overload.dsl

class ThinkTime implements Builder {


    String getResourceFile() {
        return "ThinkTime.xml"
    }

    Node build(Node node, Map parameters = [:]) {
        // Dont attach a hashTree -
        def tgNode = makeNode()
        refit(tgNode, parameters)
        node.append(tgNode)
        node
    }





}
