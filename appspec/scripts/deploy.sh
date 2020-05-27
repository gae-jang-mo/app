#!/bin/bash

REPOSITORY=/home/ec2-user/app-server
PROJECT_NAME=api-server

cd $REPOSITORY/$PROJECT_NAME/

echo "> build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/zip/*.jar $REPOSITORY/$PROJECT_NAME

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl ${api-server})

echo "> 현재 구동중인 설정 서버 pid 확인"
CURRENT_PID=$(ps -ef | grep java | grep api-server* | awk '{print $2}')
echo "$CURRENT_PID"
if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
#else
#  echo "> kill -9 $CURRENT_PID"
#  kill -9 $CURRENT_PID
#  sleep 10
fi

echo "> 개장모 메인 서버 배포 합니다 !!"

#nohup java -jar -Dspring.profiles.active=dev /home/ec2-user/app-server/deploy/api-server-0.0.1-SNAPSHOT.jar >> /home/ec2-user/app-server/logs/api-server.log &






REPOSITORY=/home/ec2-user/app
PROJECT_NAME=aws

cd $REPOSITORY/$PROJECT_NAME/

echo "> build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/zip/*.jar $REPOSITORY/$PROJECT_NAME

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl ${aws})

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"
if [ -z $CURRENT_PID ]; then
	echo ">현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ |grep *.jar | tail -n 1)
echo ">Jar Name: $JAR_NAME"

echo ">$JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo ">$JAR_NAME 실행"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
