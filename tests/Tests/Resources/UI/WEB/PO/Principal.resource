*** Settings ***
Resource    ../../../Commons.resource

*** Variables ***
${TITLE} =     PSA Gestión de Billeteras Digitales - Principal

*** Keywords ***
Open Browser To Login Page
    Open Browser    ${SERVER_HEROKU}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    

Abrir el Navegador en la Pantalla Principal
    [Documentation]    Abre el navegador en la pantalla pricipal del ERP.
    Open Browser To Login Page
    Location Should Be    ${SERVER_HEROKU}

La pantalla principal de PSA se debe abrir
    Title Should Be    ${TITLE} 
    
El ${USUARIO} ingresa a la plataforma
    Pass Execution    TODO: El ${USUARIO} ingresa a la aplicación.
    
Existe un ${USUARIO} dueño de una cuenta
    Pass Execution    TODO: verificar que el ${USUARIO} es dueño de la cuenta.    
    
Se muestra la lista de billeteras digitales asociadas al ${USUARIO}
    Pass Execution    TODO: buscar y traer todas las billeteras del ${USUARIO}    
    
Mostrar el nombre de ${USUARIO}
    Pass Execution    TODO: verificar el nombre de ${USUARIO} se muestre en la pantalla principal    
    
Mostrar la lista de billeteras disponibles
    Pass Execution    TODO: Buscar, listar y verificar el saldo de cada billetera asociada a la cuenta.
    
Mostrar el total de la cuenta del ${USUARIO}
    Pass Execution    Mostrar el saldo total del usuario en base a cada una de las billeteras asociadas.
    