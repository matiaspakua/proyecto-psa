*** Settings ***
Resource    ../../../Commons.resource
Resource    Dashboard.resource

*** Variables ***


*** Keywords ***
Abrir el Navegador en la Pantalla de Login
    [Documentation]    una keyword muy simple para la pantalla de login
    Pass Execution    TODO    
    
El usuario "${username}" ingresa con la contraseña "${password}"
    Input Text    username    ${username}
    Input Text    password    ${password}
    Click Button    submit

La pantalla principal de PSA se debe abrir
    Location Should Be    ${WELCOME URL}
    Title Should Be    ${TITLE} 
