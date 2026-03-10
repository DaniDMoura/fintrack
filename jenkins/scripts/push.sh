#!/usr/bin/env bash

set -euo pipefail

USERNAME=$1
PASSWORD=$2
BUILD_VERSION_TAG=$3

echo "$PASSWORD" | docker login \
  --username "$USERNAME" \
  --password-stdin

docker push "$USERNAME/wallet:latest"
docker push "$USERNAME/wallet:$BUILD_VERSION_TAG"
