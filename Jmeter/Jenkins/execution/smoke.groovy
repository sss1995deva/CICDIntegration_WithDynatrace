def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    def branches = [:]

tests.each { test ->

    branches[test.name] = {

        echo "Executing : ${test.name}"

        engine.runJMeter(
            test.script,
            "Scripts/Results/${test.name}.jtl"
        )
    }
}

parallel branches
}

return this