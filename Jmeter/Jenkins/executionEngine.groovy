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

    def config = readFile('Jmeter/Config/test-config.properties')

    def props = [:]

config.split('\n').each { line ->

    line = line.trim()

    if (!line || line.startsWith("#")) {
        return
    }

    def parts = line.split("=",2)

    if(parts.size()==2){
        props[parts[0].trim()] = parts[1].trim()
    }
}

    def testList = props[testType]

    if (!testList) {
        error "No configuration found for ${testType}"
    }

    def tests = []

    testList.split(",").each { name ->

        name = name.trim()

        tests << [
            name     : name,
            script   : props["${name}_SCRIPT"],
            duration : props["${name}_DURATION"],
            users    : props["${name}_USERS"]
        ]
    }

    return tests
}

return this