#!/usr/bin/env bash

echo	Test Plan para el modulo de Login.

robot	--consolecolors ON \
		--consolewidth 120\
		--timestampoutputs \
		--outputdir ../../Results/UI/\
		--debugfile ../../Results/UI/detailed.log \
		../../Tests/UI/Login/Login.robot\
		../../Tests/UI/Dashboard/VerificarVersion.robot
		../../Tests/UI/Dashboard/VerificarVersion.robot
		../../Tests/UI/Dashboard/VerificarVersion.robot
		../../Tests/UI/Dashboard/VerificarVersion.robot