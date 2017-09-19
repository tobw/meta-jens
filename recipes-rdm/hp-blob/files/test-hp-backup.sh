#!/bin/sh

set -e

echo "Creating backup"
curl -sS 'http://127.0.0.1/configajax.do' -d 'createBackup=1' | grep -q 'uisuccess'
test -f '/tmp/backup.hpj' && rm '/tmp/backup.hpj'
echo "Downloading backup"
curl -sS 'http://127.0.0.1/config.do?downloadbackup=1' -o '/tmp/backup.hpj'
echo "Restoring backup"
test -s '/tmp/backup.hpj' && curl -sS 'http://127.0.0.1/configajax.do?uploadBackup=1' -F 'data=@/tmp/backup.hpj'
