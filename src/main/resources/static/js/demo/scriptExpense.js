        // Función para hacer desaparecer el mensaje después de 4 segundos
        function hideMessage() {
            setTimeout(function() {
                var message = document.getElementById("notificationMessage");
                if (message) {
                    message.style.display = 'none';
                }
            }, 4000);  // 4000 milisegundos = 4 segundos
        }



//FUNCTIONS  BY EXPENSE REGISTER
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


    
    /*function checkDates() {
        const fechaInicioStr = document.getElementById('inputfechaInicio').value;
        const fechaFinStr = document.getElementById('inputfechaFin').value;
        const formaPago = document.getElementById('inputPago').value;
        
        // Verificar si se ingresó la fecha de inicio
        if (fechaInicioStr) {
            const [yearInicio, monthInicio, dayInicio] = fechaInicioStr.split("-");
            const fechaInicio = new Date(Date.UTC(yearInicio, monthInicio - 1, dayInicio));

            if (isNaN(fechaInicio.getTime())) {
                alert("Please, enter start date validate.");
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
           // calculateInputTiempo(fechaInicio, fechaFin);

        } else {
            alert("Please, fill in the start date.");
        }
    }
*/
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
////////////////**************************************//////////////////// */
// FUNCTIONS BY EXPENSE VOUCHERS
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});

