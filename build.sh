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

MAVEN_PROFILES=()
if [[ `uname -m` == "arm64" ]]; then
  MAVEN_PROFILES+=("arm64")
fi
if [[ " $@ " =~ [[:space:]]native[[:space:]] ]]; then
    MAVEN_PROFILES+=("native")
fi
if [ ${#MAVEN_PROFILES[@]} -eq 0 ]; then
    MAVEN_PROFILE_ARG=""
else
    MAVEN_PROFILE_ARG=-P$(IFS=, ; echo "${MAVEN_PROFILES[*]}")
fi

host=$(echo $HOSTNAME  | tr '[A-Z]' '[a-z]')

cd backend
echo "***********************"
echo "sh ./mvnw clean install"
echo "***********************"
echo ""
sh ./mvnw clean install

echo ""
echo "*****************************************************************************************************************************************"
echo "sh ./mvnw -pl resource-server spring-boot:build-image -Dspring-boot.build-image.imageName=ycbr/resource-server $MAVEN_PROFILE_ARG"
echo "*****************************************************************************************************************************************"
echo ""
sh ./mvnw -pl resource-server spring-boot:build-image -Dspring-boot.build-image.imageName=ycbr/resource-server $MAVEN_PROFILE_ARG

echo ""
echo "*****************************************************************************************************************"
echo "sh ./mvnw -pl bff spring-boot:build-image -Dspring-boot.build-image.imageName=ycbr/bff $MAVEN_PROFILE_ARG"
echo "*****************************************************************************************************************"
echo ""
sh ./mvnw -pl bff spring-boot:build-image -Dspring-boot.build-image.imageName=ycbr/bff $MAVEN_PROFILE_ARG
cd ..

rm -f "compose-${host}.yml"
cp compose.yml "compose-${host}.yml"
$SED "s/LOCALHOST_NAME/${host}/g" "compose-${host}.yml"
rm -f "compose-${host}.yml''"

rm keycloak/import/ycbr-realm.json
cp ycbr-realm.json keycloak/import/ycbr-realm.json
$SED "s/LOCALHOST_NAME/${host}/g" keycloak/import/ycbr-realm.json
rm "keycloak/import/ycbr-realm.json''"

cd react-ui/
rm .env.development
cp ../react-ui.env.development .env.development
$SED "s/LOCALHOST_NAME/${host}/g" .env.development
rm ".env.development''"
npm i
npm run build
cd ..

cd tanstack-nextjs/
rm .env.development
cp ../tanstack-nextjs.env.development .env.development
$SED "s/LOCALHOST_NAME/${host}/g" .env.development
rm ".env.development''"
npm i
npm run build
cd ..

cd nginx-reverse-proxy/
rm nginx.conf
cp ../nginx.conf ./
$SED "s/LOCALHOST_NAME/${host}/g" nginx.conf
cd ..

docker build -t ycbr/nginx-reverse-proxy ./nginx-reverse-proxy
docker build -t ycbr/react-ui ./react-ui
docker build -t ycbr/tanstack-nextjs ./tanstack-nextjs

docker compose -f compose-${host}.yml up -d

echo ""
echo "Open the following in a new private navigation window."

echo ""
echo "Keycloak as admin / admin:"
echo "http://${host}:7080/auth/admin/master/console/#/ycbr"

echo ""
echo "Sample frontends as user / user"
echo http://${host}:7080/react-ui/
