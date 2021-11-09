#!/usr/bin/env bash

echo	Test Plan para ejecutar las pruebas de API de PSA usando Newman.

newman run 	../../Tests/API/api-pagos/TP-001-Develop-Testing-BakcEndApiPagosPostmanCollection.json\
 			-e ../../Resources/API/ENV/Local.postman_environment.json\
 			-r html,cli\
 			--reporter-html-export ../../Results/API/\
 			--color on
 			
newman run 	../../Tests/API/api-pagos/TP-001-Develop-Testing-BakcEndApiPagosPostmanCollection.json\
 			-e ../../Resources/API/ENV/Heroku-Deploy.postman_environment.json\
 			-r html,cli\
 			--reporter-html-export ../../Results/API/\
 			--color on