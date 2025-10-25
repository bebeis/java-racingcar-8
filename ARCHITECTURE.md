# 아키텍처 설계 문서

## MVP 패턴 적용 (Model, View, Presenter)

User -> View <- Presenter -> Model
            --> 

- View는 Presenter에 강하게 의존하지 않음.
- View는 단순히 입/출력을 담당하고, 이벤트 발생시 Presenter의 콜백 호출
