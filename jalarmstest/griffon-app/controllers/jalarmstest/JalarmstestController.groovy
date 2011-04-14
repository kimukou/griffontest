package jalarmstest

import com.solab.alarms.aop.AlarmOnException

//@AlarmOnException(message="This is the alarm message", source="some-src", stack=5)
class JalarmstestController {
    // these will be injected by Griffon
    def model
    def view

    // void mvcGroupInit(Map args) {
    //    // this method is called after model and view are injected
    // }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }

    def consoleChannel
    def nagiosChannel
    def twitterChannel
    def mailChannel
    def httpChannel
    def alarmSender


    def consoleAction ={
        println "------------------------"
        println consoleChannel.dump()
        println "------------------------"
        consoleChannel.send('Test application alertあいうえお', 'Test')
        //sendAlarm('Test application alert', 'Test')
    }

    def nagiosAction ={
        println "------------------------"
        println nagiosChannel.dump()
        println "------------------------"
        nagiosChannel.send('Test application alertあいうえお', 'Test')
        //sendAlarm('Test application alert', 'Test')
        println nagiosChannel.dump()
    }

    @AlarmOnException
    void twitterAction(){
    //def twitterAction={
        println "------------------------"
        twitterChannel.init()  //Not Call java.lang.NullPointerException  at com.solab.alarms.channels.twitter.TwitterChannel.sign
        println twitterChannel.dump()
        println "------------------------"
        //try{
          twitterChannel.send('Test application alertあいうえお', 'Test')
        //}catch(Exception ex){
          //println "Exception $ex ${twitterChannel.dump()}"
          alarmSender.sendAlarm('Test application alertあいうえお', 'Test')
        //}
        //sendAlarm('Test application alert', 'Test')
    }

    def mailAction={
        println "------------------------"
        println mailChannel.dump()
        println "------------------------"
        mailChannel.send('Test application alertあいうえお', 'Test')
        //sendAlarm('Test application alert', 'Test')
    }

    @AlarmOnException
    void httpAction(){
    //def httpAction={
        println "------------------------"
        println httpChannel.dump()
        println "------------------------"
        httpChannel.send('Test application alertあいうえお', 'Test')
        //sendAlarm('Test application alert', 'Test')
    }

    def alarmSenderAction={
        println "------------------------"
        println alarmSender.dump()
        println "------------------------"
        alarmSender.sendAlarm('Test application alertあいうえお', 'Test')
        //sendAlarm('Test application alert', 'Test')
    }


    /*
    def action = { evt = null ->
    }
    */

    //sendAlarm(String msg) - sends an alarm to all channels
    //sendAlarm(String msg, String source) - sen
    //  sendAlarm('Test application alert', 'Test') // msg, source
}
