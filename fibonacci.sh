#!/bin/bash
docker build -t atrc-fibonacci .

mkdir data
mkdir data/inputs
mkdir data/outputs
cp test_parameters.yaml data/parameters.yaml

docker run \
  --mount type=bind,source="$(pwd)"/data,target=/data \
  atrc-fibonacci:latest