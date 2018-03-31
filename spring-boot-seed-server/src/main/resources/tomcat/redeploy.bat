CMD /C ...\apache-tomcat-8.5.24\bin\shutdown.bat;
rd /s /q "...\apache-tomcat-8.5.24\webapps\ROOT";
del "...\apache-tomcat-8.5.24\webapps\ROOT.war";
copy "...\spring-boot-seed\spring-boot-seed-client\target\ROOT.war" "...\apache-tomcat-8.5.24\webapps\";
...\apache-tomcat-8.5.24\bin\startup.bat;