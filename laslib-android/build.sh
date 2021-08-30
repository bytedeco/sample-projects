#!/bin/bash
set -e
set +x


if [[ ! -d "$ANDROID_HOME/build-tools" ]]; then
    echo "Please download Android SDK first and set the SDK path to $ANDROID_HOME."
    exit
fi

cd LAStools/src/main
if [[ ! -d "cpp" ]]; then
  git clone https://github.com/LAStools/LAStools.git cpp
fi
cd cpp
git checkout 9ecb4e682153436b044adaeb3b4bfdf556109a0f

echo "Decompressing modification archives..."
tar -xzvf "../ndk_modifications.tgz"


cd ../../../../

./gradlew assemble

