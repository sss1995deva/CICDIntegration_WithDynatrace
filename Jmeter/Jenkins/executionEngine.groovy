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

   def config = readFile('Jmeter/Config/test-config.properties')

   echo config	

    
}

return this