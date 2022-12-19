@setlocal
@set BUILD=call mvn clean package -DskipTests

@echo start activemq
cd activemq
%BUILD%
@rem cls && call mvn clean compile exec:java
start "ActiveMQ" cmd.exe /C "java -jar target\distributed-gol-activemq-0.1-jar-with-dependencies.jar"
cd ..

@echo starting cells
cd cell
%BUILD%
@rem cls && call mvn mn:run
start "Cell00" cmd.exe /C "java -Dmicronaut.server.port=8000 -Dposition.x=0 -Dposition.y=0 -jar target\distributed-gol-cell-0.1.jar"
start "Cell10" cmd.exe /C "java -Dmicronaut.server.port=8001 -Dposition.x=1 -Dposition.y=0 -jar target\distributed-gol-cell-0.1.jar"
start "Cell20" cmd.exe /C "java -Dmicronaut.server.port=8002 -Dposition.x=2 -Dposition.y=0 -jar target\distributed-gol-cell-0.1.jar"

start "Cell01" cmd.exe /C "java -Dmicronaut.server.port=8100 -Dposition.x=0 -Dposition.y=1 -jar target\distributed-gol-cell-0.1.jar"
start "Cell11" cmd.exe /C "java -Dmicronaut.server.port=8101 -Dposition.x=1 -Dposition.y=1 -jar target\distributed-gol-cell-0.1.jar"
start "Cell21" cmd.exe /C "java -Dmicronaut.server.port=8102 -Dposition.x=2 -Dposition.y=1 -jar target\distributed-gol-cell-0.1.jar"

start "Cell02" cmd.exe /C "java -Dmicronaut.server.port=8200 -Dposition.x=0 -Dposition.y=2 -jar target\distributed-gol-cell-0.1.jar"
start "Cell12" cmd.exe /C "java -Dmicronaut.server.port=8201 -Dposition.x=1 -Dposition.y=2 -jar target\distributed-gol-cell-0.1.jar"
start "Cell22" cmd.exe /C "java -Dmicronaut.server.port=8202 -Dposition.x=2 -Dposition.y=2 -jar target\distributed-gol-cell-0.1.jar"
cd ..

@set VIEW=wget http://localhost:8000/grid && type grid && del grid
%VIEW%

call wget --method=post http://localhost:8100/seed && del seed
call wget --method=post http://localhost:8101/seed && del seed
call wget --method=post http://localhost:8102/seed && del seed

call wget --method=post http://localhost:8102/tick && del tick 
pause
%VIEW%

@endlocal
