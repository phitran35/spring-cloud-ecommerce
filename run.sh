r=`pwd`
echo $r

# config service
cd $r/config-service
echo "Starting config Service..."
./mvnw package -DskipTests
java -jar target/config-service-0.0.1-SNAPSHOT.jar &
#mvn -q clean install spring-boot:run -Dspring.profiles.active=native  -Dmaven.test.skip=true &

# Discovery service
cd $r/discovery-service
echo "Starting Discovery Service..."
./mvnw package -DskipTests
java -jar target/discovery-service-0.0.1-SNAPSHOT.jar &
#mvn -q clean install spring-boot:run  -Dmaven.test.skip=true

# Gateway service
cd $r/gateway-service
echo "Starting Gateway Service..."
#mvn -q clean install spring-boot:run  -Dmaven.test.skip=true &

# Audit service
cd $r/audit-service
echo "Starting Audit Service..."
#mvn -q clean install spring-boot:run  -Dmaven.test.skip=true &

# Product service
cd $r/product-service
echo "Starting Audit Service..."
#mvn -q clean compile install spring-boot:run  -Dmaven.test.skip=true