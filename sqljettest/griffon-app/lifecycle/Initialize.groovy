/*
 * This script is executed inside the UI thread, so be sure to  call 
 * long running code in another thread.
 *
 * You have the following options
 * - execOutside { // your code }
 * - execFuture { // your code }
 * - Thread.start { // your code }
 *
 * You have the following options to run code again inside the UI thread
 * - execAsync { // your code }
 * - execSync { // your code }
 */

 //FileLock
 import java.io.*
 import java.nio.channels.FileChannel
 import java.nio.channels.FileLock
  
 //起動チェック
 final FileOutputStream fos = new FileOutputStream(new File("lock"))
 final FileChannel fc = fos.getChannel()
 final FileLock lock = fc.tryLock()
 if (lock == null) {
	 //既に起動されているので終了する
	 System.exit(0)
	 return
 }
 //ロック開放処理を登録
 Runtime.getRuntime().addShutdownHook(
	 new Thread() {
		 public void run() {
			 if (lock != null && lock.isValid()) {
				 lock.release()
			 }
			 fc.close()
			 fos.close()
		 }
	 }
 )


//startup logfile delete action
import java.io.File;
import java.io.FilenameFilter;

FilenameFilter logfilter = new FilenameFilter() {
	public boolean accept(File dir, String name) {
		if (name.endsWith(".log") ) {
			return true;
		}
		return false;
	}
}

srcDir="logs"
println "srcDir=${srcDir}"
File[] files = new File("${srcDir}").listFiles(logfilter);
println files

st_time = new Date()
files.each{
	it.delete()
}


import groovy.swing.SwingBuilder
import griffon.util.GriffonPlatformHelper
import static griffon.util.GriffonApplicationUtils.*

GriffonPlatformHelper.tweakForNativePlatform(app)
SwingBuilder.lookAndFeel((isMacOSX ? 'system' : 'nimbus'), 'gtk', ['metal', [boldFonts: false]])


//Exceptionをロガーでキャッチできるようにする設定
import groovy.swing.SwingBuilder
import griffon.util.GriffonPlatformHelper
import static griffon.util.GriffonApplicationUtils.*

if(griffon.util.RunMode.current==griffon.util.RunMode.STANDALONE && new File('setting/log4j.xml').exists()){
	org.apache.log4j.xml.DOMConfigurator.configure("setting/log4j.xml")
}
else{
	org.apache.log4j.BasicConfigurator.configure()
}

import org.apache.log4j.Logger
import org.codehaus.groovy.runtime.StackTraceUtils
import java.lang.Thread.UncaughtExceptionHandler

class LoggingExceptionHandler implements UncaughtExceptionHandler {
 private static Logger logger = Logger.getLogger(LoggingExceptionHandler.class)

 public void uncaughtException(Thread t, Throwable e)
 {
   logger.error("Uncaught exception ${e.message}", StackTraceUtils.deepSanitize(e) )
 }
}

Thread.setDefaultUncaughtExceptionHandler(new LoggingExceptionHandler());
System.setProperty("sun.awt.exception.handler",LoggingExceptionHandler.class.getName())



//グローバルアクセス参照用ポインタ設定
griffon.util.ApplicationHolder.application = app
