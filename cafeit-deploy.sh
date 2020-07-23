#!/bin/bash
######################
# Author: Long Tran
######################

workspace_dir='/var/lib/jenkins/workspace'
build_dir='build'
built_date=''

funInit() {
	echo 'Starting deploy...'
	cd $workspace_dir
	if [ ! -d "$build_dir" ]; then
		sudo mkdir $build_dir
	fi
}

funBuildGradle() {
	cd $workspace_dir/$project_name
	sudo chmod +x gradlew
	echo 'Cleaning project...'
	sudo ./gradlew clean
	if [ $? -eq 0 ]; then
	   echo 'Clean project success!' 
	else
	   echo 'Clean project failed!'
	   exit 1
	fi
	echo 'Built stating...'
	sudo ./gradlew build -x test
	if [ $? -eq 0 ]; then
	   echo 'Build project success!' 
	else
	   echo 'Build project failed!'
	   exit 1
	fi
	local jar_file=$(find build/libs/ -type f -name "*.jar")

	built_date=$(date +%Y-%m-%d-%H%M%S)
	if [ ! -d "$built_date" ]; then
		sudo mkdir $workspace_dir/$build_dir/$built_date
	fi
	echo 'Copying Jar file...'
	sudo cp $jar_file $workspace_dir/$build_dir/$built_date/
}

funCreateFile() {
sudo sh -c "cat >>$service_file" <<-EOL
[Unit]
Description=Spring app
After=syslog.target
After=network.target[Service]
User=username
Type=simple

[Service]
ExecStart=/usr/bin/java -jar $execute_file
Restart=always
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=demojenkins

[Install]
WantedBy=multi-user.target
EOL
}

funCreateService() {
	echo 'Creating service...'
	cd $workspace_dir/$build_dir/$built_date/
	execute_file=$(find $workspace_dir/$build_dir/$built_date -type f -name "*.jar")
	funCreateFile
	service_path=/etc/systemd/system/$service_file
	sudo systemctl stop $service_name
	if [ -f "$service_path" ]; then
		sudo rm -f /etc/systemd/system/$service_file
	fi
	echo 'Copying service file...'
	sudo cp $service_file /etc/systemd/system/
}

funHelp() {
	echo ""
	echo "Usage: $0 -p project-name"
	echo -p "\t-p project name "
	exit 1
}

funRunService() {
	echo "Starting $service_name service..."
	sudo systemctl daemon-reload
	sudo systemctl start $service_name
	if [ $? -eq 0 ]; then
	   echo "Start service $service_name success!"
	else
	   echo 'Start service failed!'
	   exit 1
	fi
	echo 'Deploy finish!'
}

main() {
	funInit
	funBuildGradle
	funCreateService
	funRunService
}

while getopts "p:" opt
do
   case "$opt" in
      p ) project_name="$OPTARG" ;;
      ? ) funHelp ;;
   esac
done

if [ -z "$project_name" ]
then
   echo "Please set project name";
   funHelp
fi
service_name=$project_name
service_file=$service_name.service

main


