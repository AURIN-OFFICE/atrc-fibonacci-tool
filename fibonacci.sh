#!/bin/bash
docker build -t atrc-fibonacci .

export INPUTS_F0=0
export INPUTS_F1=1
export INPUTS_LENGTH=9

docker run \
  -e INPUTS_F0=${INPUTS_F0} \
  -e INPUTS_F1=${INPUTS_F1} \
  -e INPUTS_LENGTH=${INPUTS_LENGTH} \
  --mount type=bind,source="$(pwd)"/data/inputs,target=/data/inputs \
  --mount type=bind,source="$(pwd)"/data/outputs,target=/data/outputs \
  atrc-fibonacci:latest