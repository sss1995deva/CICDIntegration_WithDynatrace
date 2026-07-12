def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    tests.each { test ->

        echo "Executing : ${test.name}"

        engine.runJMeter(
            test.script,
            "Scripts/Results/${test.name}.jtl"
        )
    }
}

return this