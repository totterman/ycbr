#!/bin/sh
echo "***************************************************************************************************************************************"
echo "* To build Spring Boot native images, run with the \"native\" argument: \"sh ./build.sh native\" (images will take much longer to build). *"
echo "*                                                                                                                                     *"
echo "* This build script tries to auto-detect ARM64 (Apple Silicon) to build the appropriate Spring Boot Docker images.                    *"
echo "***************************************************************************************************************************************"
echo ""

if [[ "$OSTYPE" == "darwin"* ]]; then
  SED="sed -i '' -e"
else
  SED="sed -i -e"
fi

if [ -f ./build.env ]; then
 . ./build.env
fi

#host=$(echo $HOSTNAME  | tr '[A-Z]' '[a-z]')
host=`hostname -f`
#host="localhost"
reverse_proxy_port=${REVERSE_PROXY_PORT:-7080}

echo "Detected hostname: ${host}"
echo "Reverse proxy port: ${reverse_proxy_port}"

cd backend
cd api-server
echo ""
echo "*****************************************************************************************************************************************"
echo "./gradlew bootBuildImage --imageName=ycbr/api-server"
echo "*****************************************************************************************************************************************"
echo ""
./gradlew clean build
./gradlew bootBuildImage --imageName=ycbr/api-server
cd ..

cd bff-server
echo ""
echo "*****************************************************************************************************************************************"
echo "./gradlew bootBuildImage --imageName=/ycbr/bff-server"
echo "*****************************************************************************************************************************************"
echo ""
./gradlew clean build
./gradlew bootBuildImage --imageName=ycbr/bff-server
cd ..
cd ..

rm -f "compose-${host}.yml"
cp compose.yml "compose-${host}.yml"
$SED "s/LOCALHOST_NAME/${host}/g" "compose-${host}.yml"
rm -f "compose-${host}.yml''"
echo "compose-${host}.yml created."

rm -f keycloak/import/ycbr-realm.json
cp keycloak/ycbr-template.json keycloak/import/ycbr-realm.json
$SED "s/LOCALHOST_NAME/${host}/g" keycloak/import/ycbr-realm.json
$SED "s/REVERSE_PROXY_PORT/${reverse_proxy_port}/g" keycloak/import/ycbr-realm.json
rm -f "keycloak/import/ycbr-realm.json''"
echo "keycloak/import/ycbr-realm.json created."

cd ui/
rm -f .env
cp ui-template.env .env
$SED "s/LOCALHOST_NAME/${host}/g" .env
$SED "s/REVERSE_PROXY_PORT/${reverse_proxy_port}/g" .env
npm ci
npm run build
docker build -t ycbr/ui .
cd ..
echo "UI built."

cd monitoring/prometheus/
rm prometheus.yml
cp prometheus-base.yml prometheus.yml
$SED "s/LOCALHOST_NAME/${host}/g" prometheus.yml
cd ../..
echo "Prometheus configuration created."


if [[ ${host} == `hostname -f` ]]; then
  docker compose -f compose-${host}.yml up -d
  echo ""
  echo "Keycloak as admin / admin:"
  echo "https://${host}:${reverse_proxy_port}/auth/admin/master/console/#/ycbr"
  echo "App frontend:"
  echo https://${host}:${reverse_proxy_port}/ui/
  echo ""
else
  echo "Build for ${host} completed."
  echo ""
fi

