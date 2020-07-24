#!/bin/sh
RunAdamBotWithCountDown() {
  secs=$1
  shift
  msg=$@
  while [ $secs -gt 0 ]
  do
    printf "\r\033[K$msg %.d " $((secs--))
    sleep 1
  done
  java -jar Adam-bot-core.jar
  echo
}
echo "Running data server."
screen 1
ABSOLUTE_PATH=${PWD}
cd "${ABSOLUTE_PATH}\jars"
java -jar Data-server.jar &
DataServerPID=$!
trap 'kill ${DataServerPID}; ' INT
echo "Running Adam bot."
RunAdamBotWithCountDown 10 "Running bot in"
BotPID=$!
trap 'kill ${BotPID}; exit' INT

