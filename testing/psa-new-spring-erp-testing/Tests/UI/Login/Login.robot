*** Settings ***
Resource    ../../../Resources/Commons.resource
Resource    ../../../Resources/UI/WEB/PO/Login.resource

*** Variables ***
${USUARIO}    admin
${PASSWORD}    password


*** Test Cases ***
TC001 - Login Correcto - S
    [Documentation]    Caso de prueba de la pantalla inicial de login de PSA.
    [Tags]    UI    Login    Regresion    Smoke
    [Teardown]    Close Browser
    
    Given Abrir el Navegador en la Pantalla de Login
    When El usuario "${USUARIO}" ingresa con la contraseña "${PASSWORD}"
    Then La pantalla principal de PSA se debe abrir

