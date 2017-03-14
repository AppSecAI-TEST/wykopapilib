# wykopapilib
WykopAPI Java Library

How to run example:

You need to have: JDK+JRE 8, git (optional)

1. git clone https://github.com/awizisieakat/wykopapilib OR download and extract zip https://github.com/awizisieakat/wykopapilib/archive/master.zip
2. Modify Example.java class to use your own properties file location or provide app key and secret directly via SimplePropertiesService
3. Go to main directory
4. Build application using gradle:
  - windows: gradlew fatJar
  - linux: ./gradlew fatJar
5. Run application:
  - java -cp build/libs/wykopapilib-all-1.0-SNAPSHOT.jar wykopapilib.examples.SaveVotersAvatar
