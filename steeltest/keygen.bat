call ../setEnvG9.bat

echo "android type keyStorePassword and  keyAliasPassword"
keytool -genkey -alias androiddebugkey -keystore debug.keystore

