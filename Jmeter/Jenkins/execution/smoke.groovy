def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    engine.runJMeter(
        "Scripts/TestScripts/Facebook.jmx",
        "Scripts/Results/results.jtl"
    )

    echo "========== SMOKE STRATEGY =========="
}

return this