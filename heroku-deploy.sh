#!/usr/bin/env bash
./gradlew clean -Pprod bootWar -x test
#heroku deploy:jar --jar build/libs/*.war --includes newrelic.jar:newrelic.yml
heroku deploy:jar --jar build/libs/*.war
