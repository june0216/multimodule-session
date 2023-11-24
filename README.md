# 💜 멀티모듈 실습 💜

# 💜 Module?

- 모듈은 독립적인 의미가 갖습니다.
- 모듈은 어떠한 추상화 정도에 대한 계층을 가지고 있습니다.
- 계층 간 의존 관계에 대한 규칙이 있습니다.

# 💜 Multi-module?

- 서로 독립적인 프로젝트를 하나의 프로젝트로 묶어 모듈로서 사용하는 구조입니다.
- 다른 모듈과 의존성을 가지며 연결합니다.
- 각 모듈은 독립적으로 빌드할 수 있습니다.

# 💜 common의 저주

![Untitled](https://user-images.githubusercontent.com/76603301/285415286-aae1cbae-918e-49aa-9053-6342c547e5d8.png)

Common을 변경한다는 것은 1) Common을 사용하는 모든 프로젝트에 영향을 줄 수 있음을 의미하고 2) Common을 고치면 사이드 이펙트가 발생하기 쉽기 때문에 변경을 꺼리게 됩니다.
3) Common으로 의존성 관리까지 하고 있다면 자동 구성에 의해 빌드 또는 실행에 영향을 줄 수도 있습니다. 
공통 모듈의 영향이 크지 않는 방향으로 모듈을 나누는 것이 좋습니다. 

# 💜 멀티 모듈 기준 
멀티 모듈을 나누는 기준은 정답이 없습니다. 각 상황에 맞추어 적합한 방법으로 나누는 것이 중요합니다. 

1) DDD의 바운디드 컨택스트 기준으로 나누기
   (ex) 부트 서버 그룹, 데이터 모듈, 연동 모듈(인프라), cloud system 그룹으로 나눌 수 있다. 
3) 관련 서버 기준으로 나누기
   (ex) 컨트롤러+서비스(api 서버), 레포지토리+도메인(db 서버), 배치 서버 등
4) 아키텍처 기준 분리
   (ex) 계층형 아키텍처를 사용할 경우 컨트롤러, 서비스, 레포지토리(+도메인)를 각각 다른 모듈로 분리
5) 도메인 관점으론 나누기
   (ex) 유저, 책, 주문 등으로 나누기

# 💜 예시
모듈을 "서버 기준으로" 나누게 될 경우 다음과 같이 구성해볼 수 있습니다. 
메인 로직을 수행하는 API 서버와 채팅 기능만 수행하는 채팅 서버를 따로 두었습니다. 

개인적으로 직접 느낀 장점은 프론트와 연결할 때 알 수 없는 원인으로 카프카 서버가 다운되었는데 이때, 카프카는 채팅 모듈에만 엮어있어 채팅 서버만 다운되었고 다른 서버는 정상 작동할 수 있어 프론트 작업의 지연되지않았다는 점이 좋았던 것 같습니다! 

```java
XXX / (루트 프로젝트)
│   
│
├── XXX-api/  (api 서버 모듈 : 8081)
│   └── src/
│
├── XXX-common/ (공통 기능 모듈)
│   └── src/
│
├── XXX-chat/ (chat 서버 모듈 : 8082)
│   └── src/

└── build.gradle (루트 빌드 파일)
```


# 💜 프로젝트 구조
본 실습은 멀티모듈을 직접 생성하는 데 초점을 맞추었으며 이해하기 쉬운 코드로 구성했습니다. (코드 자체는 best practice로 구성하지 않았습니다)
따라서 "아키텍처 기준으로" 다음과 같이 나누어보았습니다. 

온라인 쇼핑몰 서버를 구성한다고 가정합니다. 


```java
shop/ (루트 프로젝트)
│   
│
├── shop-api/ (서비스 애플리케이션 모듈 : 8080)
│   └── src/
│
├── shop-common/ (공통 기능 모듈)
│   └── src/
│
├── shop-domain/ (공통 도메인 로직 모듈)
│   └── src/
│
├── shop-domain-rdb/ (RDBMS 도메인 모듈)
│   └── src/
│
├── shop-domain-redis/ (Redis 도메인 모듈)
│   └── src/
│
│
└── build.gradle (루트 빌드 파일)
```

### common 
- **역할**: 전체 애플리케이션에서 공유하는 공통 기능 및 유틸리티 제공.
- **필요 구현**: 공통 유틸리티 클래스, 공통 상수, 공통 예외 처리 로직.
  여기서는 간단하게 공통 상수만 넣었습니다.

### shop-domain
- **역할**: 비즈니스 모델과 로직 정의.
- **필요 구현**: 엔티티 클래스 (Product, Order 등)
  여기서는 간단하게 product 만 구성했습니다.

