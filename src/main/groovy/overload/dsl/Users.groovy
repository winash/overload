package overload.dsl

class Users implements Builder {

    Node build(Node node, Map parameters = [:]) {
        buildNode(node, parameters)
    }



    String getResourceFile() {
        return "UltimateThreadGroup.xml"
    }

    Map remap() {
        [count:"num_threads",rampup:"ramp_time",times:'loops']
    }
}