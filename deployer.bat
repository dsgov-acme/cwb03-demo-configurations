@echo off

call gradlew.bat shadowJar > nul

java -jar configuration-deployer/build/libs/configuration-deployer.jar %*
