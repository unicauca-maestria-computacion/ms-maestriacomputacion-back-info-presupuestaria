# =============================================================
# Imagen del microservicio de Informacion Presupuestaria.
#
# Construccion en dos etapas. La primera compila el proyecto con el
# envoltorio de Maven incluido en el repositorio, de modo que la imagen
# no depende de que haya Maven instalado en la maquina. La segunda parte
# de una imagen que contiene unicamente el entorno de ejecucion de Java,
# sin compilador ni herramientas de construccion, y copia en ella el
# artefacto resultante.
#
# La separacion reduce el tamano de la imagen final y su superficie
# expuesta: lo que llega al servidor es el minimo necesario para
# ejecutar la aplicacion.
# =============================================================

FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Las dependencias se descargan en una capa propia, anterior a la copia
# del codigo fuente. Mientras el pom.xml no cambie, Docker reutiliza esa
# capa y las construcciones sucesivas no vuelven a descargarlas.
COPY mvnw mvnw.cmd* ./
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -q

COPY src ./src
RUN ./mvnw package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine AS runtime

# La aplicacion se ejecuta con un usuario sin privilegios: si un fallo
# permitiera ejecutar codigo dentro del contenedor, este no dispondria
# de permisos de administracion sobre el sistema de archivos.
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar
USER appuser

EXPOSE 8094
ENTRYPOINT ["java", "-jar", "app.jar"]
