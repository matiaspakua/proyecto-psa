#!/usr/bin/env bash

echo	Test Plan para ejecutar las pruebas de API de PSA usando Newman.

newman run 	../../Tests/API/api-pagos/TP001-verificar-crear-pagos_postman_collection.json\
 			-e ../../Resources/API/ENV/local_postgres-psa_postman_environment.json\
 			-r html,cli\
 			--reporter-html-export ../../Results/API/\
 			--color on
 			
newman run 	../../Tests/API/api-pagos/TP002-verificar_cancelar_pagos_postman_collection.json\
 			-e ../../Resources/API/ENV/local_postgres-psa_postman_environment.json\
 			-r html,cli\
 			--reporter-html-export ../../Results/API/\
 			--color on