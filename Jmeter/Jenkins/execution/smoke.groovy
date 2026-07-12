def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    engine.prepareWorkspace()

    engine.runJMeter(
        "Scripts/TestScripts/Mobile_Banking_Performance.jmx",
        "Scripts/Results/results.jtl"
    )

    echo "========== SMOKE STRATEGY =========="
}

return this