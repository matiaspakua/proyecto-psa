#!/usr/bin/env bash

echo	=== Regression Test Plan para el modulo Principal ===

robot	--consolecolors ON \
		--consolewidth 180\
		--timestampoutputs \
		--outputdir ../../Results/UI/\
		--debugfile ../../Results/UI/detailed.log \
		../../Tests/UI/Principal/TP-001-Develop-Testing-PantallaInicio.robot \
		../../Tests/UI/Principal/TP-002-Develop-Testing-DetalleDeTotalDeBilleterasDigitales.robot \
		../../Tests/UI/Principal/TP-003-Develop-Testing-DepositoDeDinero.robot \
		../../Tests/UI/Principal/TP-004-Develop-Testing-ExtracionDeDinero.robot \
		../../Tests/UI/Principal/TP-005-Develop-Testing-ListaDeMovimientoDeBilleterasDigitales.robot \
		../../Tests/UI/Principal/TP-006-Develop-Testing-AsociarBilleteraDigitalDeCliente.robot \
		../../Tests/UI/Principal/TP-007-Develop-Testing-EliminarBilleteraDigitalDeCliente.robot \
		../../Tests/UI/Principal/TP-008-Develop-Testing-ListaDeMovimientosPosiblesYNoPosibles.robot \
		../../Tests/UI/Principal/TP-009-Develop-Testing-CrearUnaNuevaBilleteraDigitalEnUnaCuentaEnMenosDe3Minutos.robot \
		../../Tests/UI/Principal/TP-010-Develop-Testing-AgregarFuncionalidadDeSugerenciaBotonCallToAction.robot \
		../../Tests/UI/Principal/TP-011-Develop-Testing-FiltroDeFechasParaMovimientoDeCuentas.robot