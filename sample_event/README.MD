<h1>이벤트 구독 샘플</h1>

- 테스트용 컨트렉트는 baobab(klaytn)에 배포돼있음
- CA : 0x5d73e59868C2EcC79c4A59920931d449346B8680

---

1. .env.example 파일 뒤에 .example 지우기

2. ```
   npm i
   ```

---

<이벤트 구독>

```
ts-node eventConnection.ts
```

<테스트용 컨트렉트 펑션 호출>

```
ts-node contractCall.ts
```

각각 다른 터미널에서 실행
