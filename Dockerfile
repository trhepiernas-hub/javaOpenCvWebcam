# Empezar con una imagen base de Maven
FROM maven:latest

# Instalar las dependencias necesarias para OpenCV
RUN apt update && apt install -y \
    build-essential \
    cmake \
    git \
    libgtk2.0-dev \
    pkg-config \
    libavcodec-dev \
    libavformat-dev \
    libswscale-dev

# Clonar el repositorio de OpenCV
RUN git clone https://github.com/opencv/opencv.git

# Crear un directorio para la compilación de OpenCV
RUN mkdir /opencv/build

# Establecer el directorio de trabajo
WORKDIR /opencv/build

# Compilar OpenCV
RUN cmake .. && make -j4 && make install

# Establecer el directorio de trabajo al directorio raíz
WORKDIR /

# Copiar el proyecto Maven al contenedor
COPY ./vigilancesistem /usr/src/app

# Establecer el directorio de trabajo al directorio del proyecto
WORKDIR /usr/src/app

# Compilar el proyecto Maven
RUN mvn package

# Ejecutar el archivo JAR de Java
CMD ["java", "-jar", "target/vigilancesistem-1.0-SNAPSHOT.jar"]