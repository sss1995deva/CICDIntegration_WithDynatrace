def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'
    engine.prepareWorkspace()
    def tests = engine.readTestConfiguration(params.TEST_TYPE)
    engine.prepareTestArtifacts(tests)	
    engine.executeSequential(tests)
}

return this