def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    tests.each { test ->
	 test.resultFile   = "Scripts/Results/${test.name}.jtl"
         test.reportFolder = "Scripts/Results/${test.name}_HTML"
         test.zipFile      = "Scripts/Results/${test.name}.zip"


        echo "Executing : ${test.name}"	

        engine.runJMeter(
            test.script,
            "Scripts/Results/${test.name}.jtl"
        )
     engine.generateHtml(
        resultFile,
        reportFolder
    )
    }
}

return this