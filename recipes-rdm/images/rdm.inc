RDM_BASE_INSTALL = "hp2sm \
	alsa-utils \
	cronie \
	fontconfig \
	liberation-fonts \
	date-set \
	e2fsprogs \
	e2fsprogs-tune2fs \
	attr \
	acl \
	btrfs-tools \
	dosfstools \
	initoverlay \
	system-image-update \
	prd-flash \
	boot-fsck \
	nano \
	sudo \
	qbrqry \
	nginx \
	nginx-legal \
	nginx-manual \
	tzdata \
	tcpdump \
	ifplugd \
	libstatgrab \
	procps \
	util-linux-mount \
	nfs-utils-client \
	ntp \
	ntpdate \
	ntp-utils \
	openvpn \
	util-linux \
	ethtool \
	wifi-fallback \
	crda \
	xz \
	lsof \
	logrotate \
"
RDM_BASE_INSTALL_append_mx6qcurie = "\
	ledctrl \
	fb-cursor-off \
"
RDM_INSTALL = "${RDM_BASE_INSTALL} \
	udev-extraconf \
	libntfs-3g \
	ntfsprogs \
	ntfs-3g \
	ksm-init \
"
RECOVER_INSTALL="parted \
"
SKIPPED_DEPS = "sysdetails \
	thermaldetails \
	"
