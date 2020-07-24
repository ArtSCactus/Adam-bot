# Adam-bot
 A telegram bot for tourists. He replies to the entered city name with its description (which contains the main attractions and tips where to go).
Bot can be found in telegram by tag:
```
@Adamson_test_bot
```
 
# How to run
In order to run a bot, you first need to build it. To do this, just go using the command line to the project directory and enter the command:

```maven
mvn clean install
```

After the end of the build, the 'jars' folder will appear in the project root, which will contain the assembled executable files for each module. In order to start a project, you can run each module separately. However, this can be done simultaneously. To do this, run run.sh or run.cmd (sh for Linux and cmd for Windows). When you start sh, the data-server will first start, and then after 10 seconds the bot itself will start in the background (10 seconds are needed to initialize and start the server with data). The process is similar for cmd, with the only difference that the two modules will run in different tabs.

When the bot is ready to work, a message about this will appear in the console.

# Requirements
```
jdk 1.8+
PostgreSQL 11.5+ database
```
# Notice

Bot can work without the data-server, but every time, when he cannot retrieve data from server, it will respond with a message that it cannot access the data.


# Token
Here's bot token:
```
1073299330:AAG5p4qoY9pTcmEdEnZFqUeR7hmB_OyRhfg
```
