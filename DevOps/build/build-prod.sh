#!/bin/bash


#Jenkinsfile stage -> Docker_Build: ACTIVE_DIRECTORY_APP
cd ../../backend/active-directory-client/ || exit 1
./gradlew -PclientEnv=prod dockerSaveActiveDirectoryImage || { printf "\ndockerSaveActiveDirectoryImage başarısız! gradlew hata kodu: %s\n" "$?"; exit 1; }

echo "$(tput setaf 2)build-prod.sh tamamlandı.$(tput setaf 7)"

exit 0
