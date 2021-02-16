TAG=$1
mvn package
docker build -t "${TAG}" .
