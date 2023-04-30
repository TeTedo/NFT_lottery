# env 관련

slack 참고

## COMMIT CONVENTION

```
<type>(<scope>): <short summary>
  │       │          │
  │       │          └─⫸ 명령문, 현재 시제로 작성합니다. 대문자를 사용하지 않으며,마침표로 끝내지 않습니다.
  │       │
  │       └─⫸ Commit Scope: 어디가 변경되었는지 ex) rootScope,ngHref, ngClick
  │
  └─⫸ Commit Type: build|ci|docs|feat|fix|perf|refactor|test
```

### Commit Type 종류들

- feat : 새로운 기능 추가

- fix : 버그 수정

- docs : 문서 관련

- style : 스타일 변경 (포매팅 수정, 들여쓰기 추가, …)

- refactor : 코드 리팩토링

- test : 테스트 관련 코드

- build : 빌드 관련 파일 수정

- ci : CI 설정 파일 수정

- perf : 성능 개선

- chore : 그 외 자잘한 수정

## **Fork** 방법

1. 해당 레퍼지토리 fork (main branch 자동으로 생성)

2. fork한 레퍼지토리에서 개인 브랜치 만들기

3. 개인 브랜치에서 작업

## **Merge** 방법

- main 브런치 pull 방법

1. main브런치로 이동

2. 아래 그림 빨간 부분 클릭

   <img width="704" alt="pull" src="https://user-images.githubusercontent.com/107897812/211035976-53f8ec0b-205b-4954-843c-e17ff64d567a.png">

   <img width="704" alt="pull2" src="https://user-images.githubusercontent.com/107897812/211035981-6e1e8508-5f05-42c1-8168-4a5cc3d10113.png">

3. 터미널에 git pull 명령어 입력

   ```
   git pull
   ```

4. main브런치와 개인브런치 merge

   ```
   git merge seok
   ```

5. 오류 확인 후 정상인 경우 git push

   ```
   git push
   ```

6. push 후 아래 그림에 따라 pull request 처리

   ## pull request창 열기

   <img width="656" alt="pull3" src="https://user-images.githubusercontent.com/107897812/211038644-a75f9ac2-214b-467e-abfb-e63537f19fa6.png">

   ## pull request 내용 작성

    <img width="625" alt="pull4" src="https://user-images.githubusercontent.com/107897812/211038651-7bbb0112-76ea-45ea-a645-1b1a0f070846.png">

   ## fork해온 레퍼지토리의 main브런치와 merge

    <img width="668" alt="pull6" src="https://user-images.githubusercontent.com/107897812/211038661-58c71630-b4f2-49ff-9c95-88b2f1da6d7b.png">

    <img width="665" alt="pull7" src="https://user-images.githubusercontent.com/107897812/211038667-c04b4990-f1f9-49c6-b64f-3a6cb5df51e4.png">
