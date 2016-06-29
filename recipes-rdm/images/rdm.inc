RDM_ESSENTIALS = "\
	attr \
	acl \
	e2fsprogs \
	e2fsprogs-tune2fs \
	busybox-ifplugd \
	nfs-utils-client \
	ntp \
	ntpdate \
	ntp-utils \
	prd-flash-${WANTED_ROOT_DEV} \
	procps \
	qbrqry \
	tcpdump \
	tzdata \
	util-linux-mount \
	xz \
"
RDM_BASE_INSTALL = "\
	${RDM_ESSENTIALS} \
	date-set \
	dnsmasq \
	cronie \
	dosfstools \
	sudo \
	nginx \
	nginx-legal \
	nginx-manual \
	statgrab \
	saidar \
	crda \
	logrotate \
	boot-fsck \
	initoverlay-${WANTED_ROOT_DEV} \
	udev-extraconf-mount-blacklist-${WANTED_ROOT_DEV} \
	system-image-update \
	hp2sm \
	hp2sm-ethtool \
"

RDM_BASE_INSTALL_append_curie = "\
	wa-mmc-timeout \
"
RDM_BASE_INSTALL_append_bohr = "\
	wa-mmc-timeout \
"
RDM_ESSENTIALS_append_bohr = "\
	ledctrl \
"
RDM_ESSENTIALS_append_bohr-update = "\
	date-set \
"
RDM_ESSENTIALS_append_curie = "\
	btrfs-tools \
	ledctrl \
	fb-cursor-off \
"
RDM_INSTALL = "${RDM_BASE_INSTALL} \
	delivery-reset \
	ksm-init \
	openvpn \
"
RDM_INSTALL_append_bohr = "\
	udev-rules-bohr \
"
RDM_INSTALL_append_curie = "\
	udev-rules-imx \
	libntfs-3g \
	ntfsprogs \
	ntfs-3g \
"
RECOVER_INSTALL="\
"
SKIPPED_DEPS = "sysdetails \
	thermaldetails \
	mmc-slowdown \
	stressapptest \
	collectd-graphs \
	wifi-fallback \
	"
