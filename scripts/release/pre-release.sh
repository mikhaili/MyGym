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

if [ -z "$1" ]; then
    echo "Please provide a version number for release."
    exit 1
fi

export RELEASE_VERSION="$1"
export RELEASE_BRANCH="release/$RELEASE_VERSION"
export JIRA_TICKETS="INSERT_JIRA_TICKETS"
export CONFLUENCE_DETAILS="INSERT_CONFLUENCE_DETAILS"
export SDK_VERSION="$RELEASE_VERSION"
export GIT_SHA="`git log --pretty=format:'%h' -n 1`"

bash './scripts/release/checkout_master.sh'

#echo $JIRA_TICKETS >> jira-tickets.txt
#echo $CONFLUENCE_DETAILS >> confluence-log.txt

git checkout -b $RELEASE_BRANCH
updateSDKVersion $RELEASE_VERSION

git add .
git commit -m "Hotfix release: $RELEASE_BRANCH"
git push origin $RELEASE_BRANCH

#sh './release-candidate.sh'

#export S3_BUILD_NUMBER=4780
#sh './upload-private-git.sh'

# Add commands here to download and verify

#export TESTER_VERSION_CODE="`grep -oP 'versionCode\s+\K\d+' app/build.gradle`"
#sh './build-regression-tester.sh'

