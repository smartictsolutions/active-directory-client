#!/bin/bash

cd ../../backend/active-directory-client/ || exit 1
./gradlew clean build test spotlessApply
./gradlew dockerSaveActiveDirectoryClientImage || { printf "\dockerSaveActiveDirectoryClientImage başarısız! gradlew hata kodu: %s\n" "$?"; exit 1; }

cd ../../DevOps/docker/ || exit 1

echo "$(tput setaf 2)build.sh tamamlandı.$(tput setaf 7)"

exit 0