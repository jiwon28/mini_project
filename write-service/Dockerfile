FROM eclipse-temurin:17-jdk

# 빌드 컨텍스트: write-service 디렉터리. 먼저 Gradle bootJar를 실행합니다.
WORKDIR /app

# 버전 변경 시 --build-arg JAR_FILE=build/libs/<실행 JAR 이름>으로 지정합니다.
# plain JAR이 함께 복사되지 않도록 와일드카드는 사용하지 않습니다.
ARG JAR_FILE=build/libs/write-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 애플리케이션은 root 권한 없이 실행합니다.
USER 10001:10001

# 포트 공개에 대한 메타데이터입니다. 실제 연결에는 docker run -p가 필요합니다.
EXPOSE 7000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
