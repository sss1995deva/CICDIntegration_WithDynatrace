def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    tests.each { test ->

        echo "-----------------------------"
        echo "Name     : ${test.name}"
        echo "Script   : ${test.script}"
        echo "Duration : ${test.duration}"
        echo "Users    : ${test.users}"
    }
}

return this