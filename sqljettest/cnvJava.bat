call ../setEnvG9.bat

call griffon replace-artifact -fileType=java -type=controller sqljettest.SqljettestController
call griffon replace-artifact -fileType=java -type=service sqljettest.SqljettestService
call griffon replace-artifact -fileType=java -type=model sqljettest.SqljettestModel
