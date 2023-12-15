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
      sed -i 's/sdkVersion = .*/sdkVersion = "'$1'"/' build.gradle
      ;;
    Darwin*)
      sed -i '' 's/sdkVersion = .*/sdkVersion = "'$1'"/' build.gradle
      ;;
    *)
      echo "Unknown operating system."
      exit 1
      ;;
  esac
}

# Shared scripts directory
export SHARED_SCRIPTS_DIR="./scripts/release"
# replace with release branch prefix (e.g. release)
export BRANCH_RELEASE_PREFIX="test_r"

export RELEASE_VERSION="$1"
export RELEASE_BRANCH="$BRANCH_RELEASE_PREFIX/$RELEASE_VERSION"

export SDK_VERSION="$RELEASE_VERSION"
export GIT_SHA="`git log --pretty=format:'%h' -n 1`"

#bash "$SHARED_SCRIPTS_DIR/checkout_master.sh"

git checkout -b $RELEASE_BRANCH
updateSDKVersion $RELEASE_VERSION

git add .
git commit -m "Hotfix release: $RELEASE_BRANCH"
git push origin $RELEASE_BRANCH
