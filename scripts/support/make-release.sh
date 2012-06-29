#!/bin/bash

# This script packages up an end-user version of Joshua for download.

set -u

version=$1

cd $JOSHUA
ant clean java documentation
[[ ! -d release ]] && mkdir release
ln -s $JOSHUA joshua-$version

wget -r joshua-decoder.org

tar czf release/joshua-$version.tgz \
    --exclude='*~' --exclude='#*' \
    joshua-$version/{README,build.xml,logging.properties} \
    joshua-$version/src \
    joshua-$version/bin \
    joshua-$version/lib/{*jar,eng_sm6.gr,README,LICENSES} \
    joshua-$version/scripts \
    joshua-$version/examples \
    joshua-$version/thrax/bin/thrax.jar \
		joshua-$version/joshua-decoder.org \
    joshua-$version/{joshua-decoder,extract-1best,visualize.sh} 

rm -f joshua-$version
