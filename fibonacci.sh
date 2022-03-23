#!/bin/bash
docker build -t atrc-fibonacci .

mkdir atrc_data
mkdir atrc_data/inputs
mkdir atrc_data/outputs
cp test_parameters.yaml atrc_data/parameters.yaml

docker run \
  --mount type=bind,source="$(pwd)"/atrc_data,target=/atrc_data \
  atrc-fibonacci:latest