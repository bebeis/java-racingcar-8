# 아키텍처 설계 문서

## MVC 패턴 적용 (Model, View, Controller)

- MVP와 MVC를 고민하였고, MVC를 선택
- 단 한번의 요청, 그리고 이벤트 종류가 Console Input만 존재함 -> MVC 고려

## Layer 구조

### Presentation Layer

- GameController
- Model을 호출하고 View에게 Output을 그리게 한다.

### Service Layer

- GameService
- Repository와 상호작용, 도메인 객체를 호출하여 DTO 반환

### Domain

- 도메인 규칙을 객체로 모델링하는데 집중
- 클래스가 다루는 데이터와 책임을 기준으로 설계

### Persistence Layer

- CarsMemoryRepository
- 자동차 데이터를 메모리에 저장한다.
