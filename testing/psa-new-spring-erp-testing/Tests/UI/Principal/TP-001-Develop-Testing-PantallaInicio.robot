*** Settings ***
Resource    ../../../Resources/Commons.resource
Resource    ../../../Resources/UI/WEB/PO/Principal.resource

Suite Setup    Login

*** Variables ***
${USUARIO}    admin
${PASSWORD}    password

*** Keywords ***
Login
    [Teardown]    Close Browser
    Given Abrir el Navegador en la Pantalla de Login
    When El usuario "${USUARIO}" ingresa con la contraseña "${PASSWORD}"
    Then La pantalla principal de PSA se debe abrir
    

*** Test Cases ***
TC-001 - Verificar Version de PSA
    [Documentation]    Verificar la versión actual de PSA en ejecución
    [Tags]    UI    Regresion
    [Teardown]    Close Browser
    Login
    Pass Execution    TODO    
    

TC001 - Login Correcto - S
    [Documentation]    Caso de prueba de la pantalla inicial de login de PSA.
    [Tags]    UI    Login    Regresion    Smoke
    [Teardown]    Close Browser
    
    Given Abrir el Navegador en la Pantalla de Login
    When El usuario "${USUARIO}" ingresa con la contraseña "${PASSWORD}"
    Then La pantalla principal de PSA se debe abrir

    Log To Console    paso 1  