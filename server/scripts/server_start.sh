#!/usr/bin/env bash
# 배포전에 실행시킬 커맨드라인 명령어
# 1 : 해당 경로로 이동 (cd)
# 2 : nohup - 해당 명령어를 입력하는 터미널의 연결이 끊켜도 프로세스를 실행시킴.
# 2 : > /dev/null 2> - 해당 명령어로 인해 발생되는 출력내용(에러 포함)을 버림
cd /home/ubuntu/build
#sudo nohup java -jar server-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &
sudo nohup java -jar server-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &