import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

import com.solab.alarms.channels.*
import com.solab.alarms.channels.twitter.*

import com.googlecode.jsendnsca.core.*  //sinse 1.3.1
//import com.googlecode.jsendnsca.*     //2.0.1 not running. NagiosPassiveCheckChannel is depency ?
// see https://jsendnsca.googlecode.com/svn/jsendnsca-docs/javadocs-2.0.1/index.html



beans = {
	//println griffon.util.ApplicationHolder.application.config.props.dump()
	def config = new ConfigSlurper().parse(griffon.util.ApplicationHolder.application.config.props)


  consoleChannel(TestChannel)

  nagiosSettings(NagiosSettings) {
    nagiosHost = config.nagiosSettings.nagiosHost
		port=5667
    password = config.nagiosSettings.password
    encryptionMethod = NagiosSettings.TRIPLE_DES_ENCRYPTION
    timeout =5000
  }


  nagiosChannel(NagiosPassiveCheckChannel) {
    settings = ref(nagiosSettings)
    hostname = config.nagiosChannel.hostname
    sources = [Test:'Alfresco Share connection']
  }

  //https://dev.twitter.com/apps/
  twitterChannel(TwitterChannel){
		accessToken=config.twitterChannel.accessToken
		tokenSecret=config.twitterChannel.tokenSecret
	}

  mailTemplate(SimpleMailMessage){
    subject = "My server application ALARM"
    text = "My application has send this ALARM:"
    //text = """
    //  My application has send this ALARM: ${source} 
    //  This notification has been automatically sent, do not reply.
    //"""
    from =config.mailTemplate.from
    to = config.mailTemplate.to
  }

  javaMailSender(org.springframework.mail.javamail.JavaMailSenderImpl){
      host=config.javaMailSender.host
      port=config.javaMailSender.port
      username=config.javaMailSender.username
      password=config.javaMailSender.password
      javaMailProperties =["mail.smtp.auth":true]
  }

  mailChannel(MailChannel){
    javaMailSender=ref(javaMailSender)
    mailTemplate = ref(mailTemplate)
  }
  
  //CommandLineChannel
  //HttpChannel
  httpChannel(HttpChannel){
    url="http://localhost:8080"
    postData="あいうえお"
  }

  alarmSender(com.solab.alarms.AlarmSender){
		alarmChannels=[consoleChannel,mailChannel]
  }

  //AOP test
  // see http://jalarms.sourceforge.net/aop.html
  alarmAspect(com.solab.alarms.aop.AlarmAspect){
    alarmSender=ref(alarmSender)
    message="This is the alarm message that precedes the exception"
    includeStackTrace=2
  }

}
// see http://jalarms.sourceforge.net/guide.html
/*
Here is an example configuration for the MailChannel:
    <!-- this channel will send alarms to the recipients defined in the mail template -->
    <bean id="alarmMailChannel" class="com.solab.alarms.channels.MailChannel">
            <property name="javaMailSender" ref="javaMailSender" />
            <property name="mailTemplate">
              <bean class="org.springframework.mail.SimpleMailMessage">
                      <property name="subject" value="My server application ALARM" />
                      <property name="from" value="do-not-reply@mydomain.com" />
                      <property name="text">
                        <value>My application has send this ALARM: ${msg} 
                        This notification has been automatically sent, do not reply.
                        </value>
                      </property>
                      <!-- This is obviously very important -->
                      <property name="to" value="sysadmin@mydomain.com" />
              </bean>
            </property>
    </bean>

And here's an example of the MsnChannel
    <!-- this channel will send alarms to everyone in the account's contact list -->
    <bean id="alarmMsnChannel" class="com.solab.alarms.channels.MsnChannel">
            <property name="username" value="someMsnUsername" />
            <property name="password" value="password" />
    </bean>

And here's an example of the TwitterChannel:
    <!-- this channel will post the alarms as the status in the Twitter account -->
    <bean id="twitterChannel" class="com.solab.alarms.channels.twitter.TwitterChannel">
            <property name="accessToken" value="AccessTokenYouGetByRunningThe_TwitterAuth_Program" />
            <property name="tokenSecret" value="SecretObtainedFromThe_TwitterAuth_programIncludedWith_jAlarms" />
    </bean>

Once you have configured the channels, all you need to do is setup the AlarmSender, 
which is the only bean you need to use from any component in your application.

    <bean id="alarmSender" class="com.solab.alarms.AlarmSender">
            <property name="alarmChannels">
            <list>
                    <ref local="alarmMailChannel" />
                    <ref local="alarmMsnChannel" />
                    <ref local="twitterChannel" />
            </list>
            </property>
    </bean>



//====AOP====

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">

<!-- This is to enable the autoproxying mechanism, so that any annotated beans will be proxied
  (this is necessary for the AlarmAspect to work) -->
<aop:aspectj-autoproxy />

<bean id="alarmAspect" class="com.solab.alarms.aop.AlarmAspect">
  <!-- If you only have one AlarmSender configured and it's called "alarmSender",
       then you don't need to specify this -->
  <property name="alarmSender" ref="alarmSender" />
  <property name="message" value="This is the alarm message that precedes the exception" />
  <!-- You can set this to 0 to omit the stack trace, -1 to include the full stack trace,
    or any other number to only include that number of lines of stack trace. -->
  <property name="includeStackTrace" value="2" />
</bean>

</beans>



import com.solab.alarms.aop.AlarmOnException

@AlarmOnException(message="This is the alarm message", source="some-src", stack=5)
public class MyClass {

  public void doSomething() {
    //If an exception is thrown here, an alarm is sent
  }

  public void doSomethingElse() {
    //An exception thrown here also sends an alarm.
  }
}

public class AnotherClass {

  public void doSomething() {
    //No alarm is sent here
  }

  @AlarmOnException
  public void doSomethingElse() {
    //An alarm is sent in case of an exception, with the message that
    //was configured in the aspect, no source, and the number of
    //stack trace lines configured in the aspect.
  }
}
*/







