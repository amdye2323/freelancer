## 프로젝트 개요
이 프로젝트는 프리랜서 프로필 조회 서비스로, 프리랜서 프로필 목록을 조회하고, 상세 정보를 업데이트하는 기능을 제공합니다. Spring Boot와 JPA, QueryDSL을 활용하여 구현하였으며, Redis를 캐시 시스템으로 사용하여 성능을 개선하였습니다.

## API

### 1. 프리랜서 프로필 목록 조회 (페이징)
- **URL**: `/api/freelancer/profile/paged`
- **Method**: `GET`
- **Request Parameters**:
    - `searchFreelancerSortingType` (required): **enum** - 정렬 기준 (이름, 조회수, 등록순 등)
    - `startId` (required): **long** - 시작 ID
    - `size` (required): **long** - 페이지 크기 (조회할 프로필 수)
- **Response**:
    - **Status Code**: 200 OK
    - **Body**: `PageDto<FreelancerProfileDto>` - 페이지 정보와 프리랜서 프로필 목록
    - **Example**:
      ```json
      {
        "data": [
          {
            "id": 1,
            "name": "테스터",
            "viewCount": 10,
            "createdAt": "2024-10-01T10:00:00"
          }
        ],
        "totalSize": 1,
        "pageSize": 10,
        "startId": 0
      }
      ```

### 2. 프리랜서 프로필 조회수 증가
- **URL**: `/api/freelancer/profile/detail`
- **Method**: `PUT`
- **Request Body**:
    - **Example**:
      ```json
      {
        "id": 1
      }
      ```
- **Response**:
    - **Status Code**: 200 OK
    - **Body**: `FreelancerProfileDto` - 업데이트된 프리랜서 프로필 정보
    - **Example**:
      ```json
      {
        "id": 1,
        "name": "테스터",
        "viewCount": 11,
        "createdAt": "2024-10-01T10:00:00"
      }
      ```

## Redis 사용 이유
Redis는 인메모리 데이터 구조 저장소로, 높은 성능과 빠른 응답 속도를 제공합니다. 이 프로젝트에서 Redis를 사용한 이유는 다음과 같습니다:

1. **성능 향상**: 자주 조회되는 데이터를 Redis에 캐시하여 데이터베이스 조회를 줄이고, 애플리케이션의 응답 속도를 개선합니다.
2. **스케일링 용이성**: Redis는 수평 확장이 용이하여, 트래픽이 증가할 경우 쉽게 확장할 수 있습니다.
3. **데이터 일관성**: Redis를 통해 조회수 업데이트 시 캐시와 데이터베이스 간의 일관성을 유지할 수 있습니다.

추가적으로 redisson 사용했는데 사용한 이유는 해당 라이브러리는 공유자원의 대한 락의 분배를 낙관적으로 사용할 때 키 획득&해제 관련을 좀더 쉽게 사용가능하게 만들어주는 라이브러리라 사용했습니다.

## 프로젝트 구조

````
├── project-root
│   ├── common                           # 공통 유틸리티 및 상수
│   ├── config                           # 설정 관련 패키지 (Redis 설정 등)
│   ├── controller                       # API 요청을 처리하는 컨트롤러 계층
│   ├── domain                           # 도메인 계층
│   │   └── freelancer
│   │       ├── entity                   # JPA 엔티티 클래스
│   │       ├── repository               # 데이터베이스 접근 계층 (Repository)
│   │       ├── service                  # 비즈니스 로직 처리 계층 (Service)
│   │       ├── payment                  # 결제 관련 서비스 계층
│   ├── dto                              # 데이터 전송 객체 (DTO)
│   ├── enums                            # Enum 타입 정의
│   └── factory                          # 팩토리 패턴 관련 클래스
````