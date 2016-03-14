package overload.dsl

class TestPlan implements Builder {


    String getResourceFile() {
        return "TestPlan.xml"
    }

    Node build(Node node, Map parameters = [:]) {
        buildNode(node, parameters)
    }




}
