#!/bin/sh


main()
{
	
	runContainer $*
}

runContainer()
{
	serviceName=$1
	imageName=$2
	dockerContainerName=$3
	dockerArgs=$4
	commandInfo=$5

	runService	
}

pullImage()
{
	echo "Pulling Image : $imageName"
	echo "sudo docker pull $imageName"
	sudo docker pull $imageName
}

runService()
{
	allContainerId=`sudo docker ps -a | grep $dockerContainerName | awk '{print $1}'`
	runContainerId=`sudo docker ps | grep $dockerContainerName | awk '{print $1}'`

	if [ -n "$runContainerId" ]
	then
		echo "container already running"
		exit 0
	fi		

	if [ -n "$allContainerId" ]
	then
		echo "A stopped container"
		sudo docker rm $allContainerId
	fi
	dockerArguments=`echo $dockerArgs | tr "@" " "`
	command=`echo $commandInfo | tr "@" " "`
	pullImage
	echo "Starting service $serviceName in container"
	echo "sudo docker run $dockerArguments --name=$dockerContainerName $imageName $command"
	sudo docker run $dockerArguments --name=$dockerContainerName $imageName $command	
}

getContainerId()
{
	containerName=$1
	sudo docker ps -a | grep $containerName | awk '{print $1}'
		
}

killContainer()
{
	containerName=$1
	echo "Killing container with name $containerName"
	containerId=`sudo docker ps -a | grep $containerName | awk '{print $1}'`
	if [ -n "$containerId" ]
	then
		sudo docker kill $containerId
		sudo docker rm $containerId
	fi	
	
}

isContainerRunning()
{
	cntrName=$1
	
}

$@
