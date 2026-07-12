import java.util.Properties

def prepareWorkspace() {

    echo "======================================"
    echo "Preparing Execution Workspace..."
    echo "======================================"

    sh '''
        rm -rf Jmeter/Scripts/Results/HTMLReport
        rm -f Jmeter/Scripts/Results/results.jtl
        mkdir -p Jmeter/Scripts/Results
    '''
}

def runJMeter(String jmxFile, String resultFile) {

    echo "Executing JMeter Script : ${jmxFile}"

    sh """
        cd Jmeter

        jmeter \
        -n \
        -t ${jmxFile} \
        -l ${resultFile} \
        -j Scripts/Results/jmeter_execution.log
    """
}

def readTestConfiguration(String testType) {

    echo "Loading Test Configuration..."

   def props = readProperties file: 'Jmeter/Config/test-config.properties'

    def testList = props.getProperty(testType)

    if (!testList) {
        error "No configuration found for ${testType}"
    }

    def tests = []

    testList.split(",").each { name ->

        name = name.trim()

        tests << [
            name     : name,
            script   : props.getProperty("${name}_SCRIPT"),
            duration : props.getProperty("${name}_DURATION"),
            users    : props.getProperty("${name}_USERS")
        ]
    }

    return tests
}

return this