#!/bin/bash
set -e
curl -O http://192.168.56.10:11111/config/coreos1.yml
sudo coreos-install -d /dev/sda -c coreos1.yml -b http://192.168.56.10:11111/iso
sudo reboot
