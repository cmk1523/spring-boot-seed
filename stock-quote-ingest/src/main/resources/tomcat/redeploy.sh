/Users/chris/Downloads/apache-tomcat-8.5.23/bin/shutdown.sh;
rm -rf /Users/chris/Downloads/apache-tomcat-8.5.23/webapps/ROOT;
rm -rf /Users/chris/Downloads/apache-tomcat-8.5.23/webapps/ROOT.war;
cp /Users/chris/Dropbox/Github/spring-boot-seed/target/ROOT.war Users/chris/Downloads/apache-tomcat-8.5.23/webapps/;
/Users/chris/Downloads/apache-tomcat-8.5.23/bin/startup.sh;
tail -f /Users/chris/Downloads/apache-tomcat-8.5.23/logs/catalina.out;