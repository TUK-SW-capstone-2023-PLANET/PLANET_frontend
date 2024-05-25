## 개요
대학생을 타겟층으로 환경 보존 및 건강 증진을 위한 플로깅 서비스

플로깅이란?
> 조깅을 하면서 길가의 쓰레기를 수거하는, 체육활동과 자연보호활동이 합쳐진 개념으로 스웨덴어에서 '줍다' 를 뜻하는 플로카 우프(Plocka Upp)[1]와 영어단어 조깅(jogging)이 합쳐졌다.


## 디자인 시안
[Figma](https://www.figma.com/design/dr822h0CiI70MiO0Tj9hxF/Project_PLANET?node-id=0-1&t=sR8qCeAZXWQftb1L-0)를 통해 UX/UI를 설계했습니다.
<br> 

<img width="784" alt="Screenshot 2024-05-26 at 2 39 12 AM" src="https://github.com/DaeYoungee/CodingTest/assets/121485300/a96a3b90-88d3-4968-97ec-a31f700bf8cb">

## 기술 스택
```
- Kotlin Jetpack Compose
- MVVM
- Coroutine + Flow
- Retrofit2
- Hilt
```

## 새롭게 도입한 요소 
프로젝트가 커져갈 수 록 ViewModel에 모든 기능이 모여있는 점이 복잡해 보였고 가독성이 떨어지는 점을 느껴습니다.  
어떤 기능을 하는지 명확한 이름을 가진 UseCase를 알게되었고 UseCase를 도입하면서 자연스럽게 모듈의 구조 변경의 필요성을 느꼈습니다.  
따라서 bob의 **Clean Architecture**를 적용하여 모듈을 'data', 'domain', 'presentation', 'di'로 구분하여 모듈간의 종속성을 줄이고자 하였습니다.
<br> 

## 모듈 구조
```
.
├── data
│   ├── remote
│   │   ├── api
│   │   └── dto
│   └── repository
├── di
├── domain
│   ├── repository
│   └── usecase
├── presentation
│   ├── ui
│   │   ├── navigation
│   │   ├── screen
│   │   └── component
└── └── viewmodel
```

- data : 데이터 소스(DB, 서버 등)와 상호작용을 담당합니다.(domain에 의존합니다.)
  - remote: 데이터의 입출력을 정의합니다.(remote 서버와만 상호작용 하기 때문에 datasource 대신 remote로 정의하였습니다.
  - repository: usecase가 필요로 하는 데이터의 저장 및 수정 등의 기능을 제공합니다. remote의 api 인터페이스를 참조하여 네트워크 통신이 이루어집니다.

- di: retrofit, repository, sharedPreference의 의존성을 주입하고 관리합니다.
- domain: 앱의 핵심적인 비즈니스 로직이 구현되어 있습니다.(어떠한 모듈에도 의존하지 않습니다.)
  - repository: repository의 인터페이스를 정의합니다.
  - usecase: 비즈니스 로직을 수행하는 객체입니다.
- presentation: UI, UI와 관련된 컴포넌트를 정의합니다.

## UI 설명

### 메인 화면
<img src="https://github.com/DaeYoungee/CodingTest/assets/121485300/6b54aeb2-c473-46ff-be19-d9c84d0aaf5e" width="400">
<img src="https://github.com/DaeYoungee/CodingTest/assets/121485300/3071279f-d867-458c-b27d-d55e0daa5f4c" width="400">
<img src="https://github.com/DaeYoungee/CodingTest/assets/121485300/c3516782-57cd-4f16-a6e5-1fc07dcee67d" width="400">


### 랭킹 화면
![image](https://github.com/TUK-SW-capstone-2023-PLANET/PLANET_frontend/assets/121485300/f995392f-3797-4078-bd63-f508c7042eeb)
![image](https://github.com/DaeYoungee/CodingTest/assets/121485300/82eb6726-b85b-4086-aef7-18e8583b1b31)

### 유저 정보 화면
![image](https://github.com/DaeYoungee/CodingTest/assets/121485300/558e1afa-4de5-4cbf-9850-3d18f37b6554)

### 커뮤니티 화면
![image](https://github.com/DaeYoungee/CodingTest/assets/121485300/24b6e7ab-752a-411c-9378-07f7bcdb66ce)
![image](https://github.com/DaeYoungee/CodingTest/assets/121485300/b03a70f6-0bb0-4c36-b302-72c4e0fc2b82)

### 쪽지 화면
![image](https://github.com/DaeYoungee/CodingTest/assets/121485300/f90bb9c1-1734-4b15-9230-ce8188ec9a01)
