#!/bin/bash

# Kill any existing instances
pkill -f "m4u-0.0.1-SNAPSHOT.jar"

# Start first instance on port 8080
echo "Starting instance 1 on port 8080..."
java -jar target/m4u-0.0.1-SNAPSHOT.jar --server.port=8080 &

# Start second instance on port 8081
echo "Starting instance 2 on port 8081..."
java -jar target/m4u-0.0.1-SNAPSHOT.jar --server.port=8081 &

echo "Both instances started. Use 'pkill -f m4u-0.0.1-SNAPSHOT.jar' to stop them." 