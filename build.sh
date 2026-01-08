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

#host=$(echo $HOSTNAME  | tr '[A-Z]' '[a-z]')
host=`hostname -f`
#host="localhost"
echo "Detected hostname: ${host}"

cd backend
cd api-server
echo ""
echo "*****************************************************************************************************************************************"
echo "./gradlew bootBuildImage" --imageName=ycbr/api-server
echo "*****************************************************************************************************************************************"
echo ""
./gradlew clean build
./gradlew bootBuildImage --imageName=ycbr/api-server
cd ..

cd bff-server
echo ""
echo "*****************************************************************************************************************************************"
echo "./gradlew bootBuildImage" --imageName=ycbr/bff-server
echo "*****************************************************************************************************************************************"
echo ""
./gradlew clean build
./gradlew bootBuildImage --imageName=ycbr/bff-server
cd ..

cd reverse-proxy
echo ""
echo "*****************************************************************************************************************************************"
echo "./gradlew bootBuildImage" --imageName=ycbr/reverse-proxy
echo "*****************************************************************************************************************************************"
echo ""
./gradlew clean build
./gradlew bootBuildImage --imageName=ycbr/reverse-proxy
cd ..
cd ..

rm -f "compose-${host}.yml"
cp compose.yml "compose-${host}.yml"
$SED "s/LOCALHOST_NAME/${host}/g" "compose-${host}.yml"
rm -f "compose-${host}.yml''"

rm keycloak/import/ycbr-realm.json
cp ycbr-realm.json keycloak/import/ycbr-realm.json
$SED "s/LOCALHOST_NAME/${host}/g" keycloak/import/ycbr-realm.json
rm "keycloak/import/ycbr-realm.json''"

# cd native-ui/
# rm .env.development
# cp ../native-ui.env.development .env.development
# $SED "s/LOCALHOST_NAME/${host}/g" .env.development
# rm ".env.development''"
# npm i
# npm run build
# cd ..

cd tanstack-ui/
rm .env
cp ../tanstack-ui.env .env
$SED "s/LOCALHOST_NAME/${host}/g" .env
rm ".env''"
npm i
npm run build
cd ..

cd nginx-reverse-proxy/
rm nginx.conf
cp ../nginx.conf ./
$SED "s/LOCALHOST_NAME/${host}/g" nginx.conf
cd ..

cd monitoring/prometheus/
rm prometheus.yml
cp prometheus-base.yml prometheus.yml
$SED "s/LOCALHOST_NAME/${host}/g" prometheus.yml
cd ../..


docker build -t ycbr/nginx-reverse-proxy ./nginx-reverse-proxy
#docker build -t ycbr/native-ui ./native-ui
docker build -t ycbr/tanstack-ui ./tanstack-ui

docker compose -f compose-${host}.yml up -d

echo ""
echo "Open the following in a new private navigation window."

echo ""
echo "Keycloak as admin / admin:"
echo "http://${host}:7080/auth/admin/master/console/#/ycbr"

echo ""
echo "Sample frontends as user / user"
echo http://${host}:7080/tanstack-ui/
