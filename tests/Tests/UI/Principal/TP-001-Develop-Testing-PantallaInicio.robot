*** Settings ***
Resource    ../../Resources/Commons.resource
Resource    ../../Resources/UI/WEB/PO/Principal.resource

*** Variables ***


*** Keywords ***
    

*** Test Cases ***
TC-001 - Verificar Ingreso a la Pantalla Principal.
    [Documentation]    Verificar que la pantalla principal se cargue.
    [Tags]    UI    Smoke    ${URL_ISSUES_GITHUB}/6
    [Teardown]    Close Browser

    Given Abrir el Navegador en la Pantalla Principal
    When El usuario ingresa a la plataforma
    Then La pantalla principal de PSA se debe abrir
 