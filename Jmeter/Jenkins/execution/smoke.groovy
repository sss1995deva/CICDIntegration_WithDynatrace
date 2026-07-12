def execute() {

    def common = load 'Jmeter/Jenkins/common.groovy'

    common.prepareWorkspace()

    echo "========== SMOKE STRATEGY =========="
}

return this