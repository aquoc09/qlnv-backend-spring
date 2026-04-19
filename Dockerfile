# ===== STAGE 1: Build =====
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .

# Fix quyền mvnw
RUN chmod +x mvnw

# Build jar
RUN ./mvnw clean package -DskipTests


# ===== STAGE 2: Run =====
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Mặc định sử dụng profile prod khi deploy container. 
# Bạn có thể override bằng docker run -e SPRING_PROFILES_ACTIVE=local tên-image
ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "app.jar"]