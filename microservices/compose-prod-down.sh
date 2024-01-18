#!/bin/bash

docker compose -p social-media-platform-prod -f compose-prod.yml -f compose-prod-secrets.yml down