def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'
    engine.prepareWorkspace()
    def tests = engine.readTestConfiguration(params.TEST_TYPE)
    engine.prepareTestArtifacts(tests)	
    engine.executeParallel(tests)
    engine.generateReports(tests)
    engine.zipReports(tests)
}

return this