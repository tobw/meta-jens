DESCRIPTION = "RDM HP BLOB" 
HOMEPAGE = "http://www.rademacher.de"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "zway-blob hp2-base"
RDEPENDS_${PN} += "daemontools"
RDEPENDS_${PN} += "gnupg"
RDEPENDS_${PN} += "hp2-base"
RDEPENDS_${PN} += "hp2sm-system-date"
RDEPENDS_${PN} += "service-df"
RDEPENDS_${PN} += "zway-blob"

inherit record-installed-app gradlenative

SRCREV="7f33025668b631d39d245ac25816b3f6bd0bcab7"
SRCBRANCH="jethro-auesee"
SRCREPO="${PN}"
SRCUSER="rdm-dev"
PV = "4.0+git${SRCPV}"

SRC_URI = "\
    git://git@bitbucket.org/${SRCUSER}/${SRCREPO}.git;protocol=ssh;branch=${SRCBRANCH} \
    file://log4j-use-production-environment.patch \
    file://dfservice.run \
    file://dfservice-log.run \
    file://homepilot.run \
    file://homepilot-log.run \
    file://homepilot.sh \
    file://homepilot-backup-restore.run \
    file://homepilot-backup-restore-log.run \
    file://init_appdir.sh \
    file://gpg/pubring.gpg \
    file://gpg/random_seed \
    file://gpg/secring.gpg \
    file://gpg/trustdb.gpg \
    file://test-hp-backup.sh \
    file://test-hp-backup.crond \
"

S = "${WORKDIR}/git"
GRADLE_S = "${S}/HomePilot_Blob"
EXTRA_GRADLE_OPTS = "-Pdestdir=${D}"
GRADLE_COMPILE_TARGET = "jar"

JAVA_ELF="/usr/bin/java"

HOMEPILOT_USER = "homepilot"
HOMEPILOT_USER_HOME = "/home/homepilot"

INST_DEST_PREFIX="/opt/homepilot"

SVC_SERVICES="${sysconfdir}/daemontools/service"

do_install_append () {
    # create homepilot user dir
	install -o homepilot -g users -m 0755 -d ${D}${HOMEPILOT_USER_HOME}
	install -o homepilot -g users -m 0755 -d ${D}${HOMEPILOT_USER_HOME}/bin
	install -o homepilot -g users -m 0755 ${WORKDIR}/init_appdir.sh ${D}${HOMEPILOT_USER_HOME}/bin/init_appdir.sh
	install -o homepilot -g users -m 0755 -d ${D}${INST_DEST_PREFIX}/bin

        # Install some gpg stuff
        install -o homepilot -g users -m 0700 -d ${D}${HOMEPILOT_USER_HOME}/.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/pubring.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/pubring.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/random_seed ${D}${HOMEPILOT_USER_HOME}/.gpg/random_seed
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/secring.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/secring.gpg
        install -o homepilot -g users -m 0600 ${WORKDIR}/gpg/trustdb.gpg ${D}${HOMEPILOT_USER_HOME}/.gpg/trustdb.gpg

	# Install all the init-scripts
	# 1 Create all the folders
	install -d ${D}${SVC_SERVICES}/homepilot-backup-restore
	install -d ${D}${SVC_SERVICES}/homepilot-backup-restore/log
	install -d ${D}${SVC_SERVICES}/dfservice
	install -d ${D}${SVC_SERVICES}/dfservice/log
	install -d ${D}${SVC_SERVICES}/homepilot
	install -d ${D}${SVC_SERVICES}/homepilot/log

	# 2 Move all the run-files
	install -m 0755 ${WORKDIR}/homepilot-backup-restore.run ${D}${SVC_SERVICES}/homepilot-backup-restore/run
	install -m 0755 ${WORKDIR}/homepilot-backup-restore-log.run ${D}${SVC_SERVICES}/homepilot-backup-restore/log/run
	install -m 0755 ${WORKDIR}/dfservice.run ${D}${SVC_SERVICES}/dfservice/run
	install -m 0755 ${WORKDIR}/dfservice-log.run ${D}${SVC_SERVICES}/dfservice/log/run
	install -m 0755 ${WORKDIR}/homepilot.run ${D}${SVC_SERVICES}/homepilot/run
	install -m 0755 ${WORKDIR}/homepilot.sh ${D}${INST_DEST_PREFIX}/bin/homepilot
	install -m 0755 ${WORKDIR}/homepilot-log.run ${D}${SVC_SERVICES}/homepilot/log/run

	# 3 Adjust parameters
	sed -i -e "s,@HOMEPILOT_BASE@,${INST_DEST_PREFIX},g" -e "s,@JAVA_ELF@,${JAVA_ELF},g" \
	    -e "s,@HOMEPILOT_USER_HOME@,${HOMEPILOT_USER_HOME},g" -e "s,@HOMEPILOT_USER@,${HOMEPILOT_USER},g" \
	    ${D}${SVC_SERVICES}/homepilot-backup-restore/run \
	    ${D}${SVC_SERVICES}/dfservice/run \
	    ${D}${SVC_SERVICES}/homepilot/run \
	    ${D}${INST_DEST_PREFIX}/bin/homepilot \
	    ${D}${HOMEPILOT_USER_HOME}/bin/init_appdir.sh

	# 3 Disable all but hp
	touch ${D}${SVC_SERVICES}/dfservice/down
	touch ${D}${SVC_SERVICES}/homepilot-backup-restore/down

	install -d ${D}${sysconfdir}/cron.d
	install -m 0600 ${WORKDIR}/test-hp-backup.crond ${D}${sysconfdir}/cron.d/test-hp-backup
	install -m 0755 ${WORKDIR}/test-hp-backup.sh ${D}${INST_DEST_PREFIX}/bin/test-hp-backup.sh
}

FILES_${PN} += "${INST_DEST_PREFIX} \
		${SVC_SERVICES} \
		${HOMEPILOT_USER_HOME} \
"
