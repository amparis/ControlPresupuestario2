

// Función para actualizar 'inputCargoItem' con el valor de 'inputItem' o 'selectCargo'
function updateCargoItem(value) {
    document.getElementById("inputCargoItem").value = value;
}






// Llama a esta función para actualizar el valor antes de enviar el formulario
function prepareMonto(elementId) {
	
    const element = document.getElementById(elementId);
    element.value = element.value.replace(/,/g, ''); // Elimina comas antes de enviar al servidor
}

    
        // Función para prevenir el envío del formulario con la tecla Enter
        function disableEnterKey(event) {
            if (event.key === "Enter") {
                event.preventDefault(); // Previene la acción predeterminada (enviar el formulario)
            }
        }
        // Añadir el listener al cargar el documento
        document.addEventListener("DOMContentLoaded", function() {
            const form = document.getElementById("miFormulario"); // Cambia "miFormulario" por el ID de tu formulario
            form.addEventListener("keypress", disableEnterKey);
        });

    

function loadBeneficiaryData() {
    var select = document.getElementById("beneficiarioE");
    var selectedOption = select.options[select.selectedIndex];

    // Obtener los datos del beneficiario seleccionado
    var tipo = selectedOption.getAttribute("data-tipo");
    var documento = selectedOption.getAttribute("data-documento");
    var nombres = selectedOption.getAttribute("data-nombres");
    var apellidos = selectedOption.getAttribute("data-apellidos");
   
    // Actualizar los campos de entrada
    document.getElementById("inputIdBeneficiario").value = selectedOption.value;
    document.getElementById("inputBeneficiario").value = nombres + ' ' + apellidos;

    // Actualizar las etiquetas <p>
    document.getElementById("beneficiarioTipo").textContent = tipo;
    document.getElementById("beneficiarioDocumento").textContent = documento;
    document.getElementById("beneficiarioNombre").textContent = nombres + ' ' + apellidos;
    
    document.getElementById("flexSwitchCheckDefaultDiv").style.display = "block";

}
   

function openModal() {
    // Limpiar el campo ID para crear un nuevo registro
    document.getElementById("inputId").value = 0;
    
    // Limpiar otros campos del formulario si es necesario
    document.getElementById("inputdocumento").value = "";
    // Limpiar otros campos del formulario...

    // Mostrar el modal
    $('#myModal').modal('show');
}


function toggleFields(selectElement) {
    // Obtener el valor de `nombreClase` del atributo data-nombreClase del option seleccionado
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    const nombreClase = selectedOption.getAttribute("data-nombreClase");
    const formaPago = selectedOption.getAttribute("data-formaPago");// Obtener la forma de pago de clasificacion

    const expenseObjetoPuestoLabel = document.getElementById("expenseObjetoPuesto");
    const expenseCargoItemLabel = document.getElementById("expenseCargoItem");
    const expensePrecioUnitarioMontoLabel = document.getElementById("expensePrecioUnitarioMonto");
    const inputCargoItem = document.getElementById("inputCargoItem");
    const inputItem = document.getElementById("inputItem");
    const selectCargo = document.getElementById("selectCargo");
    const btnAddCargo = document.getElementById("btnAddCargo");

    document.getElementById("inputPago").value = formaPago;
    
    if (nombreClase === "STAFF") {
        expenseObjetoPuestoLabel.textContent = "Job Position";
        expenseCargoItemLabel.textContent = "Scale";
        expensePrecioUnitarioMontoLabel.textContent = "Amount to pay";
        selectCargo.style.display = "block";
        inputItem.style.display = "none";
        btnAddCargo.style.display = "block";
     // Reiniciar valor de 'inputCargoItem' para evitar datos inconsistentes
        inputCargoItem.value = "";
    } else {
        expenseObjetoPuestoLabel.textContent = "Object";
        expenseCargoItemLabel.textContent = "Item";
        expensePrecioUnitarioMontoLabel.textContent = "Unit Cost";
        selectCargo.style.display = "none";
        inputItem.style.display = "block";
        btnAddCargo.style.display = "none";
        // Reiniciar el valor de 'inputCargoItem' (puedes agregar más reinicios si es necesario)
        inputCargoItem.value = "";
    }

}



