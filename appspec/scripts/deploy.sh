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

