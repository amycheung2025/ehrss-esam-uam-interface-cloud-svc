FROM artifactrepo.server.ha.org.hk:55743/docker-rel-ehrss/ecpimage/openjdk17:ecp-v24.05-openjdk17-17.0.11-eapm-1.49.0-conjur-13.0

ENV TZ=Asia/Hong_Kong \
    JAR_OPTS="-Dloader.path=."
WORKDIR /usr/local/cloud

USER root
COPY /deploy/script/start.sh /usr/local/cloud/bin/start.sh
COPY /deploy/test.txt /usr/local/cloud/test.txt
RUN chmod a+rx /usr/local/cloud/bin/start.sh
COPY /target/*.jar /webapps/application.jar
USER jboss
CMD ["bin/start.sh"]
