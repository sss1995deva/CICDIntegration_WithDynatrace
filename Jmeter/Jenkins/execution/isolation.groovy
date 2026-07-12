def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    def tests = engine.readTestConfiguration(params.TEST_TYPE)    
    engine.prepareTestArtifacts(tests)	


tests.each { test ->

        echo "Executing : ${test.name}"

        engine.runJMeter(
            test.script,
            test.resultFile
        )

        engine.generateHtml(
            test.resultFile,
            test.reportFolder
        )

        engine.zipReport(
            test.reportFolder,
            test.zipFile
        )    
}

}

return this