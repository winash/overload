# Overload #

Overload is a tool to generate and run jmeter scripts in a declarative fashion

 A simple load test plan looks like this
 ```
test {
    plan("test post request") {
        userLoad([[count:20,rampup:30,hold:30,shutdown:10],[count:10,rampup:30,hold:30,shutdown:10]]) {
            hit(url: 'requestb.in', port: 80,path:"/18mednu1") {
                postJson("data: { title: \'foo\',\r\n body: \'bar\',\r\n userId: 1\r\n }")
            auth(user:'admin',pass:'admin')
            }
        }
    }
}
 ```

 This will get translated to a Jmeter test like
 ```
 <jmeterTestPlan version="1.2" properties="2.5">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="TestPlan" enabled="true">
      <stringProp name="TestPlan.comments"/>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" testname="#{testname}" enabled="true">
        <collectionProp name="Arguments.arguments"/>
      </elementProp>
      <stringProp name="TestPlan.user_define_classpath"/>
    </TestPlan>
    <hashTree>
      <kg.apc.jmeter.threads.UltimateThreadGroup guiclass="kg.apc.jmeter.threads.UltimateThreadGroupGui" testclass="kg.apc.jmeter.threads.UltimateThreadGroup" testname="jp@gc - Ultimate Thread Group" enabled="true">
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController" guiclass="LoopControlPanel" testclass="LoopController" testname="Loop Controller" enabled="true">
          <boolProp name="LoopController.continue_forever">false</boolProp>
          <intProp name="LoopController.loops">-1</intProp>
        </elementProp>
        <stringProp name="ThreadGroup.on_sample_error">continue</stringProp>
        <collectionProp name="ultimatethreadgroupdata">
          <collectionProp name="sched-0">
            <stringProp name="20">20</stringProp>
            <stringProp name="0">0</stringProp>
            <stringProp name="30">30</stringProp>
            <stringProp name="30">30</stringProp>
            <stringProp name="10">10</stringProp>
          </collectionProp>
          <collectionProp name="sched-1">
            <stringProp name="10">10</stringProp>
            <stringProp name="0">0</stringProp>
            <stringProp name="30">30</stringProp>
            <stringProp name="30">30</stringProp>
            <stringProp name="10">10</stringProp>
          </collectionProp>
        </collectionProp>
      </kg.apc.jmeter.threads.UltimateThreadGroup>
      <hashTree>
        <HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="testName" enabled="true">
          <stringProp name="HTTPSampler.domain">requestb.in</stringProp>
          <stringProp name="HTTPSampler.port">80</stringProp>
          <stringProp name="HTTPSampler.connect_timeout"/>
          <stringProp name="HTTPSampler.response_timeout"/>
          <stringProp name="HTTPSampler.protocol"/>
          <stringProp name="HTTPSampler.contentEncoding"/>
          <stringProp name="HTTPSampler.path">/18mednu1</stringProp>
          <stringProp name="HTTPSampler.method">POST</stringProp>
          <boolProp name="HTTPSampler.follow_redirects">true</boolProp>
          <boolProp name="HTTPSampler.auto_redirects">false</boolProp>
          <boolProp name="HTTPSampler.use_keepalive">true</boolProp>
          <boolProp name="HTTPSampler.DO_MULTIPART_POST">false</boolProp>
          <stringProp name="HTTPSampler.implementation"/>
          <boolProp name="HTTPSampler.monitor">false</boolProp>
          <stringProp name="HTTPSampler.embedded_url_re"/>
          <stringProp name="TestPlan.comments"/>
          <elementProp name="HTTPsampler.Arguments" elementType="Arguments">
            <collectionProp name="Arguments.arguments">
              <elementProp name="" elementType="HTTPArgument">
                <boolProp name="HTTPArgument.always_encode">false</boolProp>
                <stringProp name="Argument.value">data: { title: 'foo',
        body: 'bar',
       userId: 1
 }</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
              </elementProp>
            </collectionProp>
          </elementProp>
        </HTTPSamplerProxy>
        <hashTree>
          <BeanShellPreProcessor guiclass="TestBeanGUI" testclass="BeanShellPreProcessor" testname="BeanShell PreProcessor" enabled="true">
            <stringProp name="filename"/>
            <stringProp name="parameters"/>
            <boolProp name="resetInterpreter">true</boolProp>
            <stringProp name="script">import org.apache.jmeter.protocol.http.control.Header;
        import java.util.UUID;

        sampler.getHeaderManager().removeHeaderNamed("Authorization");
        sampler.getHeaderManager().removeHeaderNamed("flow-id");
        sampler.getHeaderManager().add(new Header("Authorization","Bearer " + "YWRtaW46YWRtaW4="));
        sampler.getHeaderManager().add(new Header("flow-id",UUID.randomUUID().toString()));
    </stringProp>
          </BeanShellPreProcessor>
        </hashTree>
        <HeaderManager guiclass="HeaderPanel" testclass="HeaderManager" testname="HTTP Header Manager" enabled="true">
          <collectionProp name="HeaderManager.headers">
            <elementProp name="" elementType="Header">
              <stringProp name="Header.name">Content-type</stringProp>
              <stringProp name="Header.value">application/json</stringProp>
            </elementProp>
          </collectionProp>
        </HeaderManager>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>

 ```



