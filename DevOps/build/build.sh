#!/bin/bash

cd ../../backend/active-directory-client/ || exit 1
./gradlew dockerSaveActiveDirectoryImage || { printf "\dockerSaveActiveDirectoryImage başarısız! gradlew hata kodu: %s\n" "$?"; exit 1; }

echo "$(tput setaf 2)build.sh tamamlandı.$(tput setaf 7)"

exit 0