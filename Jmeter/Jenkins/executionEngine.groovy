import java.util.Properties

def prepareWorkspace() {

    echo "======================================"
    echo "Preparing Execution Workspace..."
    echo "======================================"

    sh '''        
        mkdir -p Jmeter/Scripts/Results/${env.BUILD_NUMBER}
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

    def lines = config.split("\\r?\\n")

    for (String line : lines) {

        line = line.trim()

        if (line == "" || line.startsWith("#")) {
            continue
        }

        def parts = line.split("=", 2)

        if (parts.length == 2) {
            props[parts[0].trim()] = parts[1].trim()
        }
    }

    def testList = props[testType]

    if (!testList) {
        error "No configuration found for ${testType}"
    }

    def tests = []

    for (String name : testList.split(",")) {

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

def executeParallel(def tests) {

    def branches = [:]

    tests.each { test ->

        branches[test.name] = {

            echo "Executing : ${test.name}"

            runJMeter(
                test.script,
                test.resultFile
            )
        }
    }

    parallel branches
}

def executeSequential(def tests) {

    tests.each { test ->

        echo "Executing : ${test.name}"

        runJMeter(
            test.script,
            test.resultFile
        )
    }
}


def prepareTestArtifacts(def tests) {

    tests.each { test ->
	
	test.resultRoot   = "Scripts/Results/${env.BUILD_NUMBER}"
	test.resultFile   = "${test.resultRoot}/${test.name}.jtl"
	test.reportFolder = "${test.resultRoot}/${test.name}_HTML"
	test.zipFile      = "${test.resultRoot}/${test.name}.zip"
	
    }
}

def generateReports(def tests) {

    tests.each { test ->

       if (fileExists("Jmeter/${test.resultFile}") &&
    sh(script: "test -s Jmeter/${test.resultFile}", returnStatus: true) == 0)) {

            generateHtml(
                test.resultFile,
                test.reportFolder
            )

        } else {

            echo "WARNING : ${test.resultFile} not found. Skipping HTML generation."
        }
    }
}

def zipReports(def tests) {

    tests.each { test ->

        if (fileExists("Jmeter/${test.reportFolder}/index.html")) {

            zipReport(
                test.reportFolder,
                test.zipFile
            )

        } else {

            echo "WARNING : ${test.reportFolder} not found. Skipping ZIP."
        }
    }
}

def generateHtml(String resultFile, String reportFolder) {

    echo "Generating HTML Report : ${reportFolder}"

    sh """
        cd Jmeter

        rm -rf ${reportFolder}

        jmeter \
        -g ${resultFile} \
        -o ${reportFolder}
    """
}

def zipReport(String reportFolder, String zipFile) {

    sh """
        cd Jmeter

        rm -f ${zipFile}

        zip -r ${zipFile} ${reportFolder}
    """
}

return this