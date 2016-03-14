package overload.dsl

import groovy.xml.MarkupBuilder

/**
 * Set up the initial doc root
 */
class Root {

    Node build(){
        NodeBuilder.newInstance().jmeterTestPlan(version:"1.2",properties:"2.5")
    }



}
