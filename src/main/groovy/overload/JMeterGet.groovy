package overload

import overload.dsl.DSLBinding

import java.util.logging.Logger

/**
 * TODO: Download and install a custom JMETER from box
 */

class JMeterGet {

    public static final String workDir = ".overload"
    private String uri = "https://www.dropbox.com/s/ylvvzcnpvxkittt/apache-jmeter-2.13.zip?dl=0"
    Logger logger = Logger.getLogger("JMeterGet")

    private String homedir = System.getProperty('user.dir')

    JMeterGet() {
        // create workdir
        def dir = new File("${homedir}/" + workDir)
        if(!dir.exists()) {
            logger.info("No working directory, creating")
            dir.mkdirs()
        }
    }


    def download() {
        // UNZIP JMETER
       ant.unzip(src:"./apache-jmeter-2.13.zip",dest:"./")

    }


    public static void main(String[] args) {


        def binding = new DSLBinding()
        def shell = new GroovyShell(binding)

        shell.evaluate("""test {
    plan("test google") {
        userLoad([[count:20,rampup:30,hold:30,shutdown:10],[count:10,rampup:30,hold:30,shutdown:10]]) {
            hit(url: 'requestb.in', port: 80,path:"/18mednu1") {
                postJson("data: { title: \\'foo\\',\\r\\n        body: \\'bar\\',\\r\\n       userId: 1\\r\\n }")

                 auth(user:'admin',pass:'admin')
            }
        }



    }


}""")

    }


}
