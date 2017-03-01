#!/bin/sh

echo Build number is:
grep build.number build.number | sed 's/^.*=//g'
