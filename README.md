## SongCloud application

Local ports usage:
```bash
  - Resource Service: 8081
  - Service Discovery: 8761
  - Resource Service DB: 54321
  - Song Service DB: 54322
  - Object storage (MinIO): 9000 and 9001
```

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