function toggleFechaFin(selectElement) {
    // Obtener el valor seleccionado
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    //const formaPago = selectedOption.value;  // Asumiendo que 'formaPago' está después de '->'
    const formaPago = selectedOption.getAttribute("data-formaPago");// Obtener la forma de pago de clasificacion
//alert(formaPago);
    // Obtener el campo de fechaFin y tiempo
    const fechaInicioInput = document.getElementById('inputfechaInicio'); 
    const fechaFinInput = document.getElementById('inputfechaFin'); 
    const tiempoInput = document.getElementById('inputTiempo');
    

    // Verificar si formaPago es 'pago único'
    if (formaPago === 'single payment') {
    	fechaFinInput.readOnly = true; // fechaFinInput.disabled = true; // Deshabilitar
    	fechaInicioInput.value='';
        fechaFinInput.value = ''; // Limpiar valor si está deshabilitado
        //tiempoInput.disabled = true;
        tiempoInput.value = '1';
        
        
    } else {
    	fechaFinInput.readOnly = false; // Quitar readonly

        //fechaFinInput.disabled = false; // Habilitar
        //tiempoInput.disabled = false;
    }
}


    
    function checkDates() {
        const fechaInicioStr = document.getElementById('inputfechaInicio').value;
        const fechaFinStr = document.getElementById('inputfechaFin').value;
        const formaPago = document.getElementById('inputPago').value;
        
        // Verificar si se ingresó la fecha de inicio
        if (fechaInicioStr) {
            const [yearInicio, monthInicio, dayInicio] = fechaInicioStr.split("-");
            const fechaInicio = new Date(Date.UTC(yearInicio, monthInicio - 1, dayInicio));

            if (isNaN(fechaInicio.getTime())) {
                alert("Por favor, ingresa una fecha de inicio válida.");
                return;
            }

            let fechaFin;
            
            // Si el tipo de forma de pago no es 'single payment', se requiere una fecha de fin válida
            if (formaPago !== 'single payment') {
                if (fechaFinStr) {
                    const [yearFin, monthFin, dayFin] = fechaFinStr.split("-");
                    fechaFin = new Date(Date.UTC(yearFin, monthFin - 1, dayFin));

                    if (isNaN(fechaFin.getTime())) {
                        alert("Por favor, ingresa una fecha de fin válida.");
                        return;
                    }
                } else {
                    alert("Por favor, ingresa la fecha de fin.");
                    return;
                }
            } else {
                // Si la forma de pago es 'single payment', se permite que fechaFin esté vacía
                fechaFin = fechaInicio; // Se asigna la fecha de inicio como fecha de fin
            }

            // Llamar a la función para calcular el tiempo con fechaInicio y fechaFin
            calculateInputTiempo(fechaInicio, fechaFin);

        } else {
            alert("Por favor, completa la fecha de inicio.");
        }
    }

    function calculateInputTiempo(fechaInicial, fechaFinal) {
        if (!fechaInicial || !fechaFinal) {
            return; // No calcular si alguna fecha es nula o indefinida
        }
        // Extraer día, mes y año de las fechas
        //alert(fechaInicial+'jo'+fechaFinal);
        const diaInicial = fechaInicial.getDate();
        const mesInicial = fechaInicial.getMonth() + 1; // Los meses en JavaScript son 0-indexed
        const anoInicial = fechaInicial.getFullYear();
        
        const diaFinal = fechaFinal.getDate();
        const mesFinal = fechaFinal.getMonth() + 1; // Los meses en JavaScript son 0-indexed
        const anoFinal = fechaFinal.getFullYear();
        
        //alert("DIA "+diaInicial);
        
        let dias;

        // Ajuste para febrero y el último día del mes
        if ((mesFinal === 2 && diaFinal >= 28) || (mesFinal !== 2 && diaFinal >= 30)) {
            diaFinal = 30;
            
        }
        // Ajustar si el día final es menor que el día inicial
        if (diaFinal < diaInicial) {
            diaFinal += 30;
            mesFinal -= 1;
            alert("Ajustar si el día final es menor que el día inicial");
        }
        // Ajustar si el mes final es menor que el mes inicial
        if (mesFinal < mesInicial) {
            mesFinal += 12;
            anoFinal -= 1;
            alert("Ajustar si el mes final es menor que el mes inicial");
        }
    // Calcular el total de días
        dias = (diaFinal + 1 - diaInicial) +
               ((mesFinal - mesInicial) * 30) +
               ((anoFinal - anoInicial) * 360);

        //return dias;
    //alert(dias);
    document.getElementById('inputTiempo').value = dias/30; // Establece el valor calculado
    }



