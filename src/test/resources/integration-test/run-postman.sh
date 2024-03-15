#!/bin/sh
newman run /postman/prova.postman_collection.json -r cli,json,htmlextra \
--reporter-json-export output/result.json --reporter-htmlextra-export output/result.html \
--env-var "baseUrl=http://host.testcontainers.internal:8086"
