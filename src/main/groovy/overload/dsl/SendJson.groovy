package overload.dsl

import com.google.common.xml.XmlEscapers

/**
 * Post JSON data
 */
class SendJson implements Builder {


    String getResourceFile() {
        return "PostJson.xml"
    }

    Node build(Node node, Map parameters = [:]) {
        // fix encoding in parameters
        def tgNode = makeNode()
        refit(tgNode, parameters)
        // descend and replace the HTTPsampler.Arguments node

        def toReplace = node.HTTPSamplerProxy[0].find {
            it['@name'].toString().toLowerCase() == "httpsampler.arguments"
        }
        Node method = node.HTTPSamplerProxy[0].find { it['@name'].toString().toLowerCase() == "httpsampler.method" }
        if(parameters.containsKey("type")){
            method.setValue(parameters["type"])
        } else {
            method.setValue("POST")
        }
        node.HTTPSamplerProxy[0].remove(toReplace)
        node.HTTPSamplerProxy[0].append(tgNode)
        node
    }

    Map remap() {
        [data: "value"]
    }


}
