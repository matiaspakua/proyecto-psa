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
    
El usuario ingresa a la plataforma
    Pass Execution    Nada para hacer    

La pantalla principal de PSA se debe abrir
    Title Should Be    ${TITLE} 