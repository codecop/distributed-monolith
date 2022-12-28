@setlocal
@cls
call mvn clean package -DskipTests

@echo ----------------------------------------
@echo start ActiveMQ
cd activemq
@rem cls && call mvn clean compile exec:java
@set JAR=-jar target\distributed-gol-activemq-0.1-jar-with-dependencies.jar
start "ActiveMQ" cmd.exe /C "java %JAR%"
cd ..
@echo wait for ActiveMQ to start
@pause


@echo ----------------------------------------
@echo starting cells
cd cell
@rem cls && call mvn mn:run
@set JAR=-jar target\distributed-gol-cell-0.1.jar
start "Cell00" cmd.exe /C "java -Dmicronaut.server.port=8000 -Dposition.x=0 -Dposition.y=0 %JAR%"
start "Cell10" cmd.exe /C "java -Dmicronaut.server.port=8100 -Dposition.x=1 -Dposition.y=0 %JAR%"
start "Cell20" cmd.exe /C "java -Dmicronaut.server.port=8200 -Dposition.x=2 -Dposition.y=0 %JAR%"

start "Cell01" cmd.exe /C "java -Dmicronaut.server.port=8001 -Dposition.x=0 -Dposition.y=1 %JAR%"
start "Cell11" cmd.exe /C "java -Dmicronaut.server.port=8101 -Dposition.x=1 -Dposition.y=1 %JAR%"
start "Cell21" cmd.exe /C "java -Dmicronaut.server.port=8201 -Dposition.x=2 -Dposition.y=1 %JAR%"

start "Cell02" cmd.exe /C "java -Dmicronaut.server.port=8002 -Dposition.x=0 -Dposition.y=2 %JAR%"
start "Cell12" cmd.exe /C "java -Dmicronaut.server.port=8102 -Dposition.x=1 -Dposition.y=2 %JAR%"
start "Cell22" cmd.exe /C "java -Dmicronaut.server.port=8202 -Dposition.x=2 -Dposition.y=2 %JAR%"
cd ..
@echo wait for servers to start
@pause


@set OPTS=-q -O-
@echo ----------------------------------------
@echo seeding x=1 and y=0,1,2 = BAR
wget %OPTS% --method=post http://localhost:8100/seed
wget %OPTS% --method=post http://localhost:8101/seed
wget %OPTS% --method=post http://localhost:8102/seed
@echo ready to view SEED?
@pause
wget %OPTS% http://localhost:8000/grid
wget %OPTS% http://localhost:8000/alive.json
wget %OPTS% http://localhost:8100/alive.json
wget %OPTS% http://localhost:8200/alive.json
@echo ;
wget %OPTS% http://localhost:8001/alive.json
wget %OPTS% http://localhost:8101/alive.json
wget %OPTS% http://localhost:8201/alive.json
@echo ;
wget %OPTS% http://localhost:8002/alive.json
wget %OPTS% http://localhost:8102/alive.json
wget %OPTS% http://localhost:8202/alive.json
@echo ;

@echo ready to TICK?
@pause
wget %OPTS% --method=post http://localhost:8102/tick 
@echo ready to view first TICK?
@pause
wget %OPTS% http://localhost:8000/grid
wget %OPTS% http://localhost:8000/alive.json
wget %OPTS% http://localhost:8100/alive.json
wget %OPTS% http://localhost:8200/alive.json
@echo ;
wget %OPTS% http://localhost:8001/alive.json
wget %OPTS% http://localhost:8101/alive.json
wget %OPTS% http://localhost:8201/alive.json
@echo ;
wget %OPTS% http://localhost:8002/alive.json
wget %OPTS% http://localhost:8102/alive.json
wget %OPTS% http://localhost:8202/alive.json
@echo ;


@echo ready to TICK?
@pause
wget %OPTS% --method=post http://localhost:8102/tick 
@echo ready to view next TICK?
@pause
wget %OPTS% http://localhost:8000/grid
wget %OPTS% http://localhost:8000/alive.json
wget %OPTS% http://localhost:8100/alive.json
wget %OPTS% http://localhost:8200/alive.json
@echo ;
wget %OPTS% http://localhost:8001/alive.json
wget %OPTS% http://localhost:8101/alive.json
wget %OPTS% http://localhost:8201/alive.json
@echo ;
wget %OPTS% http://localhost:8002/alive.json
wget %OPTS% http://localhost:8102/alive.json
wget %OPTS% http://localhost:8202/alive.json
@echo ;

@endlocal
