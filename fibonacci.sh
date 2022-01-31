#!/bin/bash
docker build -t atrc-fibonacci .

mkdir data
mkdir data/inputs
mkdir data/outputs
cp test_data.yaml data/inputs/inputs.yaml

docker run \
  --mount type=bind,source="$(pwd)"/data/inputs,target=/data/inputs \
  --mount type=bind,source="$(pwd)"/data/outputs,target=/data/outputs \
  atrc-fibonacci:latest