FROM amazoncorretto:17-alpine
RUN mkdir -p /opt/laa-crime-validation/
WORKDIR /opt/laa-crime-validation/
COPY ./build/libs/crime-validation.jar /opt/laa-crime-validation/app.jar
RUN addgroup -S appgroup && adduser -u 1001 -S appuser -G appgroup
USER 1001
EXPOSE 8390 8400
ENTRYPOINT ["java","-jar","app.jar"]
