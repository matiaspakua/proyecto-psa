*** Settings ***
Documentation
...    file:///${EXECDIR}/Tests/Resources/images/PSA_LOGO.png
...    
...    *ISSUE*: ${URL_ISSUES_GITHUB}/6\n
...
...    *FEATURE*: 001-Develop-Planeacion-US-Pantalla de Inicio.\n
...
...    *RULE*: Se deberá validar que al ingresar a la aplicación,}
...    aparezca una pantalla inicial con el nombre de usuario y
...    todas las billeteras digitales que tiene asociadas.\n
...    
...    *SRS:* 006-Discovery-Especificación-SRS


Resource    ../../Resources/Commons.resource
Resource    ../../Resources/UI/WEB/PO/Principal.resource


*** Variables ***
${USUARIO}    Juan Perez

*** Test Cases ***
TC-001 - Scenario: Verificar Ingreso a la Pantalla Principal
    [Documentation]    Verificar que la pantalla principal se cargue.
    [Tags]    UI    Smoke    REQ-008    REQ-009
    [Teardown]    Close Browser

    Given Abrir el Navegador en la Pantalla Principal
    When El ${USUARIO} ingresa a la plataforma
    Then La pantalla principal de PSA se debe abrir
 
TC-002 - Scenario: Verificar el usuario dueño de la cuenta
    [Documentation]    Verificar que cu
    [Tags]    UI    Smoke    REQ-008    REQ-009
    [Teardown]    Close Browser
    
    Given Abrir el Navegador en la Pantalla Principal
    When El ${USUARIO} ingresa a la plataforma
    And Existe un ${USUARIO} dueño de una cuenta
    And Se muestra la lista de billeteras digitales asociadas al ${USUARIO}
    Then Mostrar el nombre de ${USUARIO}
    And Mostrar la lista de billeteras disponibles
    And Mostrar el total de la cuenta del ${USUARIO}