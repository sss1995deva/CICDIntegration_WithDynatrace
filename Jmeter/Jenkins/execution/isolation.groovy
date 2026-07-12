def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    def branches = [:]

tests.each { test ->

    branches[test.name] = {

        test.resultFile   = "Scripts/Results/${test.name}.jtl"
        test.reportFolder = "Scripts/Results/${test.name}_HTML"
        test.zipFile      = "Scripts/Results/${test.name}.zip"

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

}

return this