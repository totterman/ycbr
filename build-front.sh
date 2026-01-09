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

#host=$(echo $HOSTNAME  | tr '[A-Z]' '[a-z]')
host=`hostname -f`
echo "Detected hostname: ${host}"

rm -f "compose-${host}.yml"
cp compose.yml "compose-${host}.yml"
$SED "s/LOCALHOST_NAME/${host}/g" "compose-${host}.yml"
rm -f "compose-${host}.yml''"


# cd react-ui/
# rm .env.development
# cp ../react-ui.env.development .env.development
# $SED "s/LOCALHOST_NAME/${host}/g" .env.development
# rm ".env.development''"
# npm i
# npm run build
# cd ..

# cd nextjs-ui/
# rm .env.development
# cp ../nextjs-ui.env.development .env.development
# $SED "s/LOCALHOST_NAME/${host}/g" .env.development
# rm ".env.development''"
# npm i
# npm run build
# cd ..

# cd native-ui/
# rm .env.development
# cp ../native-ui.env.development .env.development
# $SED "s/LOCALHOST_NAME/${host}/g" .env.development
# rm ".env.development''"
# npm i
# npm run build
# cd ..

echo Building ui...
cd ui/
rm .env
cp ../ui.env ./.env
$SED "s/LOCALHOST_NAME/${host}/g" .env
rm ".env''"
npm i
npm run build
echo ui build complete.
cd ..

cd nginx-reverse-proxy/
rm nginx.conf
cp ../nginx.conf ./
$SED "s/LOCALHOST_NAME/${host}/g" nginx.conf
cd ..

docker build -t ycbr/nginx-reverse-proxy ./nginx-reverse-proxy
# docker build -t ycbr/react-ui ./react-ui
# docker build -t ycbr/nextjs-ui ./nextjs-ui
# docker build -t ycbr/native-ui ./native-ui
docker build -t ycbr/ui ./ui

docker compose -f compose-${host}.yml up -d

echo ""
echo "Open the following in a new private navigation window."

echo ""
echo "Keycloak as admin / admin:"
echo "http://${host}:7080/auth/admin/master/console/#/ycbr"

echo ""
echo "Sample frontends as user / user"
echo http://${host}:7080/react-ui/
echo http://${host}:7080/ui/
