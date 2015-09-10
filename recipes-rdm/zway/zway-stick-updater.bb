DESCRIPTION = "RDM Zway Stick Updater"
HOMEPAGE = "http://www.rademacher.de"

LICENSE = "commercial"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"

FILESEXTRAPATHS_prepend := "${THISDIR}/z-way-stick-updater:"

PV = "0.4"

SRC_URI = "http://internal.rdm.local/blobs/z-way-stick-updater-${PV}.tar.gz;name=updater;unpack=0"

DEPENDS = "hp2-base"
RDEPENDS_${PN} += "hp2-base"
RDEPENDS_${PN} += "python-fcntl"
RDEPENDS_${PN} += "python-logging"
RDEPENDS_${PN} += "python-pyserial"

SRC_URI[updater.md5sum] = "0de675f8fc08cf83ccc363404806fb11"
SRC_URI[updater.sha256sum] = "1006970f683c5755a1260f56e609a7d4b103900519b264abcae2f80dd2a63c1d"

INST_DEST_PREFIX="/opt/z-way-stick-updater"

do_install() {
    install -d ${D}${INST_DEST_PREFIX}
    (cd ${D}${INST_DEST_PREFIX} && tar xf ${WORKDIR}/z-way-stick-updater-${PV}.tar.gz)
}

FILES_${PN} += "${INST_DEST_PREFIX}"
