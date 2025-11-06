일정 관리 앱 (Schedule App)

 개발 환경
- Spring Boot 3.5.7  
- Java 17  
- H2 Database  
- JPA  

필드명 타입 | 설명 |
|--------|------|------|
 id | Long | 일정 ID 
 title | String | 일정 제목 
 contents | String | 일정 내용 
 writer | String | 작성자 
 password | String | 비밀번호 
 createdAt | LocalDateTime | 작성일 
 modifiedAt | LocalDateTime | 수정일 

 API 명세서

 일정 등록
**POST** `/schedules`
```json
{
  "title": "공부하기",
  "contents": "Spring Boot 과제하기",
  "writer": "홍길동",
  "password": "1234"
}
```

=== 전체 조회===
**GET** `/schedules`

< 단건 조회>
**GET** `/schedules/{id}`

< 수정 >
**PUT** `/schedules/{id}`
```json
{
  "title": "수정된 일정",
  "writer": "홍길동",
  "password": "1234"
}
```

삭제
*DELETE** `/schedules/{id}`
```json
{
  "password": "1234"
}
```

 H2 콘솔: http://localhost:8080/h2-console
