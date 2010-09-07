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

//===============================================================================================

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


//===============================================================================================


//Look abd Feel
import groovy.swing.SwingBuilder
import griffon.util.GriffonPlatformHelper
import static griffon.util.GriffonApplicationUtils.*

GriffonPlatformHelper.tweakForNativePlatform(app)
SwingBuilder.lookAndFeel((isMacOSX ? 'system' : 'nimbus'), 'gtk', ['metal', [boldFonts: false]])

//===============================================================================================

//スプラッシュスクリーンの設定
def splashScreen = SplashScreen.getInstance()

// Setting a splash image
//URL url = this.class.getResource("mySplash.jpg")
//splashScreen.setImage(url)
//
// Setting Status Text
// SplashScreen.getInstance().showStatus("Initializing the Controller")
splashScreen.showStatus('初期化中')
splashScreen.splash()
splashScreen.waitForSplash()