function calculateTotalMontoPagar() {
    const cantidad = parseFloat(document.getElementById("inputCantidad").value) || 0;
    const precioUnitario = parseFloat(document.getElementById("inputPrecioUnitario").value) || 0;
    const tiempo = parseFloat(document.getElementById("inputTiempo").value) || 1;
        // Calcular montos
        const montoTotalPagar = (cantidad * precioUnitario) * tiempo;

        const formateador = new Intl.NumberFormat('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
        // Formatear monto solo para mostrarlo
        document.getElementById("inputMontoPagar").value = formateador.format(montoTotalPagar);

    const cambioMoneda = parseFloat(document.getElementById("inputCambioMoneda").value) || 0;
    const divisaOther = document.getElementById("divisaOther");
    
    if (divisaOther.checked) {
        const montoLCU = (montoTotalPagar * cambioMoneda);
        document.getElementById("inputTotalLCU").value = formateador.format(montoLCU);

    }
    else{
    	document.getElementById("inputTotalLCU").value = formateador.format(montoTotalPagar);
    }

}


document.getElementById('btnAttach').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        // Aquí podrías subir el archivo a tu servidor y obtener la URL
        // Por ejemplo, usando una llamada AJAX o un formulario de envío de archivos

        // Por ahora, simplemente añadimos el nombre del archivo al campo de texto
        document.getElementById('inputAttach').value = file.name;
    }
});



    function updateLabel() {
        const select = document.getElementById("inputtipoB");
        const label = document.getElementById("documento");
        const labelF = document.getElementById("firstName");
        const labelL = document.getElementById("lastName");
        const labelE = document.getElementById("email");
        const labelC = document.getElementById("company");
        
        if (select.value === "STAFF") {
            label.textContent = "Identification Document (*)";
            labelF.textContent ="First Name (*)";
            labelL.textContent ="Last Name (*)";
            labelE.textContent = "Email (*)";
            labelC.textContent = "Company Name";
            document.getElementById("inputnombre").required=true;
            document.getElementById("inputapellidos").required=true;
            document.getElementById("inputrazonSocial").required=false;
            document.getElementById("inputemail").required=true;
            
            
        } else if  (select.value === "LEGAL ENTITY"){
        	
            label.textContent = "Number Identification of Company (*)";
            labelF.textContent ="First Name (Legal representative) ";
            labelL.textContent ="Last Name (Legal representative) ";
            labelE.textContent = "Email";
            labelC.textContent = "Company Name (*)";
            document.getElementById("inputnombre").required=false;
            document.getElementById("inputapellidos").required=false;
            document.getElementById("inputrazonSocial").required=true;
            document.getElementById("inputemail").required=false;
        }
        else
        	{
            label.textContent = "Identification Document (*)";
            labelF.textContent ="First Name (*)";
            labelL.textContent ="Last Name (*)";
            labelE.textContent = "Email";
            labelC.textContent = "Company Name";
            document.getElementById("inputnombre").required=true;
            document.getElementById("inputapellidos").required=true;
            document.getElementById("inputrazonSocial").required=false;
            document.getElementById("inputemail").required=false;
        	}
    }

    // Call updateLabel on page load to set the label correctly if editing an existing record
    window.onload = updateLabel;


    document.getElementById("classificacionEgreso").addEventListener("change", function() {
    	var selectedOption = select.options[select.selectedIndex];
    	var classExpense = selectedOption.getAttribute("data-nombreClase");

        
        if (classExpense) {
            // Realiza la solicitud AJAX para obtener los beneficiarios
            fetch(`/expense/getBeneficiariesByClassExpense?classExpense=${classExpense}`)
                .then(response => response.json())
                .then(beneficiaries => {
                    const claseSelect = document.getElementById("beneficiarioE");

                    // Limpia las opciones de categorías
                    claseSelect.innerHTML = '<option value="">Select beneficiary</option>';

                    // Llena las opciones con los datos recibidos
                    beneficiaries.forEach(beneficiary => {
                        const option = document.createElement("option");
                        option.value = beneficiary.id;
                        option.setAttribute("data-tipo", beneficiary.tipo);
                        option.setAttribute("data-documento", beneficiary.documento);
                        option.setAttribute("data-nombres", beneficiary.nombres);
                        option.setAttribute("data-apellidos", beneficiary.apellidos ? beneficiary.apellidos : '');

                        // Establece el texto del option
                        option.textContent = `${beneficiary.tipo} => ${beneficiary.nombres} ${beneficiary.apellidos ? beneficiary.apellidos : ''} - ${beneficiary.documento}`;
                        claseSelect.appendChild(option);
                    });
                })
                .catch(error => console.error('Error fetching beneficiaries:', error));
        }
    });


    document.addEventListener("DOMContentLoaded", function() {
        // Seleccionar los elementos de radio y el select
        const divisaUsd = document.getElementById("divisaUsd");
        const divisaOther = document.getElementById("divisaOther");
        const divisaSelect = document.getElementById("divisaSelect");
        const divisaRow = document.getElementById("divisaRow");
    

        // Configuración inicial: USD seleccionado, select deshabilitado
        divisaSelect.disabled = true;
        divisaRow.style.display = "none"; // Oculta divisaRow por defecto

        // Función para habilitar o deshabilitar el select y ajustar las opciones
        function toggleDivisaSelect() {
            if (divisaUsd.checked) {
                divisaSelect.disabled = true;
                resetOptions(); // Muestra todas las opciones, incluyendo USD
                divisaSelect.value = ""; // Limpia la selección
                divisaRow.style.display = "none"; 
                divisaSelect.required= false;
                document.getElementById("inputCambioMoneda").required = false;
                document.getElementById("inputCambioMoneda").value = 1;
                calculateTotalMontoPagar();
            } else if (divisaOther.checked) {
                divisaSelect.disabled = false;
                removeUSDOption(); // Elimina la opción de USD
                divisaRow.style.display = "block";
                divisaSelect.required= true;
                document.getElementById("inputCambioMoneda").required = true;
            }
        }
        // Restaura todas las opciones en divisaSelect, incluyendo USD
        function resetOptions() {
            Array.from(divisaSelect.options).forEach(option => {
                option.style.display = "block";
            });
        }
        // Oculta la opción USD en divisaSelect
        function removeUSDOption() {
            Array.from(divisaSelect.options).forEach(option => {
                if (option.getAttribute("data-cod") === "USD") {
                    option.style.display = "none";
                }
            });
        }
        // Escucha cambios en los radio buttons
        divisaUsd.addEventListener("change", toggleDivisaSelect);
        divisaOther.addEventListener("change", toggleDivisaSelect);

        // Inicializa el comportamiento
        toggleDivisaSelect();
    });
    
    function validateCountrySelection() {
    const paisSelect = document.getElementById("paisExpense");
    if (paisSelect.value === "") {
        alert("Please select a country.");
        paisSelect.focus();
        return false; // Evita el envío del formulario
    }
    return true; // Permite el envío del formulario si la validación pasa
}
    function validateTransferSelection() {
    const transferSelect = document.getElementById("inputTipoTransferencia");
    if (transferSelect.value === "") {
        alert("Please select a type of transfer.");
        transferSelect.focus();
        return false; // Evita el envío del formulario
    }
    return true; // Permite el envío del formulario si la validación pasa
}
