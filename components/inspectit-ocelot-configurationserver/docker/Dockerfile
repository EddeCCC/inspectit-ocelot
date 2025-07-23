# Use lightweight base image with OpenJDK (supports multi-platform)
FROM amazoncorretto:17-alpine

ADD ./  /
RUN ls -l
ENTRYPOINT ["sh", "/entrypoint.sh"]
