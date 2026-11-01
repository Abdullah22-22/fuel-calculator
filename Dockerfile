FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
    libgtk-3-0 libgl1 libxtst6 libxrender1 libxi6 \
    libasound2t64 fontconfig \
    fonts-noto fonts-noto-core fonts-noto-cjk fonts-vlgothic \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/fuel-calculator-1.0-SNAPSHOT.jar app.jar

ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

ENV DISPLAY=host.docker.internal:0.0

CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]