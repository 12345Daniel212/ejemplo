document.addEventListener('DOMContentLoaded', () => {

    // --- 1. CONFIGURADOR DE PRODUCTO (MENUDEO) ---
    const sizeBtns = document.querySelectorAll('#size-options .btn-select');
    const roastBtns = document.querySelectorAll('#roast-options .btn-select');
    const grindCards = document.querySelectorAll('.molienda-card');
    const priceDisplay = document.getElementById('final-price');

    // Estado inicial: 250g, Medio, Grano
    let retailBase = 150,
        grindExtra = 0,
        szVal = "250g",
        rstVal = "MEDIO",
        grdVal = "GRANO";

    function updateRetailPrice() {
        const total = retailBase + grindExtra;
        priceDisplay.innerText = total;

        // Actualizar etiquetas visuales en la bolsa
        if (document.getElementById('display-size'))
            document.getElementById('display-size').innerText = szVal.toUpperCase();
        if (document.getElementById('display-roast'))
            document.getElementById('display-roast').innerText = rstVal;
        if (document.getElementById('display-grind'))
            document.getElementById('display-grind').innerText = grdVal;
    }

    // Selección de Tamaño
    sizeBtns.forEach(btn => btn.addEventListener('click', () => {
        document.querySelector('#size-options .btn-select.active').classList.remove('active');
        btn.classList.add('active');
        retailBase = parseInt(btn.dataset.price);
        szVal = btn.dataset.val;
        updateRetailPrice();
    }));

    // Selección de Tueste
    roastBtns.forEach(btn => btn.addEventListener('click', () => {
        document.querySelector('#roast-options .btn-select.active').classList.remove('active');
        btn.classList.add('active');
        rstVal = btn.dataset.val.toUpperCase();
        updateRetailPrice();
    }));

    // Selección de Molienda
    grindCards.forEach(card => card.addEventListener('click', () => {
        document.querySelector('.molienda-card.active').classList.remove('active');
        card.classList.add('active');
        grindExtra = parseInt(card.dataset.extra);
        grdVal = card.dataset.val.toUpperCase();
        updateRetailPrice();
    }));

    // Botón Ordenar WhatsApp
    const btnOrder = document.getElementById('btn-order');
    if (btnOrder) {
        btnOrder.addEventListener('click', () => {
            const finalPrice = retailBase + grindExtra;
            const msg = encodeURIComponent(`¡Hola Punto Tueste! Me interesa el pedido personalizado: Bolsa de ${szVal}, tueste ${rstVal}, molienda ${grdVal}. Total: $${finalPrice}`);
            window.open(`https://wa.me/523111026504?text=${msg}`);
        });
    }


    // --- 2. CALCULADORA DE MAYOREO (LA BARRITA) ---
    const slider = document.getElementById('b2b-range');
    const kgOutput = document.getElementById('kg-output');
    const uPriceDisplay = document.getElementById('b2b-u-price');
    const savingDisplay = document.getElementById('b2b-saving');

    // Referencia de precio menudeo para calcular el ahorro
    const RETAIL_KG_REF = 430;

    if (slider) {
        slider.addEventListener('input', () => {
            const kg = parseInt(slider.value);
            let precioMayoreo;

            // Escala de precios de mayoreo (Ajustada proporcionalmente al nuevo precio de $430)
            if (kg >= 30) {
                precioMayoreo = 310;
            } else if (kg >= 15) {
                precioMayoreo = 330;
            } else if (kg >= 10) {
                precioMayoreo = 340;
            } else {
                precioMayoreo = 350; // Para 5kg a 9kg
            }

            // Cálculo de ahorro: (Precio Normal $430 - Precio Mayoreo) * cantidad de kilos
            const ahorroTotal = (RETAIL_KG_REF - precioMayoreo) * kg;

            // Actualizar el HTML
            kgOutput.innerText = kg;
            uPriceDisplay.innerText = `$${precioMayoreo}`;
            savingDisplay.innerText = `$${ahorroTotal.toLocaleString()}`;
        });
    }

    // Botón Cotización Mayoreo
    const btnB2B = document.querySelector('.btn-b2b');
    if (btnB2B) {
        btnB2B.addEventListener('click', () => {
            const kg = slider.value;
            const msg = encodeURIComponent(`¡Hola! Quisiera una cotización de mayoreo para mi negocio por un volumen aproximado de ${kg} kilos al mes.`);
            window.open(`https://wa.me/523111026504?text=${msg}`);
        });
    }
});