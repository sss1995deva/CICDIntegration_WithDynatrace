def execute() {

    def engine = load 'Jmeter/Jenkins/executionEngine.groovy'

    def tests = engine.readTestConfiguration(params.TEST_TYPE)

    echo "Loaded Configuration:"

    tests.each {

        echo "--------------------------------"

        echo "Name      : ${it.name}"
        echo "Script    : ${it.script}"
        echo "Duration  : ${it.duration}"
        echo "Users     : ${it.users}"
    }
}

return this