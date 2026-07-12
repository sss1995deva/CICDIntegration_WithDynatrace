prepareWorkspace()

runJMeter(
    String jmxFile,
    String resultFile
)

generateHtml(
    String resultFile,
    String reportFolder
)

zipReport(
    String reportFolder,
    String zipName
)

archiveReport(
    String zipName
)

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

return this