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

//スプラッシュスクリーンの設定
def splashScreen = SplashScreen.getInstance()
splashScreen.showStatus(app.getMessage("splash.initialize.onstartupend.start") )//'開始処理中')


//CSS
import griffon.builder.css.CSSDecorator
CSSDecorator.decorate("style", app.builders.'steeltest'.mainFrame)
