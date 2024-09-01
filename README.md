## SongCloud application

### Installation
1. Download and install [JDK 22](https://www.oracle.com/java/technologies/downloads/)
2. Download and install [GIT](https://git-scm.com/downloads)
3. Download and install [Docker Engine](https://docs.docker.com/engine/install/)
4. Run two docker containers with PostgreSQL databases from Docker Hub image. 
   - First container with Resource Service database:
      ```bash
      docker run --name pg-resource -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resource -d -p:54321:5432 postgres
      ```
   - The second one with Song Service database:
     ```bash
     docker run --name pg-song -e POSTGRES_PASSWORD=root -e POSTGRES_DB=song -d -p:54321:5432 postgres
     ```

4. Clone the project:
   ```bash
   git clone https://github.com/pavlatushka/belhard-msvc-latushka.git
   ```

5. Build executable jars using gradlew.
   - Open terminal from cloned project folder
   - Run build task:
      ```bash
      gradlew build
      ```
     Take two builds at:
     ```
     {PROJECT_FOLDER}\resource-service\build\libs\resource-service-0.0.1-SNAPSHOT.jar
   
     {PROJECT_FOLDER}\song-service\build\libs\song-service-0.0.1-SNAPSHOT.jar
     ```
6. Start services
    ```bash
    java -jar resource-service-0.0.1-SNAPSHOT.jar
    ```
    ```bash
    java -jar song-service-0.0.1-SNAPSHOT.jar
    ```