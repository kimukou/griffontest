package org.codehaus.griffon.mercurial.service


public class MercurialService {
    String mercurialExecutable

    File rootDirectory

    def status(String directory = null, Boolean recursive = true) {
        println"Status in ${rootDirectory} --> $mercurialExecutable status -A ${directory ?: ''}"
        String status = "$mercurialExecutable status -A ${directory ?: ''}".execute(null, rootDirectory).text
        println "status $status  "
        return parseStatus(status, recursive)
    }

    def parseStatus(String status, recursive) {
        if( status.startsWith("abort:") ) throw new Exception(status)

        def parsed = [:]
        status.eachLine {statusLine ->
            def statusCode = statusLine[0..0]
            def fileAffected = statusLine[2..-1].split(File.separator)
            if( fileAffected.size() == 1 ) {
                parsed[fileAffected[0]] = new MercurialFile(name: fileAffected[0], status: statusCode, directory: '')
            } else if( recursive ) {
                parsed[fileAffected[-1]] = new MercurialFile(name: fileAffected[-1], status: statusCode, directory: fileAffected[0..-2].join(File.separator))
            }
        }
        return parsed
    }
}
