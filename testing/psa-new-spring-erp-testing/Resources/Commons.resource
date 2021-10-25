*** Settings ***
Documentation     A resource file with reusable keywords and variables.
...
...               The system specific keywords created here form our own
...               domain specific language. They utilize keywords provided
...               by the imported SeleniumLibrary.

Library    SeleniumLibrary    WITH NAME    Selenium 
Library    OperatingSystem    WITH NAME    OS

*** Variables ***
${SERVER_LOCAL}         localhost:8080/
${BROWSER}        Chrome
${DELAY}          0