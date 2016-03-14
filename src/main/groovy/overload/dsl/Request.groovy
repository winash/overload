package overload.dsl

class Request implements Builder {


    String getResourceFile() {
        return "HttpSamplerProxy.xml"
    }

    Node build(Node node, Map parameters = [:]) {
        buildNode(node, parameters)
    }

    Map remap() {
        [url:"domain",port:"port"]
    }



}
