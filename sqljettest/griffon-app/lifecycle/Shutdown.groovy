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

//終了時のログバックアップ処理
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


execOutside { 
	srcDir="logs"
	println "srcDir=${srcDir}"
	File[] files = new File("${srcDir}").listFiles(logfilter);
	println files

	st_time = new Date()
	files.each{
		if( it.exists()){
				//println "${it} / ${srcDir}/${it.name}.${st_time.format("yyyy-MM-dd_HH-mm-ss")}"
				//it.renameTo(new File("${srcDir}/${it.name}.${st_time.format("yyyy-MM-dd_HH-mm-ss")}") )
				new File("${srcDir}/${it.name}.${st_time.format("yyyy-MM-dd_HH-mm-ss")}") << it.readBytes()
				//it.delete()
		}
	}
}

