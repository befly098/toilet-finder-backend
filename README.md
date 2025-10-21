# 🚽 똥칸 찾기 — Backend (Spring Boot, Java 23)

> Minimum Viable Product (MVP) API 서버

## ✨ Tech Stack
- **Java 23** (Toolchain)
- **Spring Boot 3.3.x** — Web, Validation, Cache(Caffeine)
- **PostgreSQL** (Neon 등 무료 티어 권장)
- **OpenAPI (springdoc)** — /swagger-ui/index.html
- Build: **Gradle**
- Deploy: Render/Fly.io/Koyeb (무료 플랜)

## 📁 Project Structure (초안)
```
backend/
 ├─ src/main/java/com/toiletfinder/
 │   ├─ api/            # controllers
 │   ├─ domain/         # entities, dtos
 │   ├─ service/        # business logic
 │   ├─ repo/           # repositories
 │   └─ config/         # config, CORS, cache
 ├─ src/main/resources/
 │   ├─ application.yaml
 │   └─ data/toilet_sample.csv
 └─ build.gradle
```

## ⚙️ Prerequisites
- JDK 23 (toolchain로 자동 설치 가능)
- Gradle wrapper
- PostgreSQL (Neon/Supabase/Local)

## 🔐 Environment
`src/main/resources/application.yaml` 예시:
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:toilet}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:postgres}
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        jdbc:
          time_zone: UTC
  cache:
    type: caffeine

caffeine:
  spec: maximumSize=1000,expireAfterWrite=10m

app:
  cors:
    allowed-origins: ${ALLOWED_ORIGINS:http://localhost:5173}
```

필수 ENV (배포 환경):
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
- `ALLOWED_ORIGINS` (프론트 도메인)

## 🚀 Run (Local)
```bash
./gradlew bootRun
# health check
curl http://localhost:8080/api/health
```

## 🧭 API (초안)
- `GET /api/health` — 상태 확인
- `GET /api/toilets?lat&lng&radius=1000&limit=30` — 근처 화장실 목록 (거리순)
- `GET /api/toilets/{id}` — 상세
- `POST /api/ratings` — 별점(1~5), 디바이스/화장실 1회
- `POST /api/reports` — 제보(정보/혼잡도)

OpenAPI UI: `/swagger-ui/index.html`  
OpenAPI JSON: `/v3/api-docs`

## 🗄️ DB Schema (요약)
- **toilet**: 기본 정보 + `rating_sum`, `rating_count`
- **rating**: `toilet_id`, `score`, `device_hash`, `ip_hash`
- **report**: `toilet_id`, `type(INFO|CROWD)`, `payload`

> 위치 검색은 Haversine 쿼리로 시작 → 필요 시 PostGIS로 확장

## 🛡️ Policies
- **Rate limit**: IP/일 제한 (간단 필터)
- **Dedup**: `rating (toilet_id, device_hash)` UNIQUE
- **CORS**: 화이트리스트 기반

## 🧪 Test
```bash
./gradlew test
```

## 📦 Build
```bash
./gradlew clean build
java -jar build/libs/*.jar
```

## ☁️ Deploy (예: Render)
- Build Command: `./gradlew bootJar`
- Start Command: `java -jar build/libs/*SNAPSHOT.jar`
- ENV: DB_* / ALLOWED_ORIGINS 세팅

## 📝 License
MIT (변경 가능)
