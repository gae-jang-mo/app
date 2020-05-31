#!/bin/bash

REPOSITORY=/home/ec2-user/app-server/deploy/zip

cd $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(ps -ef | grep java | grep api-server* | awk '{print $2}')
echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ |grep *.jar | tail -n 1)
echo ">Jar Name: $JAR_NAME"

echo ">$JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> 개장모 메인 서버 배포 합니다 !!"
nohup java -jar -Dspring.profiles.active=dev /home/ec2-user/app-server/deploy/zip/api-server-0.0.1-SNAPSHOT.jar >> /home/ec2-user/app-server/logs/api-server.log &