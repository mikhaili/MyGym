#!/bin/bash

# Get a git SHA from the most recent commit
export GIT_SHA="$(git log --pretty=format:'%h' -n 1)"

# Clone and pull the latest from private git repo
#git clone https://github.com/username/repo.git
#cd repo
#git pull origin

# Assuming the aar file is in the root directory of your repo
AAR_FILE=$(ls | grep ".aar")

# Extract aar file and decompile classes.jar using jadx
mkdir aar_content
cd aar_content
unzip ../${AAR_FILE}
jadx classes.jar

# Check VERSION value and GIT_SHA
VERSION=$(grep -r -oP 'public static final String SDK_VERSION = "\K[^"]*' .)
SHA=$(grep -r -oP 'public static final String GIT_SHA = "\K[^"]*' .)

if [[ "$VERSION" != "$SDK_VERSION" ]] || [[ "$SHA" != "$GIT_SHA" ]]; then
    echo "VERSION or GIT_SHA is incorrect in aar file"
    exit 1
else
    echo "VERSION and GIT_SHA are correct in aar file"
fi

# Return to the initial directory
cd ../..

# Remove cloned repo
rm -rf repo
