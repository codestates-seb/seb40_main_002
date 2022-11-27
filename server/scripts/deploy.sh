#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/deploy/server/build/libs/server-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /home/ec2-user/deploy/server/deploy.log

echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/deploy/server/deploy.log

echo "> build 파일 복사" >> /home/ec2-user/deploy/server/deploy.log
DEPLOY_PATH=/home/ec2-user/deploy/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/deploy/server/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy/server/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ec2-user/deploy/server/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi


DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ec2-user/deploy/server/deploy.log
sudo nohup java -jar $DEPLOY_JAR >> /home/ec2-user/deploy.log 2>/home/ec2-user/deploy/server/deploy_err.log &