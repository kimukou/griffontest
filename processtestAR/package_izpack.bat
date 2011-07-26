call ../setEnvG9.bat

::call griffon clean
::griffon package izpack
call griffon prepare-izpack
call griffon create-izpack

