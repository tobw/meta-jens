SUMMARY = "An image for HP1 or HP2."
LICENSE = "MIT"
PR="r0"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

include recipes-core/images/core-image-base.bb
include rdm.inc
include rdm-hp2.inc

inherit core-image distro_features_check

# Add extra image features
EXTRA_IMAGE_FEATURES += " \
    nfs-server \
    ssh-server-dropbear \
"

IMAGE_INSTALL += " \
	${CORE_IMAGE_BASE_INSTALL} \
	${HP2_INSTALL} \
	${RDM_INSTALL} \
	netaddr-ip-perl \
	perl-modules \
	python3 \
	python3-misc \
	python3-modules \
"

IMAGE_INSTALL_remove = "hp-blob"
IMAGE_INSTALL_remove = "hp2sm"
IMAGE_INSTALL_remove = "hp2sm-ethtool"
IMAGE_INSTALL_remove = "hp2sm-system-wrc${WRC_VERSION}"
IMAGE_INSTALL_remove = "hp2sm-zway"
IMAGE_INSTALL_remove = "service-df"
IMAGE_INSTALL_remove = "system-image-update"

export IMAGE_BASENAME = "rdm-min-image"