function openModal() {
    // Limpiar el campo ID para crear un nuevo registro
    document.getElementById("inputId").value = 0;
    
    // Limpiar otros campos del formulario si es necesario
    document.getElementById("inputdocumento").value = "";
    // Limpiar otros campos del formulario...

    // Mostrar el modal
    $('#myModal').modal('show');
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
 //calcula el total cost/ basado en el cambio de divisa registrado en expense
function calculateTotalCosto() {
    const cantidad = parseFloat(document.getElementById("inputCantidad").value) || 0;
    const cantidadPagos = parseFloat(document.getElementById("inputCantidadPagos").value) || 0;
    const precioUnitario = parseFloat(document.getElementById("inputPrecioUnitario").value) || 0;
    const cambio = parseFloat(document.getElementById("inputCambioExpense").value) || 0;

        // Calcular montos
        const montoTotalPagar = (cantidad*cantidadPagos*precioUnitario);
        const montoUSD = cambio ? montoTotalPagar / cambio : 0;
        const formateador = new Intl.NumberFormat('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
        //const numeroFormateado = formateador.format(montoTotalPagar);
        document.getElementById("inputCostoTotal").value = formateador.format(montoTotalPagar);
        document.getElementById("inputTotalUSD").value = formateador.format(montoUSD);
}
//despliega el detalle de cada voucher registrado de INTERFAZ beneficiario
function toggleDetailsB(element) {
    // Selecciona el contenedor de detalles para mostrar/ocultar
    const detailsTable = element.closest('td').querySelector('.details-table');
    if (detailsTable.style.display === 'none') {
        detailsTable.style.display = 'block';
        element.textContent = '-'; // Cambia el ícono a '-'
    } else {
        detailsTable.style.display = 'none';
        element.textContent = '+'; // Cambia el ícono a '+'
    }
}

//evento onchange en selectExpense
    function updatePhaseDivisaSelection() {
        const expensesSelect = document.getElementById("expensesBeneficiary");
        const selectedOption = expensesSelect.options[expensesSelect.selectedIndex];
        
        const cambioValue = selectedOption.getAttribute("data-cambio");
        const divisa = selectedOption.getAttribute("data-divisa");
        document.getElementById("inputCambioExpense").value = cambioValue;
        document.getElementById("labelDivisa").textContent = 'Total ('+divisa+')';
        
        // Obtiene el data-fase-id del expense seleccionado
        const faseId = selectedOption.getAttribute("data-fase-id");
        
        // Selecciona la fase correspondiente en el select de fases
        const fasesSelect = document.getElementById("fasesExpense");
        Array.from(fasesSelect.options).forEach(option => {
            option.selected = option.value === faseId;
        });
        
        //document.getElementById("fasesExpense").addEventListener("change", function() {
            const selectedPhaseId = faseId;//this.value;
            
            if (selectedPhaseId) {
                // Realiza la solicitud AJAX para obtener las categorías
                fetch(`/gastos/getCategoriesByPhase?phaseId=${selectedPhaseId}`)
                    .then(response => response.json())
                    .then(categories => {
                        const categorySelect = document.getElementById("categoriesFases");

                        // Limpia las opciones de categorías
                        categorySelect.innerHTML = '<option value="">Select category</option>';

                        // Llena las opciones con los datos recibidos
                        categories.forEach(category => {
                            const option = document.createElement("option");
                            option.value = category.id;
                            option.textContent = category.categoria;
                            categorySelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error fetching categories:', error));
            }
       // });
    }
    
    document.getElementById('btnAttach').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});
// para recuperar el archivo guardado
document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        // Aquí podrías subir el archivo a tu servidor y obtener la URL
        // Por ejemplo, usando una llamada AJAX o un formulario de envío de archivos

        // Por ahora, simplemente añadimos el nombre del archivo al campo de texto
        document.getElementById('inputAttach').value = file.name;
    }
});
//despliega el detalle de cada voucher INTERFAZ ADMIN
/*
function toggleDetails(button, detailsId) {
    //var detailsSection = document.getElementById(detailsId);
    const detailsSection = document.getElementById(detailsId);
    if (detailsSection.style.display === "none") {
        detailsSection.style.display = "block";
        //button.innerHTML = "Hide vouchers by expense";
        button.querySelector('span').textContent = "Hide vouchers by expense";
    } else {
        detailsSection.style.display = "none";
        //button.innerHTML = "Show vouchers by expense";
        button.querySelector('span').textContent = "Show vouchers by expense";
    }
}*/
///DESPLIEGA EL DETALLE DE XPENSE BY BENEFICIARY	
	function toggleDetails(button) {
    const targetId = button.getAttribute('data-target-id');
    const targetElement = document.getElementById(targetId);
    if (targetElement) {
        const isHidden = targetElement.style.display === 'none';
        targetElement.style.display = isHidden ? 'block' : 'none';
        button.querySelector('span').textContent = isHidden ? 'Hide vouchers by expense' : 'Show vouchers by expense';
    }
    else {
        alert('Element with ID '+targetId+ 'not found.');
    }
}

function toggleSubDetails(link) {
    var subDetails = link.closest('td').querySelector('.details-table');
    if (subDetails.style.display === "none") {
        subDetails.style.display = "block";
        link.innerHTML = "-";
    } else {
        subDetails.style.display = "none";
        link.innerHTML = "+";
    }
}

// Obtener el valor de beneficiaryId cuando se abre el modal INTERFAZ ADMIN
    $('#expenseDisclaimerModal').on('show.bs.modal', function (event) {
        // Obtener el botón que abrió el modal
        var button = $(event.relatedTarget); 
        // Extraer el valor del atributo data-beneficiary-id
        var beneficiaryId = button.data('beneficiary-id');
        var beneficiaryName = 'Select beneficiary expenses: '+button.data('beneficiary-fullname');
        // Opcional: asignar este valor a un campo en el modal
        $('#beneficiaryIdField').val(beneficiaryId);
        $('#labelBeneficiaryFullName').text(beneficiaryName);

        // Recuperar projectId y beneficiaryId desde los campos del modal
        const projectId = $('#inputproyectoId').val(); // Suponiendo que tienes un campo con el ID 'inputproyectoId'
        const beneficiaryIdField = $('#beneficiaryIdField').val();

         if (projectId && beneficiaryIdField) {
            // Realizar la solicitud AJAX para obtener los gastos por beneficiario y proyecto
            fetch(`/gastos/getExpensesByBeneficiary?projectId=${projectId}&beneficiaryId=${beneficiaryIdField}`)
                .then(response => response.json())
                .then(expenses => {
                    // Poblado de expensesBeneficiary
                    const expensesSelect = document.getElementById("expensesBeneficiary");
                    expensesSelect.innerHTML = '<option value="">Select expense</option>';

                    expenses.forEach(expense => {
                        const option = document.createElement("option");
                        option.value = expense.id;
                        option.textContent = `${expense.clasificacionEgreso.nombreClase} - ${expense.objeto} ( ${expense.cargoItem} ) = ${expense.montoTotal} (USD)`;
                        option.setAttribute("data-fase-id", expense.proyectoFase.fase.id); // Agrega el data-fase-id
                        option.setAttribute("data-cambio", expense.cambio); // Agrega el data-fase-id
                        option.setAttribute("data-divisa", expense.divisa.divisa); // Agrega el data-fase-id
                        expensesSelect.appendChild(option);
                    });

                })
                .catch(error => console.error('Error fetching expenses:', error));
        } else {
            console.error('Faltan projectId o beneficiaryId');
        }
    });
    /*
    // Poblado de fasesExpense
    const fasesSelect = document.getElementById("fasesExpense");
    fasesSelect.innerHTML = '<option value="">Select phase</option>';

    const uniquePhases = new Set(); // Para evitar duplicados

    expenses.forEach(expense => {
        const phaseId = expense.proyectoFase.fase.id;
        const phaseName = `${expense.proyectoFase.fase.nombre} - ${expense.proyectoFase.fase.faseDescripcion}`;

        if (!uniquePhases.has(phaseId)) {
            uniquePhases.add(phaseId);
            const option = document.createElement("option");
            option.value = phaseId;
            option.textContent = phaseName;
            fasesSelect.appendChild(option);
        }
    });*/
 
    function setDeleteUrl(projectId,id,tipo) {
        // Establecer la URL del botón de confirmación de eliminación
        document.getElementById("confirmDeleteBtn").setAttribute("href", "/gastos/eliminar-voucherBeneficiario/"+projectId+"/" + id+"/"+tipo);
    }

    // Trigger para abrir el selector de archivo EN DESCARGO/ VOUCHER
    function triggerFileInput() {
        document.getElementById("fileInput").click();
    }

    // Actualiza el campo de texto con el nombre del archivo seleccionado
    function updateFileName() {
        const fileInput = document.getElementById("fileInput");
        const inputAttach = document.getElementById("inputAttach");
        if (fileInput.files.length > 0) {
        	inputAttach.value = fileInput.files[0].name;
            // Eliminar la clase de error si existía
            inputAttach.classList.remove("is-invalid");
        }

    }
    // Validar el formulario
    function validateAttach() {
    	const attachField = document.getElementById("inputAttach");

       if (!attachField.value.trim()) {
		    attachField.classList.add("is-invalid");
		    return false; // Detener el envío
		} else {
		    attachField.classList.remove("is-invalid");
		    return true; // Permitir el envío
		}
	}
	
	// Función para establecer la fecha máxima como la fecha actual
    window.onload = function() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); // Enero es 0!
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd; // Formato YYYY-MM-DD

        document.getElementById("inputfechaVoucherBeneficiario").setAttribute("max", today);
    }

