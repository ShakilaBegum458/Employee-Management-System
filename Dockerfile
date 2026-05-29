FROM tomcat:9.0-jdk21

# Remove default Tomcat webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Create ROOT webapp directory structure
RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
    /usr/local/tomcat/webapps/ROOT/WEB-INF/lib

# Copy webapp resources (HTML, CSS, JS, web.xml, JARs)
COPY src/main/webapp/ /usr/local/tomcat/webapps/ROOT/

# Copy compiled classes
COPY build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Configure Tomcat to use Railway's PORT (default 8080)
RUN sed -i 's/port="8080"/port="${PORT:-8080}"/' /usr/local/tomcat/conf/server.xml

EXPOSE ${PORT:-8080}

CMD ["catalina.sh", "run"]
