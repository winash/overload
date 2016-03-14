package overload.dsl

import com.google.common.base.Strings

import java.util.logging.Logger

class DSLBinding extends Binding {

    Logger logger = Logger.getLogger("DSLBinding")
    Stack<Pair> stack = new Stack();


    DSLBinding() {
        this.setVariable("test", this.&test)
    }


    def test(Closure cl) {
        logger.info("Starting test")
        def rootNode = new Root().build()
        stack.push(new Pair(name: "root",node:rootNode))
        def code = cl.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        stack.pop()

        // Dump the jmeter XML
        def writer = new StringWriter()
        def printer = new XmlNodePrinter(new PrintWriter(writer))
        printer.preserveWhitespace = true
        printer.print(rootNode)
        new File("./test.jmx").write(writer.toString())

    }


    def methodMissing(String name, Object args) {
        this."$name"(args)
    }

    def plan(String name,Closure cl) {
        def currentNode = TestPlan.newInstance.build(stack.peek().node)
        stack.push(new Pair(name:  "testPlan",node:  currentNode))
        def code = cl.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        stack.pop()
    }


    def users(Map args,Closure cl) {

        def currentNode = Users.newInstance.build(stack.peek().node,args)
        stack.push(new Pair(name:  "users",node:  currentNode))
        def code = cl.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        stack.pop()
    }




    def userLoad(List<Map> args,Closure cl) {
        // semantics are a bit different
        def currentNode = UltimateThread.newInstance.build(stack.peek().node,[list:args])
        stack.push(new Pair(name:  "userLoad",node:  currentNode))
        def code = cl.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        stack.pop()

    }


    def hit(Map args,Closure cl){
        def currentNode = Request.newInstance.build(stack.peek().node,args)
        stack.push(new Pair(name:  "hit",node:  currentNode))
        def code = cl.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        stack.pop()
    }

    def postJson(String data){
        if(Strings.isNullOrEmpty(data)){
            throw new RuntimeException("Format error, see manual");
        }
        if(stack.peek().name != "hit"){
            throw new RuntimeException("post can only be declared in a hit block");
        }

        SendJson.newInstance.build(stack.peek().node,[data:data, type:'POST'])

    }

    def putJson(String data){
        if(Strings.isNullOrEmpty(data)){
            throw new RuntimeException("Format error, see manual");
        }
        if(stack.peek().name != "hit"){
            throw new RuntimeException("post can only be declared in a hit block");
        }

        SendJson.newInstance.build(stack.peek().node,[data:data, type:'PUT'])

    }


    def think(Map args){
        if(!args){
            throw new RuntimeException("Format error, see manual");
        }
        if(stack.peek().name != "hit"){
            throw new RuntimeException("post can only be declared in a hit block");
        }
        ThinkTime.newInstance.build(stack.peek().node,args)
    }

    def auth(Map args){
        if(!args){
            throw new RuntimeException("Format error, see manual");
        }
        if(stack.peek().name != "hit"){
            throw new RuntimeException("post can only be declared in a hit block");
        }
        Auth.newInstance.build(stack.peek().node,args)
    }



    class Pair{
        String name
        Node node
    }









}