자 여기서 요구 사항을 더 추가해보겠습니다

예를 들어보겠습니다. 상황을 가정해보겠습니다. 온라인 상점에는 '상품(Product)' 관련 로직을 처리하는 RDBMS 기반의 도메인 모듈 **`domain-product`**가 있습니다.

이제 새로운 요구사항으로, 상품의 삽입이 발생할 때마다 관련 정보를 실시간으로 처리해야 하는 요구가 생겼다고 합시다. 이를 위해 Redis를 사용하여 실시간 정보를 임시 저장하는 로직을 추가해야 합니다.
 
![multi-infra.png](https://user-images.githubusercontent.com/76603301/285415106-4f0a0366-82ff-4cac-a1b4-b59b6377271e.png)

도메인 계층에서 여러 인프라스트럭처를 사용해야할 때 꼬이는 의존 관계를 많이 보았습니다.

예를 들어 RDBMS를 사용하는 ‘A’라는 도메인 모듈이 있습니다. 시스템의 요구사항으로 ‘A’의 도메인의 삽입이 발생할 때 ‘B’라는 도메인으로 가공하여 임시 저장시켜야 하는 요구가 생겼습니다.

이 때 RDBMS 를 사용하는 도메인 모듈에 `Redis` 의존성을 추가한다면 의존 지옥을 맛볼 가능성이 큽니다. `A` 도메인에 대한 조회성을 위해서만 도메인 모듈을 사용해야하는 상황에서도 `Redis`에 대한 설정과 의존을 설정해주어야 합니다. 앞으로 더 다양한 `인프라스트럭처`를 사용하게 될 때 점점 더 지옥이 열릴 것을 확신합니다.

그래서 하나의 모듈은 하나의 `인프라스트럭처`만 책임지도록 모듈을 작성하는 것을 추천합니다. 그리고 위와 같은 두 인프라스트럭처 사이의 관계가 생길 때 두 모듈을 품는 모듈을 작성하거나 두 인프라스트럭처 모듈을 품는 어플리케이션에서 처리하시길 바랍니다.

예를 들어보겠습니다. 상황을 가정해보겠습니다. 온라인 상점에는 '상품(Product)' 관련 로직을 처리하는 RDBMS 기반의 도메인 모듈 **`domain-product`**가 있습니다. 이제 새로운 요구사항으로, 상품의 삽입이 발생할 때마다 관련 정보를 실시간으로 처리해야 하는 요구가 생겼다고 합시다. 이를 위해 Redis를 사용하여 실시간 정보를 임시 저장하는 로직을 추가해야 합니다.

### shop-domain-rdb
- **역할**: RDBMS를 사용하는 도메인 로직 구현.
- **필요 구현**: JPA 엔티티, 레포지토리 인터페이스.

### shop-domain-redis
- **역할**: Redis를 사용하는 도메인 로직 구현.
- **필요 구현**: Redis를 사용하는 데이터 액세스 로직.

### shop-api
- **역할**: 애플리케이션의 전체 흐름을 조정하고, 각 도메인 모듈 간의 상호작용을 관리. shop-api가 실행 가능한 애플리케이션입니다. 
- **필요 구현**: 서비스 통합 로직, 트랜잭션 관리, RDBMS와 Redis 간의 데이터 동기화 로직., 고객 인터페이스 API (상품 조회, 주문, 결제 등), 비즈니스 로직 처리, 고객 인증 및 인가.

### 모듈

```java
// domain-product 모듈 내의 Product 도메인 클래스
public class Product {
    // RDBMS에 저장될 상품 정보
}

// domain-product 모듈 내의 ProductService 클래스
public class ProductService {
    // 상품 데이터를 RDBMS에 저장하는 로직
    public void saveProduct(Product product) {
        // RDBMS 저장 로직...
        // Redis에 임시 저장하는 로직을 여기에 추가하면 문제가 생깁니다.
    }
}
```

여기에 Redis 의존성을 추가하는 순간, **`domain-product`** 모듈은 RDBMS 뿐만 아니라 Redis와도 강하게 결합되어 버립니다. 이는 아래와 같은 문제를 일으킬 수 있습니다:

1. **의존성 복잡성**: **`domain-product`** 모듈을 사용하기 위해서는 이제 RDBMS와 Redis 모두에 대한 설정이 필요합니다. 이는 단순한 상품 조회 등 RDBMS만 필요한 기능에도 Redis 설정을 필요로 합니다.
2. **유지보수 어려움**: RDBMS와 Redis 중 하나에 변경 사항이 발생하면 **`domain-product`** 모듈 전체에 영향을 미치며, 이로 인해 유지보수가 복잡해집니다.
3. **확장성 제한**: 향후 다른 인프라스트럭처를 추가하려 할 때마다 모듈의 복잡성이 증가하고, 각 인프라스트럭처에 대한 의존성이 누적됩니다.

### 권장

이러한 문제를 해결하기 위해, 각 인프라스트럭처에 대한 책임을 분리한 별도의 모듈을 작성하는 것이 좋습니다.

```java
// domain-product-rdb 모듈: RDBMS 관련 로직만 포함
public class ProductRdbService {
    public void saveProduct(Product product) {
        // RDBMS 저장 로직
    }
}

// domain-product-redis 모듈: Redis 관련 로직만 포함
public class ProductRedisService {
    public void saveTemporaryProductInfo(ProductInfo info) {
        // Redis 저장 로직
    }
}
```

```java
// application-service 모듈: 애플리케이션 로직을 조정
public class ProductApplicationService {
    private final ProductRdbService productRdbService;
    private final ProductRedisService productRedisService;

    public ProductApplicationService(ProductRdbService rdbService, ProductRedisService redisService) {
        this.productRdbService = rdbService;
        this.productRedisService = redisService;
    }

    public void insertProduct(Product product) {
        productRdbService.saveProduct(product);
        productRedisService.saveTemporaryProductInfo(convertToProductInfo(product));
    }

    // Product 객체를 ProductInfo로 변환하는 메서드
    private ProductInfo convertToProductInfo(Product product) {
        // ...
    }
}
```

# 1) 프로젝트 생성

## 1-1) 프로젝트 생성

### Spring project 설정

✔️**Java version** : java 11

✔️**spring boot Version**  = 2.7.17

## 1-2 ) src 폴더 삭제

하나의 프로젝트에 여러개의 모듈이 위치해 있고 공통 코드 같은 경우도 따로 모듈에 들어갈 거기 때문에 **src 폴더는 삭제**

→ 루트 프로젝트는 **하위 모듈을 관리하는 역할** 만 하 때문에 src 폴더를 삭제해도 괜찮다.

![스크린샷 2023-11-20 오전 2.00.56(2).png](https://user-images.githubusercontent.com/76603301/285414920-d8eb4b0a-20df-467f-87a6-e5f9a9240ef0.png)

## 1-3) 모듈 생성


![스크린샷 2023-11-21 오전 12.57.46(2).png](https://user-images.githubusercontent.com/76603301/285414643-1b610c62-8aba-43a8-841e-4af2bb60e21e.png)
![스크린샷 2023-11-20 오전 2.01.35(2).png](https://user-images.githubusercontent.com/76603301/285414340-d3a295b9-6f31-4534-a6bc-12c1ec49d386.png)

## 1-4) setting.gradle

setting.gradle 에 새로 만들어진 모듈에 대해서 코드를 추가해줘야 하는데, New > Module 을 통해서 모듈을 만든 경우에는 인텔리제이가 자동으로 코드를 넣어준다.

만일 코드가 없는 경우에는 `include #{모듈명}` 으로 추가해주자.

```java
rootProject.name = 'shop'
include 'shop-domain'
include 'shop-domain-rdb'
include 'shop-domain-redis'
include 'shop-api'
include 'shop-common'
```

# 2) gradle 작성

<aside>
🌱 Gradle? => 각 모듈의 종속성을 명확하게 정의하고 관리할 수 있는 기능을 제공

</aside>

## 2-1) root

### root (****`build.gradle`)**

```java
// 기본적인 Gradle 플러그인 설정
plugins {
    id 'java' // Java 언어에 대한 지원을 추가합니다. 컴파일, 테스트, 패키징 등의 기능을 제공합니다.
    id 'org.springframework.boot' version '2.7.17' // Spring Boot 애플리케이션을 위한 플러그인입니다. Spring Boot의 기능과 의존성 관리를 제공합니다.
    id 'io.spring.dependency-management' version '1.0.15.RELEASE' // Spring Boot의 의존성 관리 기능을 제공합니다. 이를 통해 의존성 버전을 Spring Boot의 버전에 맞춰 관리할 수 있습니다.
}

// 모든 프로젝트에 공통적으로 적용되는 설정
allprojects {
    sourceCompatibility = '11' // Java 소스 호환성을 Java 11로 설정합니다.
    targetCompatibility = '11' // 컴파일된 바이트코드의 호환성을 Java 11로 설정합니다.

    // 의존성을 다운로드할 Maven 중앙 저장소를 지정합니다.
    repositories {
        mavenCentral()
    }
}

// 모든 서브 프로젝트에 적용되는 설정
subprojects {
    // 서브 프로젝트에 적용할 플러그인을 지정합니다.
    apply plugin: 'java'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'
    group = 'com.efub' // 그룹 ID를 설정합니다.
    version = '0.0.1-SNAPSHOT' // 버전을 설정합니다.

    // 서브 프로젝트에 적용할 공통 의존성을 선언합니다.
    dependencies {
        implementation "org.projectlombok:lombok" // Lombok 라이브러리를 추가합니다.
        annotationProcessor "org.projectlombok:lombok"
        implementation 'org.springframework.boot:spring-boot-starter-web' // 웹 애플리케이션 개발에 필요한 스프링 부트 스타터 의존성을 추가합니다.

        // 테스트 관련 의존성을 추가합니다.
        testImplementation "org.projectlombok:lombok"
        testAnnotationProcessor "org.projectlombok:lombok"
        testImplementation 'org.springframework.boot:spring-boot-starter-test'

        // JPA를 위한 스프링 부트 스타터 의존성을 추가합니다.
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    }

    // JUnit 5를 사용하기 위한 설정입니다.
    tasks.named('test') {
        useJUnitPlatform()
    }
}

// 각 서브 프로젝트의 구체적인 설정
project(':shop-common') {
    bootJar { enabled = false } // 실행 가능한 jar 파일 생성을 비활성화합니다. `shop-common`은 라이브러리 형태이므로 실행 파일이 필요하지 않습니다.
    jar { enabled = true } // 일반 jar 파일 생성을 활성화합니다.

    dependencies {
        // 여기에 'shop-common' 프로젝트의 의존성을 추가할 수 있습니다.
    }
}

project(":shop-api") {
    bootJar { enabled = true } // 실행 가능한 jar 파일 생성을 활성화합니다. 'shop-api'는 실행 가능한 애플리케이션입니다.
    jar { enabled = false } // 일반 jar 파일 생성을 비활성화합니다. -> 일반 jar 파일 생성을 활성화하는 것은 shop-common이 다른 프로젝트에서 라이브러리로서 임포트되고 사용될 수 있도록 합니다. 일반 jar 파일은 라이브러리와 같은 재사용 가능한 코드를 포함하는 데 적합하며, 실행 가능한 환경을 포함하지 않기 때문에 다른 프로젝트에서 이 라이브러리를 참조하는 데 적합

    dependencies {
        implementation project(':shop-common') // 'shop-common' 모듈에 대한 의존성을 추가합니다.
        implementation project(':shop-domain-rdb') // 'shop-domain-rdb' 모듈에 대한 의존성을 추가합니다.
        implementation project(':shop-domain-redis') // 'shop-domain-redis' 모듈에 대한 의존성을 추가합니다.
        implementation 'org.springframework
	}
}

project(":shop-domain") {
    bootJar { enabled = false }
    jar { enabled = true }
    dependencies {
        implementation project(':shop-common')

    }
}

project(":shop-domain-rdb") {
    bootJar { enabled = false }
    jar { enabled = true }
    dependencies {
        implementation project(':shop-common')
        implementation project(':shop-domain')
        implementation 'org.springframework.boot:spring-boot-starter-data-rest'
        runtimeOnly 'com.mysql:mysql-connector-j'

    }
}
project(":shop-domain-redis") {
    bootJar { enabled = false }
    jar { enabled = true }
    dependencies {
        implementation project(':shop-common')
        implementation project(':shop-domain')
        //redis
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    }
}
```

- **`buildscript`**: 스프링 부트 Gradle 플러그인 및 의존성 관리 플러그인에 필요한 설정을 정의합니다. 이는 프로젝트 전체에 걸쳐 스프링 부트의 버전을 일관되게 관리하고자 할 때 유용합니다.
- **`subprojects`**: 모든 하위 프로젝트(서브 모듈)에 공통으로 적용될 설정을 정의합니다. 여기에는 Java 플러그인, IDEA 플러그인, 스프링 부트 플러그인 등이 포함됩니다.
- **`repositories`**: Maven 중앙 저장소를 지정합니다. 이 저장소는 프로젝트에서 필요한 모든 종속성을 다운로드하는 데 사용됩니다.
- **`dependencies`**: 모든 서브 프로젝트에서 공통적으로 사용될 종속성을 정의합니다. 예를 들어, Lombok과 JUnit Jupiter와 같은 테스트 종속성이 여기에 포함됩니다.



| application - 실행가능한 main |
| --- |
| redis, rdb |
| domain - 엔티티 |
| common - 공통 |


### 참고 자료 
1) https://techblog.woowahan.com/2637/
2) https://mangkyu.tistory.com/304

