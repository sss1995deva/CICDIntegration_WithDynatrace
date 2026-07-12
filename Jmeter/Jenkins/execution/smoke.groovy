def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

	tests.each { test ->

    engine.runJMeter(
        test.script,
        "Scripts/Results/${test.name}.jtl"
    )
}
}

return this