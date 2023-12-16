#!/bin/bash

detectOS() {
    unset OS
    if [[ "$(uname)" == "Darwin" ]]; then
        OS="Mac"
    elif [[ "$(uname)" == "Linux" ]]; then
        OS="Linux"
    else
        echo "Unsupported OS. Please use Linux or Mac."
        exit 1
    fi
}

updateSDKVersion() {
  case "$(uname -s)" in
    Linux*)
      sed -i 's/sdkVersion = .*/sdkVersion = "'$1'"/' $2
      ;;
    Darwin*)
      sed -i '' 's/sdkVersion = .*/sdkVersion = "'$1'"/' $2
      ;;
    *)
      echo "Unknown operating system."
      exit 1
      ;;
  esac
}

if [ -z "$1" ]; then
    echo "Please provide a version number for release."
    exit 1
fi

export RELEASE_VERSION="$1"
export DEFAULT_BUILD_GRADLE_PATH="app/build.gradle"
#if no parameter is passed, the default path is app/build.gradle
export BUILD_GRADLE_PATH=${2:-DEFAULT_BUILD_GRADLE_PATH}

updateSDKVersion $RELEASE_VERSION $BUILD_GRADLE_PATH
