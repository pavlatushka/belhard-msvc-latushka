## SongCloud application

### Installation
1. Download and install [JDK 17](https://www.oracle.com/java/technologies/downloads/)
2. Download and install [GIT](https://git-scm.com/downloads)
3. Download and install [Docker Engine](https://docs.docker.com/engine/install/)
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
6. Start application
    ```bash
    docker compose up
    ```
