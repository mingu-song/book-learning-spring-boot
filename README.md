# 스프링부트 2.0
## 마이크로서비스와 리액티브 프로그래밍
#### 에이콘

> active_profile = ch01 으로 각각 설정하여 실행

이 책은 먼가 한방에 되는게 없다. 버전 문제인지... 2장부터 안될 줄이야 ㅎㅎ

## Chapter 01
webflux 로 하나 만들어 보는 기분으로 진행
* embedded mongo db 를 이용한 간단한 Flux<Chapter> 리턴 컨트롤러 작성
* ChapterController / HomeController

## Chapter 02
파일기반으로 리액티브 mvc를 처리해본다
* 리액티브 멀티파트 업로드를 위한 라이브러리
  * https://github.com/synchronoss/nio-multipart
  * 하지만 사용하는 곳이 없다 ㅎㅎ (Flux로 받는것처럼 되어 있지만 하나만 업로드 됨. 다음에 나오나..)
* 해당 챕터는 먼가 안되는게 많네 스프링 버전탓인지...
  * Files.newDirectoryStream => Files.list 로 변경 :: 리액티브에서 처리되지 않아서 변경
  * HiddenHttpMethodFilter => 삭제 :: 신기하게 delete 요청이 post로 들어온다;; rest controller가 아니어서 그런가..
  
## Chapter 03
파일과 db에 함께 기록하고, 삭제하는 챕터이며, mongo db 설정이 중요하지만 책의 내용으로는 되지 않는다.
* mongodb docker 로 생성 :: mongo-compose.yml
    * https://woolbro.tistory.com/90
* flapdoodle.embed.mongo 주석 처리
* application.yml :: mongo db 정보 추가
* InitDatabase.java :: MongoOperations 가 주입되지 않아 ReactiveMongoTemplate 사용

    

