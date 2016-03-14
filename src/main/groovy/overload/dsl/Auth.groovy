package overload.dsl

class Auth implements Builder {


    String getResourceFile() {
        return "Auth.xml"
    }

    Node build(Node node, Map parameters = [:]) {
        Node hashTree = ht(node)
        Node tgNode = makeNode()
        Node code = getCode(parameters["user"],parameters["pass"])
        tgNode.append(code)
        hashTree.append(tgNode)
        node.append(hashTree)
        node.append(getHeader())
        hashTree
    }

    Node getHeader(){
        new XmlParser().parseText("""<HeaderManager guiclass="HeaderPanel" testclass="HeaderManager" testname="HTTP Header Manager" enabled="true">
    <collectionProp name="HeaderManager.headers">
            <elementProp name="" elementType="Header">
              <stringProp name="Header.name">Content-type</stringProp>
              <stringProp name="Header.value">application/json</stringProp>
            </elementProp>
          </collectionProp>
</HeaderManager>""")
    }


    Node getCode(String user,String pass){
        String template = """<stringProp name="script">import org.apache.jmeter.protocol.http.control.Header;
        import java.util.UUID;

        sampler.getHeaderManager().removeHeaderNamed(&quot;Authorization&quot;);
        sampler.getHeaderManager().removeHeaderNamed(&quot;flow-id&quot;);
        sampler.getHeaderManager().add(new Header(&quot;Authorization&quot;,&quot;Bearer &quot; + &quot;##generatedToken&quot;));
        sampler.getHeaderManager().add(new Header(&quot;flow-id&quot;,UUID.randomUUID().toString()));
    </stringProp>"""

        String bearer = "${user}:${pass}".bytes.encodeBase64().toString()

        def replaced = template.replace("##generatedToken", bearer)
        new XmlParser().parseText(replaced)
    }


}
