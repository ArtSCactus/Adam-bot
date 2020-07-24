@echo off
echo "Starting the data-server."
start  "Data-server" java -jar %cd%/jars/Data-server.jar
echo "Bot will start working in 10 seconds."
timeout 10
start  "Bot" java -jar %cd%/jars/Adam-bot-core.jar

