FROM busybox

ADD ./  /
RUN ls -l
RUN ln /inspectit-ocelot-agent.jar /javaagent.jar &&\
    chmod -R go+r /javaagent.jar

ENTRYPOINT ["sh", "/entrypoint.sh"]