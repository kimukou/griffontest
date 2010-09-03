// log4j configuration
log4j {
    appender.stdout = 'org.apache.log4j.ConsoleAppender'
    appender.'stdout.layout'='org.apache.log4j.PatternLayout'
    appender.'stdout.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.errors = 'org.apache.log4j.FileAppender'
    appender.'errors.layout'='org.apache.log4j.PatternLayout'
    appender.'errors.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.'errors.File'='stacktrace.log'
    rootLogger='error,stdout'
    logger {
        griffon='error'
        StackTrace='error,errors'
        org {
            codehaus.griffon.commons='info' // core / classloading
        }
    }
    additivity.StackTrace=false
}

//■lookandfeel
// サンプル：http://www.jtattoo.net/ScreenShots.html
lookandfeel.lookAndFeel = 'JTattoo'

//黒
lookandfeel.theme = 'HiFi'
//lookandfeel.theme = 'Noire'

//lookandfeel.theme = 'Acrylic'
//lookandfeel.theme = 'Aero'
//lookandfeel.theme = 'Aluminium'
//lookandfeel.theme = 'Bernstein'
//lookandfeel.theme = 'Fast'
//lookandfeel.theme = 'Graphite'
//lookandfeel.theme = 'Luna'

//Mac風
//lookandfeel.theme = 'McWin'
//薄青
//lookandfeel.theme = 'Mint'
//グレー
//lookandfeel.theme = 'Smart'

lookandfeel.keystroke = 'shift meta L'

