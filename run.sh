cd "$(dirname "$0")" || exit

echo "Starting ApiGateway..."
mvn -pl ApiGateway spring-boot:run &

echo "Starting DiscoveryServer..."
mvn -pl DiscoveryServer spring-boot:run &

echo "Starting NotificationService..."
mvn -pl NotificationService spring-boot:run &

echo "Starting OrderService..."
mvn -pl OrderService spring-boot:run &

echo "Starting SearchService..."
mvn -pl SearchService spring-boot:run &

echo "Starting UserService..."
mvn -pl UserService spring-boot:run &

echo "Starting VehicleInventoryService..."
mvn -pl VehicleInventoryService spring-boot:run &

wait

echo "All services have been started."
