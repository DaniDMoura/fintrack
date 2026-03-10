#!/usr/bin/env bash

set -euo pipefail

USERNAME=$1
BUILD_VERSION_TAG=$2

docker build \
  -t "$USERNAME/wallet:latest" \
  -t "$USERNAME/wallet:$BUILD_VERSION_TAG" \
  .
