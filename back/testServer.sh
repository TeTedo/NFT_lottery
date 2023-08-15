##!/bin/bash

# code update
git pull

# 8080번에서 실행중인 프로세스 PID 찾기
PID=$(lsof -t -i:8080)

# 해당 프로세스가 존재하면 종료
if [[ ! -z $PID ]]; then
  kill $PID
  echo "8080번 포트 종료 성공"
fi

# 프로젝트 빌드
./gradlew clean build

# 실행
nohup java -jar build/libs/undefined-0.0.1-SNAPSHOT.jar &