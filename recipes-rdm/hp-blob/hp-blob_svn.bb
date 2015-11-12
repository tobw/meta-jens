DESCRIPTION = "RDM HP BLOB" 
HOMEPAGE = "http://www.rademacher.de"
LICENSE = "commercial"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "zway-blob hp2-base"
RDEPENDS_${PN} += "hp2-base"
RDEPENDS_${PN} += "daemontools"
RDEPENDS_${PN} += "at"
RDEPENDS_${PN} += "gnupg"
RDEPENDS_${PN} += "service-df"
RDEPENDS_${PN} += "zway-blob"

inherit record-installed-app

HPREV="4777"
PV = "4.0.${HPREV}"
SRC_URI = "svn://192.168.1.186/svn/EW_Prj/001/HP_Blob/trunk/;protocol=http;module=HomePilot_Blob;rev=${HPREV} \
                file://dfservice.run \
                file://dfservice-log.run \
                file://homepilot.run \
                file://homepilot-log.run \
                file://homepilot.sh \
                file://homepilot-network-manager.run \
                file://homepilot-network-manager-log.run \
                file://jetty.run \
                file://jetty-log.run \
                file://jetty.sh \
                file://z-way.run \
                file://z-way-log.run \
                file://init_appdir.sh \
                file://gpg/pubring.gpg \
                file://gpg/random_seed \
                file://gpg/secring.gpg \
                file://gpg/trustdb.gpg \
"

S = "${WORKDIR}/HomePilot_Blob"

JAVA_ELF="/usr/java/bin/java"

HOMEPILOT_USER = "homepilot"
HOMEPILOT_USER_HOME = "/home/homepilot"

ZWAY_DEST_PREFIX="/opt/z-way"
INST_DEST_PREFIX="/opt/homepilot"
jetty_home="/opt/homepilot/opt/jetty"
# XXX Pat should finally fix that
TARBALL_NAME="hp-dist_4.0.0.0.tar.gz"

SVC_SERVICES="${sysconfdir}/daemontools/service"

do_install() {
        # create homepilot user dir
	install -o homepilot -g users -m 0755 -d ${D}${HOMEPILOT_USER_HOME}
	install -o homepilot -g users -m 0755 -d ${D}${HOMEPILOT_USER_HOME}/bin
	install -o homepilot -g users -m 0755 ${WORKDIR}/init_appdir.sh ${D}${HOMEPILOT_USER_HOME}/bin/init_appdir.sh

	# create INST_DEST_PREFIX folder
	install -d ${D}${INST_DEST_PREFIX}

	# Extract tarball into INST_DEST_PREFIX dir of target
	tar xzf ${S}/${TARBALL_NAME} -C ${D}${INST_DEST_PREFIX}

	# Clean-up messed up so-files from Jetty distribution ... 
	find ${D}${INST_DEST_PREFIX} -name '*.so' | xargs rm -f

        # Install some gpg stuff
        install -o homepilot -g users -m 0700 -d ${D}${HOMEPILOT_USER_HOME}/.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/pubring.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/pubring.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/random_seed ${D}${HOMEPILOT_USER_HOME}/.gpg/random_seed
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/secring.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/secring.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/trustdb.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/trustdb.gpg

	# Install all the init-scripts
	# 1 Create all the folders
	install -d ${D}${SVC_SERVICES}/homepilot-network-manager
	install -d ${D}${SVC_SERVICES}/homepilot-network-manager/log
	install -d ${D}${SVC_SERVICES}/dfservice
	install -d ${D}${SVC_SERVICES}/dfservice/log
	install -d ${D}${SVC_SERVICES}/homepilot
	install -d ${D}${SVC_SERVICES}/homepilot/log
	install -d ${D}${SVC_SERVICES}/jetty
	install -d ${D}${SVC_SERVICES}/jetty/log
	install -d ${D}${SVC_SERVICES}/z-way
	install -d ${D}${SVC_SERVICES}/z-way/log

	# 2 Move all the run-files
	install -m 0755 ${WORKDIR}/homepilot-network-manager.run ${D}${SVC_SERVICES}/homepilot-network-manager/run
	install -m 0755 ${WORKDIR}/homepilot-network-manager-log.run ${D}${SVC_SERVICES}/homepilot-network-manager/log/run
	install -m 0755 ${WORKDIR}/dfservice.run ${D}${SVC_SERVICES}/dfservice/run
	install -m 0755 ${WORKDIR}/dfservice-log.run ${D}${SVC_SERVICES}/dfservice/log/run
	install -m 0755 ${WORKDIR}/homepilot.run ${D}${SVC_SERVICES}/homepilot/run
	install -m 0755 ${WORKDIR}/homepilot.sh ${D}${INST_DEST_PREFIX}/bin/homepilot
	install -m 0755 ${WORKDIR}/homepilot-log.run ${D}${SVC_SERVICES}/homepilot/log/run
	install -m 0755 ${WORKDIR}/jetty.run ${D}${SVC_SERVICES}/jetty/run
	install -m 0755 ${WORKDIR}/jetty-log.run ${D}${SVC_SERVICES}/jetty/log/run
	install -m 0755 ${WORKDIR}/jetty.sh ${D}${INST_DEST_PREFIX}/bin/jetty
	install -m 0755 ${WORKDIR}/z-way.run ${D}${SVC_SERVICES}/z-way/run
	install -m 0755 ${WORKDIR}/z-way-log.run ${D}${SVC_SERVICES}/z-way/log/run

	# 
	sed -i -e "s,@HOMEPILOT_BASE@,${INST_DEST_PREFIX},g" -e "s,@ZWAY_BASE@,${ZWAY_DEST_PREFIX},g"  \
	    -e "s,@JETTY_BASE@,${INST_DEST_PREFIX}/opt/jetty,g" -e "s,@JAVA_ELF@,${JAVA_ELF},g" \
	    -e "s,@HOMEPILOT_USER_HOME@,${HOMEPILOT_USER_HOME},g" -e "s,@HOMEPILOT_USER@,${HOMEPILOT_USER},g" \
	    ${D}${SVC_SERVICES}/homepilot-network-manager/run \
	    ${D}${SVC_SERVICES}/dfservice/run \
	    ${D}${SVC_SERVICES}/homepilot/run \
	    ${D}${INST_DEST_PREFIX}/bin/homepilot \
	    ${D}${SVC_SERVICES}/jetty/run \
	    ${D}${INST_DEST_PREFIX}/bin/jetty \
	    ${D}${SVC_SERVICES}/z-way/run \
	    ${D}${HOMEPILOT_USER_HOME}/bin/init_appdir.sh

	# 3 Disable all but hp
	touch ${D}${SVC_SERVICES}/dfservice/down
	touch ${D}${SVC_SERVICES}/jetty/down
	touch ${D}${SVC_SERVICES}/z-way/down
}

FILES_${PN} += "${INST_DEST_PREFIX} \
                ${ZWAY_DEST_PREFIX} \
		${SVC_SERVICES} \
		${HOMEPILOT_USER_HOME} \
"
