package quartztest


class MyJob {
    static triggers = {
        cron name: 'MyJob', cronExpression: "0/1 * * * * ?"
    }

    String group = "MyGroup"

    void execute() { println "Job MyJob: ping! ${new Date()}" } 
}
