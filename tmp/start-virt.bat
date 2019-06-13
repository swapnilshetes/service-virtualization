@ECHO OFF
SET root=%cd%
SET mainroot= %root%%b%
java -jar  %mainroot%\wiremock-standalone-2.23.2.jar --port=8112